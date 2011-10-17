package com.softberries.klerk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Company;

public class CompanyDao extends GenericDao<Company> {

	private static final String SQL_INSERT_COMPANY = "INSERT INTO COMPANY(name, vatid, telephone, mobile, email, www) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_COMPANY = "DELETE FROM COMPANY WHERE id = ?";
	private static final String SQL_FIND_COMPANY_BY_ID = "SELECT * FROM COMPANY WHERE id = ?";
	private static final String SQL_DELETE_ALL_COMPANIES = "DELETE FROM COMPANY WHERE id > 0";
	private static final String SQL_FIND_COMPANY_ALL = "SELECT * FROM COMPANY";
	private static final String SQL_UPDATE_COMPANY = "UPDATE COMPANY SET name = ?, vatid = ?, telephone = ?, mobile = ?, email = ?, www = ? WHERE id = ?";

	public CompanyDao(String databasefilepath) {
		super(databasefilepath);
	}
	@Override
	public List<Company> findAll() throws SQLException {
		List<Company> companies = new ArrayList<Company>();
		try {
			init();
			ResultSetHandler<List<Company>> h = new BeanListHandler<Company>(
					Company.class);
			companies = run.query(conn, SQL_FIND_COMPANY_ALL, h);
			// find addresses
			AddressDao adrDao = new AddressDao();
			for (Company c : companies) {
				c.setAddresses(adrDao.findAllByCompanyId(c.getId(), run, conn));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			close(conn, st, generatedKeys);
		}
		return companies;
	}

	@Override
	public Company find(Long id) throws SQLException {
		try {
			init();
			return this.find(id, run, conn, st, generatedKeys);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			close(conn, st, generatedKeys);
		}
		return null;
	}

	/**
	 * Used by other DAO's, reuses existing connection, runner and result set
	 * objects
	 * 
	 * @param id
	 * @param run
	 * @param conn
	 * @param st
	 * @param generatedKeys
	 * @return
	 * @throws SQLException
	 */
	public Company find(Long id, QueryRunner run, Connection conn,
			PreparedStatement st, ResultSet generatedKeys) throws SQLException {
		Company p = null;
		ResultSetHandler<Company> h = new BeanHandler<Company>(Company.class);
		p = run.query(conn, SQL_FIND_COMPANY_BY_ID, h, id);
		if(p != null){
			// find addresses
			AddressDao adrDao = new AddressDao();
			p.setAddresses(adrDao.findAllByCompanyId(p.getId(), run, conn));
		}
		return p;
	}

	@Override
	public void create(Company c) throws SQLException {
		try {
			init();
			st = conn.prepareStatement(SQL_INSERT_COMPANY,
					Statement.RETURN_GENERATED_KEYS);
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
				throw new SQLException(
						"Creating company failed, no generated key obtained.");
			}
			// if the company creation was successfull, add addresses
			AddressDao adrDao = new AddressDao();
			for (Address adr : c.getAddresses()) {
				adr.setCompany_id(c.getId());
				adrDao.create(adr, run, conn, generatedKeys);
				if (adr.isMain()) {
					c.setAddress(adr);
				}
			}
			conn.commit();
		} catch (Exception e) {
			// rollback the transaction but rethrow the exception to the caller
			conn.rollback();
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			close(conn, st, generatedKeys);
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

			// delete unused addresses
			AddressDao adrDao = new AddressDao();
			List<Address> toDel = new ArrayList<Address>();
			if (c.getId() != null) {
				List<Address> existingAddresses = adrDao.findAllByCompanyId(
						c.getId(), run, conn);
				for (Address adr : existingAddresses) {
					if (!c.getAddresses().contains(adr)) {
						toDel.add(adr);
					}
				}
			}
			for (Address adr : toDel) {
				adrDao.delete(adr.getId(), conn);
			}
			// update addresses
			for (Address adr : c.getAddresses()) {
				if (adr.getId() != null && adr.getId() > 0) {
					// update
					adrDao.update(adr, run, conn);
				} else {// create
					adr.setCompany_id(c.getId());
					adrDao.create(adr, run, conn, generatedKeys);
				}
			}
			conn.commit();
		} catch (Exception e) {
			// rollback the transaction but rethrow the exception to the caller
			conn.rollback();
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			close(conn, st, generatedKeys);
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		// delete addresses
		Company toDel = find(id);
		AddressDao adrDao = new AddressDao();
		try {
			init();
			for (Address adr : toDel.getAddresses()) {
				adrDao.delete(adr.getId(), conn);
			}
			st = conn.prepareStatement(SQL_DELETE_COMPANY);
			st.setLong(1, id);
			// run the query
			int i = st.executeUpdate();
			System.out.println("i: " + i);
			if (i == -1) {
				System.out.println("db error : " + SQL_DELETE_COMPANY);
			}
			conn.commit();
		} catch (Exception e) {
			// rollback the transaction but rethrow the exception to the caller
			conn.rollback();
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			close(conn, st, generatedKeys);
		}
	}

	@Override
	public void deleteAll() throws SQLException {
		try {
			List<Company> companies = findAll();
			for(Company c : companies){
				delete(c.getId());
			}
		} catch (Exception e) {
			// rollback the transaction but rethrow the exception to the calle
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			close(conn, st, generatedKeys);
		}
	}

}
