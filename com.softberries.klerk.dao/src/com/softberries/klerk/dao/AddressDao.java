package com.softberries.klerk.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.softberries.klerk.dao.to.Address;

public class AddressDao extends GenericDao<Address>{
	
	private static final String SQL_INSERT_ADDRESS = "INSERT INTO ADDRESS(country, city, street, postCode, houseNumber, flatNumber, notes, main, person_id, company_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_ADDRESS = "DELETE FROM ADDRESS WHERE id = ?";
	private static final String SQL_FIND_ADDRESS_BY_ID = "SELECT * FROM ADDRESS WHERE id = ?";
	private static final String SQL_DELETE_ALL_ADDRESSES = "DELETE FROM ADDRESS WHERE id > 0";
	private static final String SQL_FIND_ADDRESS_ALL = "SELECT * FROM ADDRESS";
	private static final String SQL_FIND_ADDRESS_ALL_BY_COMPANY_ID = "SELECT * FROM ADDRESS WHERE COMPANY_ID = ?";
	private static final String SQL_UPDATE_ADDRESS = "UPDATE ADDRESS SET country = ?, city = ?, street = ?, postCode = ?, houseNumber = ?, flatNumber = ?, notes = ?, main = ?, person_id = ?, company_id = ? WHERE id = ?";
	
	@Override
	public List<Address> findAll() throws SQLException {
		List<Address> companies = new ArrayList<Address>();
		try{
			init();
			ResultSetHandler<List<Address>> h = new BeanListHandler<Address>(Address.class);
			companies = run.query(conn, SQL_FIND_ADDRESS_ALL, h); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return companies;
	}
	public List<Address> findAllByCompanyId(Long companyId) throws SQLException {
		List<Address> companies = new ArrayList<Address>();
		try{
			init();
			ResultSetHandler<List<Address>> h = new BeanListHandler<Address>(Address.class);
			companies = run.query(conn, SQL_FIND_ADDRESS_ALL_BY_COMPANY_ID, h, companyId); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return companies;
	}

	@Override
	public Address find(Long id) throws SQLException {
		Address p = null;
		try{
			init();
			ResultSetHandler<Address> h = new BeanHandler<Address>(Address.class);
			p = run.query(conn, SQL_FIND_ADDRESS_BY_ID, h, id); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return p;
	}

	@Override
	public void create(Address c) throws SQLException {
		try {
			init();
	        st = conn.prepareStatement(SQL_INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS); 
	        st.setString(1, c.getCountry());
	        st.setString(2, c.getCity());
	        st.setString(3, c.getStreet());
	        st.setString(4, c.getPostCode());
	        st.setString(5, c.getHouseNumber());
	        st.setString(6, c.getFlatNumber());
	        st.setString(7, c.getNotes());
	        st.setBoolean(8, c.isMain());
	        if(c.getPerson_id() == null && c.getCompany_id() == null){
	        	throw new SQLException("For Address either Person or Company needs to be specified");
	        }
	        if(c.getPerson_id() != null){
	        	st.setLong(9, c.getPerson_id());
	        }else{
	        	st.setNull(9, java.sql.Types.NUMERIC);
	        }
	        if(c.getCompany_id() != null){
	        	st.setLong(10, c.getCompany_id());
	        }else{
	        	st.setNull(10, java.sql.Types.NUMERIC);
	        }
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_INSERT_ADDRESS);
	        }
	        generatedKeys = st.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            c.setId(generatedKeys.getLong(1));
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
	}

	@Override
	public void update(Address c) throws SQLException {
		try {
			init();
			st = conn.prepareStatement(SQL_UPDATE_ADDRESS); 
			st.setString(1, c.getCountry());
	        st.setString(2, c.getCity());
	        st.setString(3, c.getStreet());
	        st.setString(4, c.getPostCode());
	        st.setString(5, c.getHouseNumber());
	        st.setString(6, c.getFlatNumber());
	        st.setString(7, c.getNotes());
	        st.setBoolean(8, c.isMain());
	        if(c.getPerson_id() == null && c.getCompany_id() == null){
	        	throw new SQLException("Either Person or Company needs to be specified");
	        }
	        if(c.getPerson_id() != null){
	        	st.setLong(9, c.getPerson_id());
	        }else{
	        	st.setNull(9, java.sql.Types.NUMERIC);
	        }
	        if(c.getCompany_id() != null){
	        	st.setLong(10, c.getCompany_id());
	        }else{
	        	st.setNull(10, java.sql.Types.NUMERIC);
	        }
	        st.setLong(11, c.getId());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_UPDATE_ADDRESS);
	        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		try {
			init();
			st = conn.prepareStatement(SQL_DELETE_ADDRESS); 
	        st.setLong(1, id);
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_ADDRESS);
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
			st = conn.prepareStatement(SQL_DELETE_ALL_ADDRESSES);
			int i = st.executeUpdate();
			System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_ALL_ADDRESSES);
	        }
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
	}
}
