package com.softberries.klerk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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

import com.softberries.klerk.dao.to.Product;

public class ProductDao {

	private static final String SQL_INSERT_PRODUCT = "INSERT INTO PRODUCT(code, name, description) VALUES(?, ?, ?)";
	private static final String SQL_DELETE_PRODUCT = "DELETE FROM PRODUCT WHERE id = ?";
	private static final String SQL_FIND_PRODUCT_BY_ID = "SELECT * FROM PRODUCT WHERE id = ?";
	private static final String SQL_DELETE_ALL_PRODUCTS = "DELETE FROM PRODUCT WHERE id > 0";
	private static final String SQL_FIND_PRODUCT_ALL = "SELECT * FROM PRODUCT";
	
	private Connection conn = null;
	private PreparedStatement st = null;
	private ResultSet generatedKeys = null;
	private QueryRunner run = null;

	public Product findProduct(Long id) throws SQLException{
		Product p = null;
		try{
			init();
			ResultSetHandler<Product> h = new BeanHandler<Product>(Product.class);
			p = run.query(conn, SQL_FIND_PRODUCT_BY_ID, h, id); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return p;
	}
	
	public List<Product> findAllProducts() throws SQLException{
		List<Product> products = new ArrayList<Product>();
		try{
			init();
			ResultSetHandler<List<Product>> h = new BeanListHandler<Product>(Product.class);
			products = run.query(conn, SQL_FIND_PRODUCT_ALL, h); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return products;
	}
	public void createProduct(Product p) throws SQLException{
		try {
			init();
	        st = conn.prepareStatement(SQL_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS); 
	        st.setString(1, p.getCode());
	        st.setString(2, p.getName());
	        st.setString(3, p.getDescription());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_INSERT_PRODUCT);
	        }
	        generatedKeys = st.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            p.setId(generatedKeys.getLong(1));
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
	}
	
	public void deleteProduct(Long id) throws SQLException{
		try {
			init();
			st = conn.prepareStatement(SQL_DELETE_PRODUCT); 
	        st.setLong(1, id);
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_PRODUCT);
	        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
	}
	public void deleteAllProducts() throws SQLException{
		try{
			init();
			st = conn.prepareStatement(SQL_DELETE_ALL_PRODUCTS);
			int i = st.executeUpdate();
			System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_PRODUCT);
	        }
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
	}
	
	public void init() throws ClassNotFoundException, SQLException{
		run = new QueryRunner();
		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:~/.klerk/klerk", "sa", "");
//		conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
	}
	
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
}
