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
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.Person;

public class PeopleDao extends GenericDao<Person> {

	private static final String SQL_INSERT_PERSON = "INSERT INTO PERSON(firstname, lastname, telephone, mobile, email, www) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_PERSON = "DELETE FROM PERSON WHERE id = ?";
	private static final String SQL_FIND_PERSON_BY_ID = "SELECT * FROM PERSON WHERE id = ?";
	private static final String SQL_DELETE_ALL_PEOPLE = "DELETE FROM PERSON WHERE id > 0";
	private static final String SQL_FIND_PERSON_ALL = "SELECT * FROM PERSON";
	private static final String SQL_UPDATE_PERSON = "UPDATE PERSON SET firstname = ?, lastname = ?, telephone = ?, mobile = ?, email = ?, www = ? WHERE id = ?";

	
	public PeopleDao(String databasefilepath) {
		super(databasefilepath);
	}
	@Override
	public List<Person> findAll() throws SQLException {
		List<Person> people = new ArrayList<Person>();
		try {
			init();
			ResultSetHandler<List<Person>> h = new BeanListHandler<Person>(
					Person.class);
			people = run.query(conn, SQL_FIND_PERSON_ALL, h);
			// find addresses
			AddressDao adrDao = new AddressDao();
			for (Person c : people) {
				c.setAddresses(adrDao.findAllByPersonId(c.getId(), run, conn));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			close(conn, st, generatedKeys);
		}
		return people;
	}

	@Override
	public Person find(Long id) throws SQLException {
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
	public Person find(Long id, QueryRunner run, Connection conn,
			PreparedStatement st, ResultSet generatedKeys) throws SQLException {
		Person p = null;
		ResultSetHandler<Person> h = new BeanHandler<Person>(Person.class);
		p = run.query(conn, SQL_FIND_PERSON_BY_ID, h, id);
		if(p != null){
			// find addresses
			AddressDao adrDao = new AddressDao();
			p.setAddresses(adrDao.findAllByPersonId(p.getId(), run, conn));
		}
		return p;
	}

	@Override
	public void create(Person c) throws SQLException {
		try {
			init();
			st = conn.prepareStatement(SQL_INSERT_PERSON,
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, c.getFirstName());
			st.setString(2, c.getLastName());
			st.setString(3, c.getTelephone());
			st.setString(4, c.getMobile());
			st.setString(5, c.getEmail());
			st.setString(6, c.getWww());
			// run the query
			int i = st.executeUpdate();
			System.out.println("i: " + i);
			if (i == -1) {
				System.out.println("db error : " + SQL_INSERT_PERSON);
			}
			generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				c.setId(generatedKeys.getLong(1));
			} else {
				throw new SQLException(
						"Creating user failed, no generated key obtained.");
			}
			// if the person creation was successfull, add addresses
			AddressDao adrDao = new AddressDao();
			for (Address adr : c.getAddresses()) {
				adr.setPerson_id(c.getId());
				adrDao.create(adr, run, conn, generatedKeys);
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
	public void update(Person c) throws SQLException {
		try {
			init();
			st = conn.prepareStatement(SQL_UPDATE_PERSON);
			st.setString(1, c.getFirstName());
			st.setString(2, c.getLastName());
			st.setString(3, c.getTelephone());
			st.setString(4, c.getMobile());
			st.setString(5, c.getEmail());
			st.setString(6, c.getWww());
			st.setLong(7, c.getId());
			// run the query
			int i = st.executeUpdate();
			System.out.println("i: " + i);
			if (i == -1) {
				System.out.println("db error : " + SQL_UPDATE_PERSON);
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
					adr.setPerson_id(c.getId());
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
		Person toDel = find(id);
		AddressDao adrDao = new AddressDao();
		try {
			init();
			for (Address adr : toDel.getAddresses()) {
				adrDao.delete(adr.getId(), conn);
			}
			st = conn.prepareStatement(SQL_DELETE_PERSON);
			st.setLong(1, id);
			// run the query
			int i = st.executeUpdate();
			System.out.println("i: " + i);
			if (i == -1) {
				System.out.println("db error : " + SQL_DELETE_PERSON);
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
			List<Person> people = findAll();
			for(Person p : people){
				delete(p.getId());
			}
		} catch (Exception e) {
			// rollback the transaction but rethrow the exception to the caller
			e.printStackTrace();
			throw new SQLException(e);
		} finally {
			close(conn, st, generatedKeys);
		}
	}
}
