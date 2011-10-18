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

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.dao.to.IDocumentType;
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;

public class DocumentDao extends GenericDao<Document>{

	private static final String SQL_INSERT_DOCUMENT = "INSERT INTO DOCUMENT(title, notes, createdDate, transactionDate, dueDate, placeCreated, documentType, creator_id, buyer_id, seller_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_DOCUMENT = "DELETE FROM DOCUMENT WHERE id = ?";
	private static final String SQL_FIND_DOCUMENT_BY_ID = "SELECT * FROM DOCUMENT WHERE id = ?";
	private static final String SQL_FIND_DOCUMENT_BY_TYPE = "SELECT * FROM DOCUMENT WHERE documentType = ?";
	private static final String SQL_DELETE_ALL_DOCUMENTS = "DELETE FROM DOCUMENT WHERE id > 0";
	private static final String SQL_FIND_DOCUMENT_ALL = "SELECT * FROM DOCUMENT";
	private static final String SQL_UPDATE_DOCUMENT = "UPDATE DOCUMENT SET title = ?, notes = ?, createdDate = ?, transactionDate = ?, dueDate = ?, placeCreated = ?, documentType = ?, creator_id = ?, buyer_id = ?, seller_id = ? WHERE id = ?";
	
	public DocumentDao(String databasefilepath) {
		super(databasefilepath);
	}
	@Override
	public List<Document> findAll() throws SQLException {
		List<Document> documents = new ArrayList<Document>();
		try{
			init();
			ResultSetHandler<List<Document>> h = new BeanListHandler<Document>(Document.class);
			documents = run.query(conn, SQL_FIND_DOCUMENT_ALL, h); 
			//find items
			DocumentItemDao idao = new DocumentItemDao(this.filePath);
			CompanyDao cdao = new CompanyDao(this.filePath);
			PeopleDao pdao = new PeopleDao(this.filePath);
			
			for(Document d : documents){
				d.setItems(idao.findAllByDocumentId(d.getId(), run, conn));
				if(d.getDocumentType() == IDocumentType.INVOICE_PURCHASE){
					d.setSeller(cdao.find(d.getSeller_id(), run, conn, st, generatedKeys));
				}else{
					d.setBuyer(cdao.find(d.getBuyer_id(), run, conn, st, generatedKeys));
				}
				d.setCreator(pdao.find(d.getCreator_id(), run, conn, st, generatedKeys));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return documents;
	}
	public List<Document> findAllByType(final int DOC_TYPE) throws SQLException{
		List<Document> documents = new ArrayList<Document>();
		try{
			init();
			ResultSetHandler<List<Document>> h = new BeanListHandler<Document>(Document.class);
			documents = run.query(conn, SQL_FIND_DOCUMENT_BY_TYPE, h, DOC_TYPE); 
			//find items
			DocumentItemDao idao = new DocumentItemDao(this.filePath);
			CompanyDao cdao = new CompanyDao(this.filePath);
			PeopleDao pdao = new PeopleDao(this.filePath);
			
			for(Document d : documents){
				d.setItems(idao.findAllByDocumentId(d.getId(), run, conn));
				if(d.getDocumentType() == IDocumentType.INVOICE_PURCHASE){
					d.setSeller(cdao.find(d.getSeller_id(), run, conn, st, generatedKeys));
				}else{
					d.setBuyer(cdao.find(d.getBuyer_id(), run, conn, st, generatedKeys));
				}
				d.setCreator(pdao.find(d.getCreator_id(), run, conn, st, generatedKeys));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return documents;
	}
	@Override
	public Document find(Long id) throws SQLException {
		Document d = null;
		try{
			init();
			ResultSetHandler<Document> h = new BeanHandler<Document>(Document.class);
			d = run.query(conn, SQL_FIND_DOCUMENT_BY_ID, h, id); 
			//find items, creator, seller and buyer
			DocumentItemDao idao = new DocumentItemDao(this.filePath);
			CompanyDao cdao = new CompanyDao(this.filePath);
			PeopleDao pdao = new PeopleDao(this.filePath);
			d.setItems(idao.findAllByDocumentId(d.getId(), run, conn));
			if(d.getDocumentType() == IDocumentType.INVOICE_PURCHASE){
				d.setSeller(cdao.find(d.getSeller_id(), run, conn, st, generatedKeys));
			}else{
				d.setBuyer(cdao.find(d.getBuyer_id(), run, conn, st, generatedKeys));
			}
			d.setCreator(pdao.find(d.getCreator_id(), run, conn, st, generatedKeys));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(conn, st, generatedKeys);
		}
		return d;
	}

	@Override
	public void create(Document d) throws SQLException {
		try {
			init();
	        st = conn.prepareStatement(SQL_INSERT_DOCUMENT, Statement.RETURN_GENERATED_KEYS); 
	        st.setString(1, d.getTitle());
	        st.setString(2, d.getNotes());
	        st.setDate(3, new java.sql.Date(d.getCreatedDate().getTime()));
	        st.setDate(4, new java.sql.Date(d.getTransactionDate().getTime()));
	        st.setDate(5, new java.sql.Date(d.getDueDate().getTime()));
	        st.setString(6, d.getPlaceCreated());
	        st.setInt(7, d.getDocumentType());
	        st.setLong(8, d.getCreator().getId());
	        st.setLong(9, d.getBuyer().getId());
	        st.setLong(10, d.getSeller().getId());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_INSERT_DOCUMENT);
	        }
	        generatedKeys = st.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            d.setId(generatedKeys.getLong(1));
	        } else {
	            throw new SQLException("Creating document failed, no generated key obtained.");
	        }
	      //if the document creation was successful, add document items
	        DocumentItemDao idao = new DocumentItemDao(this.filePath);
	        for(DocumentItem di : d.getItems()){
	        	di.setDocument_id(d.getId());
	        	idao.create(di, run, conn, generatedKeys);
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
	public void update(Document d) throws SQLException {
		try {
			init();
	        st = conn.prepareStatement(SQL_UPDATE_DOCUMENT, Statement.RETURN_GENERATED_KEYS); 
	        st.setString(1, d.getTitle());
	        st.setString(2, d.getNotes());
	        st.setDate(3, new java.sql.Date(d.getCreatedDate().getTime()));
	        st.setDate(4, new java.sql.Date(d.getTransactionDate().getTime()));
	        st.setDate(5, new java.sql.Date(d.getDueDate().getTime()));
	        st.setString(6, d.getPlaceCreated());
	        st.setInt(7, d.getDocumentType());
	        st.setLong(8, d.getCreator().getId());
	        st.setLong(9, d.getBuyer().getId());
	        st.setLong(10, d.getSeller().getId());
	        st.setLong(11, d.getId());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_UPDATE_DOCUMENT);
	        }
	        
	        //delete unused items
	        DocumentItemDao idao = new DocumentItemDao(this.filePath);
			List<DocumentItem> toDel = new ArrayList<DocumentItem>();
			if(d.getId() != null){
				List<DocumentItem> existingItems = idao.findAllByDocumentId(d.getId(), run, conn);
				System.out.println("Existing items: " + existingItems.size());
				for(DocumentItem di : existingItems){
					System.out.println("di id: " + di.getId().longValue() + ", di docid: " + di.getDocument_id().longValue());
					System.out.println(printAllDIs(d.getItems()));
					if(!d.getItems().contains(di)){
						System.out.println("add to remove: " + di);
						toDel.add(di);
					}
				}
			}
			for(DocumentItem di : toDel){
				idao.delete(di.getId(), conn);
			}
			//update items
			for(DocumentItem di : d.getItems()){
				if(di.getId() != null && di.getId() > 0){
					//update
					System.out.println("update: " + di);
					idao.update(di, run, conn);
				}else{//create
					di.setDocument_id(d.getId());
					idao.create(di, run, conn, generatedKeys);
					System.out.println("insert: " + di);
				}
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

	private String printAllDIs(List<DocumentItem> items) {
		StringBuilder builder = new StringBuilder();
		for(DocumentItem di : items){
			Long diId = di.getId();
			String temp = "di id: " + diId + ", di docid: " + di.getDocument_id().longValue();
			builder.append(temp);
			builder.append("\n");
		}
		return builder.toString();
	}
	@Override
	public void delete(Long id) throws SQLException {
		//delete items
		Document toDel = find(id);
		DocumentItemDao iDao = new DocumentItemDao(this.filePath);
		try {
			init();
			for(DocumentItem di : toDel.getItems()){
				iDao.delete(di.getId(), conn);
			}
			st = conn.prepareStatement(SQL_DELETE_DOCUMENT); 
	        st.setLong(1, id);
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_DOCUMENT);
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
			List<Document> docs = findAll();
			for(Document d : docs){
				delete(d.getId());
			}
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
