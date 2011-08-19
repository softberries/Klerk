package com.softberries.klerk.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.softberries.klerk.dao.to.Person;

public class PeopleDao extends GenericDao<Person>{

	private static final String SQL_INSERT_PERSON = "INSERT INTO PERSON(firstname, lastname, telephone, mobile, email, www) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_PERSON = "DELETE FROM PERSON WHERE id = ?";
	private static final String SQL_FIND_PERSON_BY_ID = "SELECT * FROM PERSON WHERE id = ?";
	private static final String SQL_DELETE_ALL_PEOPLE = "DELETE FROM PERSON WHERE id > 0";
	private static final String SQL_FIND_PERSON_ALL = "SELECT * FROM PERSON";
	private static final String SQL_UPDATE_PERSON = "UPDATE PERSON SET firstname = ?, lastname = ?, telephone = ?, mobile = ?, email = ?, www = ? WHERE id = ?";
	@Override
	public List<Person> findAll() throws SQLException {
		List<Person> companies = new ArrayList<Person>();
		try{
			init();
			ResultSetHandler<List<Person>> h = new BeanListHandler<Person>(Person.class);
			companies = run.query(conn, SQL_FIND_PERSON_ALL, h); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return companies;
	}

	@Override
	public Person find(Long id) throws SQLException {
		Person p = null;
		try{
			init();
			ResultSetHandler<Person> h = new BeanHandler<Person>(Person.class);
			p = run.query(conn, SQL_FIND_PERSON_BY_ID, h, id); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return p;
	}

	@Override
	public void create(Person c) throws SQLException {
		try {
			init();
	        st = conn.prepareStatement(SQL_INSERT_PERSON, Statement.RETURN_GENERATED_KEYS); 
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
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
	        conn.commit();
		} catch (Exception e) {
			//rollback the transaction but rethrow the exception to the caller
			conn.rollback();
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
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
	        conn.commit();
		} catch (Exception e) {
			//rollback the transaction but rethrow the exception to the caller
			conn.rollback();
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
			close(conn, st, generatedKeys);
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		try {
			init();
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
			//rollback the transaction but rethrow the exception to the caller
			conn.rollback();
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
			close(conn, st, generatedKeys);
		}
	}

	@Override
	public void deleteAll() throws SQLException {
		try{
			init();
			st = conn.prepareStatement(SQL_DELETE_ALL_PEOPLE);
			int i = st.executeUpdate();
			System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_ALL_PEOPLE);
	        }
	        conn.commit();
		} catch (Exception e) {
			//rollback the transaction but rethrow the exception to the caller
			conn.rollback();
			e.printStackTrace();
			throw new SQLException(e);
		}finally{
			close(conn, st, generatedKeys);
		}
	}
}
