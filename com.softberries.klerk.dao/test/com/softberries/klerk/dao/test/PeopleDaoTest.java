package com.softberries.klerk.dao.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.softberries.klerk.dao.PeopleDao;
import com.softberries.klerk.dao.to.Person;

public class PeopleDaoTest {
	@BeforeClass
	public static void beforeClass() throws Exception{
		System.out.println("beforeClass()");
		PeopleDao dao = new PeopleDao();
		for(int i = 1; i< 11;i++){
			Person p1 = new Person();
			p1.setFirstName("ABC" + i);
			p1.setLastName("Product Testowy " + i);
			p1.setEmail("email " + i);
			p1.setMobile("mobile " + i);
			p1.setTelephone("telepone " + i);
			dao.create(p1);
		}
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		System.out.println("afterClass()");
		PeopleDao dao = new PeopleDao();
		dao.deleteAll();
	}
	@Test
	public void testFindAllCompanies() throws SQLException {
		System.out.println("testFindAllCompanies");
		PeopleDao dao = new PeopleDao();
		List<Person> companies = dao.findAll();
		assertTrue(companies.size() >= 10);
	}
	@Test
	public void testFindCompany() throws SQLException{
		System.out.println("testfindCompany");
		PeopleDao dao = new PeopleDao();
		Person c = new Person();
		c.setFirstName("company name");
		c.setLastName("vatid");
		dao.create(c);
		assertTrue(c.getId().longValue() > 0);
		Person cc = dao.find(c.getId());
		assertNotNull(cc);
	}
	@Test
	public void testCreateCompany() throws SQLException {
		System.out.println("testCreateCompany");
		PeopleDao dao = new PeopleDao();
		Person p = new Person();
		p.setFirstName("company name");
		p.setLastName("vatid");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		dao.delete(p.getId());
	}
	@Test
	public void testUpdateCompany(){
		System.out.println("updateCompany");
		try{
		PeopleDao dao = new PeopleDao();
		//crate product
		Person p = new Person();
		p.setFirstName("company name");
		p.setLastName("vatid");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		//update product
		p.setFirstName("ABCD");
		dao.update(p);
		//find product
		Person pf = dao.find(p.getId());
		assertNotNull(pf);
		assertTrue(pf.getId().longValue() == p.getId().longValue());
		assertTrue(pf.getFirstName().equals("ABCD"));
		//delete product
		dao.delete(pf.getId());
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
