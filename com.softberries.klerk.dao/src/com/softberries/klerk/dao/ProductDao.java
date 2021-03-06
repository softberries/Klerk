/*******************************************************************************
 * Copyright (c) 2011 Softberries Krzysztof Grajek.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Softberries Krzysztof Grajek - initial API and implementation
 ******************************************************************************/
package com.softberries.klerk.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.softberries.klerk.dao.to.Product;

public class ProductDao extends GenericDao<Product>{

	private static final String SQL_INSERT_PRODUCT = "INSERT INTO PRODUCT(code, name, description) VALUES(?, ?, ?)";
	private static final String SQL_DELETE_PRODUCT = "DELETE FROM PRODUCT WHERE id = ?";
	private static final String SQL_FIND_PRODUCT_BY_ID = "SELECT * FROM PRODUCT WHERE id = ?";
	private static final String SQL_DELETE_ALL_PRODUCTS = "DELETE FROM PRODUCT WHERE id > 0";
	private static final String SQL_FIND_PRODUCT_ALL = "SELECT * FROM PRODUCT";
	private static final String SQL_UPDATE_PRODUCT = "UPDATE PRODUCT SET code = ?, name = ?, description = ? WHERE id = ?";
	
	public ProductDao(String databasefilepath) {
		super(databasefilepath);
	}
	
	@Override
	public List<Product> findAll() throws SQLException {
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

	@Override
	public Product find(Long id) throws SQLException {
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
	public Product find(Long id, QueryRunner run1, Connection conn1) throws SQLException {
		Product p = null;
		try{
			init();
			ResultSetHandler<Product> h = new BeanHandler<Product>(Product.class);
			p = run1.query(conn1, SQL_FIND_PRODUCT_BY_ID, h, id); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public void update(Product p) throws SQLException {
		try {
			init();
			st = conn.prepareStatement(SQL_UPDATE_PRODUCT); 
			st.setString(1, p.getCode());
			st.setString(2, p.getName());
			st.setString(3, p.getDescription());
	        st.setLong(4, p.getId());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_UPDATE_PRODUCT);
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
			st = conn.prepareStatement(SQL_DELETE_PRODUCT); 
	        st.setLong(1, id);
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_PRODUCT);
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
			st = conn.prepareStatement(SQL_DELETE_ALL_PRODUCTS);
			int i = st.executeUpdate();
			System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_ALL_PRODUCTS);
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
	public void create(Product p) throws SQLException {
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
