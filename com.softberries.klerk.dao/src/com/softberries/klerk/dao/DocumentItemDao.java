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
import com.softberries.klerk.dao.to.DocumentItem;

public class DocumentItemDao {
	
	private String path;
	
	public DocumentItemDao(String path){
		this.path = path;
	}
	private static final String SQL_INSERT_DOCUMENTITEM = "INSERT INTO DOCUMENTITEM(priceNetSingle, priceGrossSingle, priceTaxSingle, priceNetAll, priceGrossAll, priceTaxAll, taxValue, quantity, product_id, document_id, product_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_DOCUMENTITEM = "DELETE FROM DOCUMENTITEM WHERE id = ?";
	private static final String SQL_FIND_DOCUMENTITEM_BY_ID = "SELECT * FROM DOCUMENTITEM WHERE id = ?";
	private static final String SQL_DELETE_ALL_DOCUMENTITEMS = "DELETE FROM DOCUMENTITEM WHERE id > 0";
	private static final String SQL_FIND_DOCUMENTITEM_ALL = "SELECT * FROM DOCUMENTITEM";
	private static final String SQL_FIND_DOCUMENTITEM_ALL_BY_DOCUMENT_ID = "SELECT * FROM DOCUMENTITEM WHERE DOCUMENT_ID = ?";
	private static final String SQL_UPDATE_DOCUMENTITEM = "UPDATE DOCUMENTITEM SET priceNetSingle = ?, priceGrossSingle = ?, priceTaxSingle = ?, priceNetAll = ?, priceGrossAll = ?, priceTaxAll = ?, taxValue = ?, quantity = ?, product_id = ?, document_id = ?, product_name = ? WHERE id = ?";
	
	public List<DocumentItem> findAll(QueryRunner run, Connection conn) throws SQLException {
		List<DocumentItem> items = new ArrayList<DocumentItem>();
		ResultSetHandler<List<DocumentItem>> h = new BeanListHandler<DocumentItem>(DocumentItem.class);
		items = run.query(conn, SQL_FIND_DOCUMENTITEM_ALL, h); 
		ProductDao pdao = new ProductDao(path);
		for(DocumentItem di : items){
			di.setProduct(pdao.find(di.getProduct_id(), run, conn));
		}
		return items;
	}
	public List<DocumentItem> findAllByDocumentId(Long docId,QueryRunner run, Connection conn) throws SQLException {
		List<DocumentItem> items = new ArrayList<DocumentItem>();
		ResultSetHandler<List<DocumentItem>> h = new BeanListHandler<DocumentItem>(DocumentItem.class);
		items = run.query(conn, SQL_FIND_DOCUMENTITEM_ALL_BY_DOCUMENT_ID, h, docId); 
		ProductDao pdao = new ProductDao(this.path);
		for(DocumentItem di : items){
			di.setProduct(pdao.find(di.getProduct_id(), run, conn));
		}
		return items;
	}

	public DocumentItem find(Long id, QueryRunner run, Connection conn) throws SQLException {
		DocumentItem p = null;
		ResultSetHandler<DocumentItem> h = new BeanHandler<DocumentItem>(DocumentItem.class);
		p = run.query(conn, SQL_FIND_DOCUMENTITEM_BY_ID, h, id); 
		ProductDao pdao = new ProductDao(this.path);
		p.setProduct(pdao.find(p.getProduct_id(), run, conn));
		return p;
	}

	//priceNetSingle, priceGrossSingle, priceTaxSingle, priceNetAll, priceGrossAll, priceTaxAll, taxValue, quantity, product_id, document_id
	public void create(DocumentItem c, QueryRunner run, Connection conn, ResultSet generatedKeys) throws SQLException {
			PreparedStatement st = conn.prepareStatement(SQL_INSERT_DOCUMENTITEM, Statement.RETURN_GENERATED_KEYS); 
	        st.setString(1, c.getPriceNetSingle());
	        st.setString(2, c.getPriceGrossSingle());
	        st.setString(3, c.getPriceTaxSingle());
	        st.setString(4, c.getPriceNetAll());
	        st.setString(5, c.getPriceGrossAll());
	        st.setString(6, c.getPriceTaxAll());
	        st.setString(7, c.getTaxValue());
	        st.setString(8, c.getQuantity());
	        if(c.getProduct().getId().longValue() == 0 && c.getDocument_id().longValue() == 0){
	        	throw new SQLException("For DocumentItem corresponding product and document it belongs to need to be specified");
	        }
	        if(c.getProduct().getId() != 0){
	        	st.setLong(9, c.getProduct().getId());
	        }else{
	        	st.setNull(9, java.sql.Types.NUMERIC);
	        }
	        if(c.getDocument_id().longValue() != 0){
	        	st.setLong(10, c.getDocument_id());
	        }else{
	        	st.setNull(10, java.sql.Types.NUMERIC);
	        }
	        st.setString(11, c.getProduct().getName());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_INSERT_DOCUMENTITEM);
	        }
	        generatedKeys = st.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            c.setId(generatedKeys.getLong(1));
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }
	}

	public void update(DocumentItem c, QueryRunner run, Connection conn) throws SQLException {
			PreparedStatement st = conn.prepareStatement(SQL_UPDATE_DOCUMENTITEM); 
			st.setString(1, c.getPriceNetSingle());
	        st.setString(2, c.getPriceGrossSingle());
	        st.setString(3, c.getPriceTaxSingle());
	        st.setString(4, c.getPriceNetAll());
	        st.setString(5, c.getPriceGrossAll());
	        st.setString(6, c.getPriceTaxAll());
	        st.setString(7, c.getTaxValue());
	        st.setString(8, c.getQuantity());
	        if(c.getProduct().getId().longValue() == 0 && c.getDocument_id().longValue() == 0){
	        	throw new SQLException("For DocumentItem corresponding product and document it belongs to need to be specified");
	        }
	        if(c.getProduct().getId() != 0){
	        	st.setLong(9, c.getProduct().getId());
	        }else{
	        	st.setNull(9, java.sql.Types.NUMERIC);
	        }
	        if(c.getDocument_id().longValue() != 0){
	        	st.setLong(10, c.getDocument_id());
	        }else{
	        	st.setNull(10, java.sql.Types.NUMERIC);
	        }
	        st.setString(11, c.getProduct().getName());
	        st.setLong(12, c.getId());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_UPDATE_DOCUMENTITEM);
	        }
	}

	public void delete(Long id, Connection conn) throws SQLException {
		PreparedStatement st = conn.prepareStatement(SQL_DELETE_DOCUMENTITEM); 
        st.setLong(1, id);
        // run the query
        int i = st.executeUpdate();    
        System.out.println("i: " + i);
        if (i == -1) {
            System.out.println("db error : " + SQL_DELETE_DOCUMENTITEM);
        }
	}

	public void deleteAll(Connection conn) throws SQLException {
		PreparedStatement st = conn.prepareStatement(SQL_DELETE_ALL_DOCUMENTITEMS);
		int i = st.executeUpdate();
		System.out.println("i: " + i);
        if (i == -1) {
            System.out.println("db error : " + SQL_DELETE_ALL_DOCUMENTITEMS);
        }
	}
}
