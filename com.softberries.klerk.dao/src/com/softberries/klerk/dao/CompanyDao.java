package com.softberries.klerk.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.dao.to.Product;

public class CompanyDao extends GenericDao<Company>{

	private static final String SQL_INSERT_COMPANY = "INSERT INTO COMPANY(name, vatid, telephone, mobile, email, www) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_COMPANY = "DELETE FROM COMPANY WHERE id = ?";
	private static final String SQL_FIND_COMPANY_BY_ID = "SELECT * FROM COMPANY WHERE id = ?";
	private static final String SQL_DELETE_ALL_COMPANIES = "DELETE FROM COMPANY WHERE id > 0";
	private static final String SQL_FIND_COMPANY_ALL = "SELECT * FROM COMPANY";
	private static final String SQL_UPDATE_COMPANY = "UPDATE COMPANY SET name = ?, vatid = ?, telephone = ?, mobile = ?, email = ?, www = ? WHERE id = ?";
	@Override
	public List<Company> findAll() throws SQLException {
		List<Company> companies = new ArrayList<Company>();
		try{
			init();
			ResultSetHandler<List<Company>> h = new BeanListHandler<Company>(Company.class);
			companies = run.query(conn, SQL_FIND_COMPANY_ALL, h); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		//find addresses
		AddressDao adrDao = new AddressDao();
		for(Company c : companies){
			c.setAddresses(adrDao.findAllByCompanyId(c.getId()));
		}
		return companies;
	}

	@Override
	public Company find(Long id) throws SQLException {
		Company p = null;
		try{
			init();
			ResultSetHandler<Company> h = new BeanHandler<Company>(Company.class);
			p = run.query(conn, SQL_FIND_COMPANY_BY_ID, h, id); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		//find addresses
		AddressDao adrDao = new AddressDao();
		p.setAddresses(adrDao.findAllByCompanyId(p.getId()));
		return p;
	}

	@Override
	public void create(Company c) throws SQLException {
		try {
			init();
	        st = conn.prepareStatement(SQL_INSERT_COMPANY, Statement.RETURN_GENERATED_KEYS); 
	        st.setString(1, c.getName());
	        st.setString(2, c.getVatid());
	        st.setString(3, c.getTelephone());
	        st.setString(4, c.getTelephone());
	        st.setString(5, c.getTelephone());
	        st.setString(6, c.getTelephone());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_INSERT_COMPANY);
	        }
	        generatedKeys = st.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            c.setId(generatedKeys.getLong(1));
	        } else {
	            throw new SQLException("Creating company failed, no generated key obtained.");
	        }
	        
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		//if the company creation was successfull, add addresses
        AddressDao adrDao = new AddressDao();
        for(Address adr : c.getAddresses()){
        	adr.setCompany_id(c.getId());
        	adrDao.create(adr);
        	if(adr.isMain()){
        		c.setAddress(adr);
        	}
        }
	}

	@Override
	public void update(Company c) throws SQLException {
		try {
			init();
			st = conn.prepareStatement(SQL_UPDATE_COMPANY); 
			st.setString(1, c.getName());
	        st.setString(2, c.getVatid());
	        st.setString(3, c.getTelephone());
	        st.setString(4, c.getMobile());
	        st.setString(5, c.getEmail());
	        st.setString(6, c.getWww());
	        st.setLong(7, c.getId());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_UPDATE_COMPANY);
	        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		//update addresses
		AddressDao adrDao = new AddressDao();
		for(Address adr : c.getAddresses()){
			if(adr.getId() != null && adr.getId() > 0){
				//update
				adrDao.update(adr);
			}else{//create
				adr.setCompany_id(c.getId());
				adrDao.create(adr);
			}
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		//delete addresses
		Company toDel = find(id);
		AddressDao adrDao = new AddressDao();
		for(Address adr : toDel.getAddresses()){
			adrDao.delete(adr.getId());
		}
		try {
			init();
			st = conn.prepareStatement(SQL_DELETE_COMPANY); 
	        st.setLong(1, id);
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_COMPANY);
	        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
	}

	@Override
	public void deleteAll() throws SQLException {
		try{
			init();
			st = conn.prepareStatement(SQL_DELETE_ALL_COMPANIES);
			int i = st.executeUpdate();
			System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_ALL_COMPANIES);
	        }
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
	}

	
}
