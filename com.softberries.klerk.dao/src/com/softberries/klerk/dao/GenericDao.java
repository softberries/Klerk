package com.softberries.klerk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

/**
 * Common functionality for data access objects
 * within Klerk application
 * 
 * @author kris
 *
 */
public abstract class GenericDao<T> {
	
	/**
	 * Database connection object
	 */
	protected Connection conn = null;
	/**
	 * Keeps the current prepared statement reference
	 */
	protected PreparedStatement st = null;
	/**
	 * Keeps the current {@code ResultSet} reference
	 */
	protected ResultSet generatedKeys = null;
	/**
	 * Utility object from Apache library 
	 * allowing convertion between {@code ResultSet}
	 * and specific Transfer Object
	 */
	protected QueryRunner run = null;
	
	/**
	 * Initializes connection to the database
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void init() throws ClassNotFoundException, SQLException{
		run = new QueryRunner();
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:~/.klerk/klerk", "sa", "");
		conn.setAutoCommit(false);
	}
	/**
	 * Closes all resources after execution
	 * 
	 * @param con {@code Connection} object
	 * @param stm {@code PreparedStatement} object
	 * @param gk {@code ResultSet} object
	 * @throws SQLException
	 */
	protected void close(Connection con, PreparedStatement stm,
			ResultSet gk) throws SQLException {
		if(gk != null){
			gk.close();
		}
		if(stm != null){
			stm.close();
		}
		if(con != null){
			con.close();
		}
	}
	/**
	 * Finds all records and convert it to the specific type
	 * 
	 * @return {@code List<T>} list of specific objects created from {@code ResultSet}
	 *  
	 * @throws SQLException
	 */
	public abstract List<T> findAll()  throws SQLException;
	/**
	 * Finds object of type <T> in the database
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public abstract T find(Long id) throws SQLException;
	/**
	 * Adds new record to the database based on the Object of type T
	 * @param t
	 * @throws SQLException
	 */
	public abstract void create(T t) throws SQLException;
	/**
	 * Updates single record in the database
	 * @param t
	 * @throws SQLException
	 */
	public abstract void update(T t) throws SQLException;
	/**
	 * Deletes single record in the database
	 * @param id
	 * @throws SQLException
	 */
	public abstract void delete(Long id) throws SQLException;
	/**
	 * Deletes all records in a table corresponding the type T
	 * @throws SQLException
	 */
	public abstract void deleteAll() throws SQLException;
}
