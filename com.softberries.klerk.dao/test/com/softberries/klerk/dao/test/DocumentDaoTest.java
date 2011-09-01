package com.softberries.klerk.dao.test;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.DocumentDao;
import com.softberries.klerk.dao.DocumentDao;
import com.softberries.klerk.dao.PeopleDao;
import com.softberries.klerk.dao.ProductDao;
import com.softberries.klerk.dao.to.Company;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;

public class DocumentDaoTest {
	private static final String databaseFilePath = "~/.klerk/klerk_test";
	
	@BeforeClass
	public static void beforeClass() throws Exception{
		System.out.println("beforeClass()");
		CompanyDaoTest.createCompany(2322);
		PeopleDaoTest.createPerson(223);
		ProductDaoTest.createProduct(23);
		for(int i = 1; i< 11;i++){
			createDocument(i);
		}
	}
	
	private static Document createDocument(int i) throws SQLException {
		PeopleDao pdao =new PeopleDao(databaseFilePath);
		ProductDao prDao = new ProductDao(databaseFilePath);
		DocumentDao dao = new DocumentDao(databaseFilePath);
		CompanyDao cdao = new CompanyDao(databaseFilePath);
		Document d = new Document();
		d.setId(new Long(i));
		Person p = pdao.findAll().get(0);
		d.setCreator(p);
		d.setCreatedDate(new Date());
		d.setTransactionDate(new Date());
		d.setDueDate(new Date());
		d.setNotes("notes for the document " + i);
		d.setPlaceCreated("Gdańsk");
		d.setTitle("Faktura Sprzedaży " + i);
		Company c = cdao.findAll().get(0);
		d.setBuyer(c);
		d.setSeller(c);
		List<DocumentItem> dis = new ArrayList<DocumentItem>();
		for(int j = 0; j < 10; j++){
			DocumentItem di = new DocumentItem();
			di.setDocument_id(d.getId());
			di.setId(new Long(j));
			di.setPriceGrossSingle("" + j + ".23");
			di.setPriceNetSingle("" + j + ".00");
			di.setPriceTaxSingle("0.23");
			di.setPriceGrossAll("" + j + ".23");
			di.setPriceNetAll("" + j + ".00");
			di.setPriceTaxAll("0.23");
			Product pr = prDao.findAll().get(0);
			di.setProduct(pr);
			di.setProduct_name("product name");
			di.setQuantity("1.00");
			di.setTaxValue("23");
			dis.add(di);
		}
		d.setItems(dis);
		dao.create(d);
		return d;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		System.out.println("afterClass()");
		DocumentDao dao = new DocumentDao(databaseFilePath);
		dao.deleteAll();
		CompanyDao cdao = new CompanyDao(databaseFilePath);
		PeopleDao pdao = new PeopleDao(databaseFilePath);
		ProductDao prDao = new ProductDao(databaseFilePath);
		cdao.deleteAll();
		pdao.deleteAll();
		prDao.deleteAll();
	}
	@Test
	public void testFindAllDocuments() throws SQLException {
		System.out.println("testFindAllDocuments");
		DocumentDao dao = new DocumentDao(databaseFilePath);
		List<Document> documents = dao.findAll();
		assertTrue(documents.size() >= 10);
	}
	@Test
	public void testFindDocument() throws SQLException{
		System.out.println("testfindDocument");
		DocumentDao dao = new DocumentDao(databaseFilePath);
		PeopleDao pdao = new PeopleDao(databaseFilePath);
		ProductDao prDao = new ProductDao(databaseFilePath);
		Document d = createDocument(333);
		assertTrue(d.getId().longValue() > 0);
		Document cc = dao.find(d.getId());
		assertNotNull(cc);
	}
	@Test
	public void testCreateDocument() throws SQLException {
		System.out.println("testCreateDocument");
		DocumentDao dao = new DocumentDao(databaseFilePath);
		
		Document d = createDocument(444);
		
		dao.create(d);
		assertTrue(d.getId().longValue() > 0);
		dao.delete(d.getId());
	}
	@Test
	public void testUpdateDocument(){
		System.out.println("updateDocument");
		try{
		DocumentDao dao = new DocumentDao(databaseFilePath);
		//crate document
		Document d = createDocument(555);

		List<DocumentItem> items = d.getItems();
		System.out.println("size 1: " + items.size());
		assertTrue(d.getId().longValue() > 0);
		//update document
		items.remove(0);
		System.out.println("size 2: " + items.size());
		d.setItems(items);
		dao.update(d);
		//find document
		Document pf = new DocumentDao(databaseFilePath).find(d.getId());
		assertNotNull(pf);
		System.out.println("size 3: " + pf.getItems().size());
		assertTrue(pf.getId().longValue() == d.getId().longValue());
		assertTrue(pf.getItems().size() == 9);
		//delete document
		dao.delete(pf.getId());
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	@Test
	public void testListDifference(){
		List<DocumentItem> list1 = new ArrayList<DocumentItem>();
		List<DocumentItem> list2 = new ArrayList<DocumentItem>();
		DocumentItem di = new DocumentItem();
		di.setId(new Long("1"));
		di.setDocument_id(new Long("1"));
		list1.add(di);
		DocumentItem di2 = new DocumentItem();
		di2.setId(new Long("1"));
		di2.setDocument_id(new Long("1"));
		list2.add(di2);
		assertTrue(list1.contains(di2));
	}
}
