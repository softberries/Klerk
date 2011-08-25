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
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;

public class DocumentDao extends GenericDao<Document>{

	private static final String SQL_INSERT_DOCUMENT = "INSERT INTO DOCUMENT(title, notes, createdDate, transactionDate, dueDate, placeCreated, creator_id, buyer_id, seller_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_DOCUMENT = "DELETE FROM DOCUMENT WHERE id = ?";
	private static final String SQL_FIND_DOCUMENT_BY_ID = "SELECT * FROM DOCUMENT WHERE id = ?";
	private static final String SQL_DELETE_ALL_DOCUMENTS = "DELETE FROM DOCUMENT WHERE id > 0";
	private static final String SQL_FIND_DOCUMENT_ALL = "SELECT * FROM DOCUMENT";
	private static final String SQL_UPDATE_DOCUMENT = "UPDATE DOCUMENT SET title = ?, notes = ?, createdDate = ?, transactionDate = ?, dueDate = ?, placeCreated = ?, creator_id = ?, buyer_id = ?, seller_id = ? WHERE id = ?";
	
	public List<Document> findAllDocuments(){
		List<Document> documents = new ArrayList<Document>();
		for(int i = 1; i< 300;i++){
			Document d = new Document();
			d.setId(new Long(i));
			Person p = new Person();
			p.setFirstName("Krzysztof");
			p.setLastName("Grajek");
			d.setCreator(p);
			d.setCreatedDate(new Date());
			d.setTransactionDate(new Date());
			d.setDueDate(new Date());
			d.setNotes("notes for the document " + i);
			d.setPlaceCreated("Gdańsk");
			d.setTitle("Faktura Sprzedaży " + i);
			List<DocumentItem> dis = new ArrayList<DocumentItem>();
			for(int j = 0; j < 10; j++){
				DocumentItem di = new DocumentItem();
				di.setDocumentId(d.getId());
				di.setId(new Long(j));
				di.setPriceGrossSingle("" + j + ".23");
				di.setPriceNetSingle("" + j + ".00");
				di.setPriceTaxSingle("0.23");
				di.setPriceGrossAll("" + j + ".23");
				di.setPriceNetAll("" + j + ".00");
				di.setPriceTaxAll("0.23");
				Product pr = new Product();
				pr.setCode("ABC"+j);
				pr.setDescription("description " + j);
				pr.setId(new Long(j));
				pr.setName("Product Name " + j);
				di.setProduct(pr);
				di.setQuantity("1.00");
				di.setTaxValue("23");
				dis.add(di);
			}
			d.setItems(dis);
			documents.add(d);
		}
		return documents;
	}

	@Override
	public List<Document> findAll() throws SQLException {
		List<Document> documents = new ArrayList<Document>();
		try{
			init();
			ResultSetHandler<List<Document>> h = new BeanListHandler<Document>(Document.class);
			documents = run.query(conn, SQL_FIND_DOCUMENT_ALL, h); 
			//find items
			DocumentItemDao idao = new DocumentItemDao();
			CompanyDao cdao = new CompanyDao();
			PeopleDao pdao = new PeopleDao();
			
			for(Document d : documents){
				d.setItems(idao.findAllByDocumentId(d.getId(), run, conn));
				d.setBuyer(cdao.find(d.getBuyer_id(), run, conn, st, generatedKeys));
				d.setSeller(cdao.find(d.getSeller_id(), run, conn, st, generatedKeys));
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
			DocumentItemDao idao = new DocumentItemDao();
			CompanyDao cdao = new CompanyDao();
			PeopleDao pdao = new PeopleDao();
			d.setItems(idao.findAllByDocumentId(d.getId(), run, conn));
			d.setBuyer(cdao.find(d.getBuyer_id(), run, conn, st, generatedKeys));
			d.setSeller(cdao.find(d.getSeller_id(), run, conn, st, generatedKeys));
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
	        st.setLong(7, d.getCreator().getId());
	        st.setLong(8, d.getBuyer().getId());
	        st.setLong(9, d.getSeller().getId());
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
	      //if the document creation was successful, add addresses
	        DocumentItemDao idao = new DocumentItemDao();
	        for(DocumentItem di : d.getItems()){
	        	di.setDocumentId(d.getId());
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
	        st.setLong(7, d.getCreator().getId());
	        st.setLong(8, d.getBuyer().getId());
	        st.setLong(9, d.getSeller().getId());
	        st.setLong(10, d.getId());
	        // run the query
	        int i = st.executeUpdate();    
	        System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_UPDATE_DOCUMENT);
	        }
	        
	        //delete unused items
	        DocumentItemDao idao = new DocumentItemDao();
			List<DocumentItem> toDel = new ArrayList<DocumentItem>();
			if(d.getId() != null){
				List<DocumentItem> existingItems = idao.findAllByDocumentId(d.getId(), run, conn);
				for(DocumentItem di : existingItems){
					if(!d.getItems().contains(di)){
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
					idao.update(di, run, conn);
				}else{//create
					di.setDocumentId(d.getId());
					idao.create(di, run, conn, generatedKeys);
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

	@Override
	public void delete(Long id) throws SQLException {
		//delete items
		Document toDel = find(id);
		AddressDao iDao = new AddressDao();
		for(DocumentItem di : toDel.getItems()){
			iDao.delete(di.getId(), conn);
		}
		try {
			init();
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
			init();
			st = conn.prepareStatement(SQL_DELETE_ALL_DOCUMENTS);
			int i = st.executeUpdate();
			System.out.println("i: " + i);
	        if (i == -1) {
	            System.out.println("db error : " + SQL_DELETE_ALL_DOCUMENTS);
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
