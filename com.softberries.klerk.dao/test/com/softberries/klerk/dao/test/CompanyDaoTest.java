package com.softberries.klerk.dao.test;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.to.Company;

public class CompanyDaoTest {

	@BeforeClass
	public static void beforeClass() throws Exception{
		System.out.println("beforeClass()");
		CompanyDao dao = new CompanyDao();
		for(int i = 1; i< 11;i++){
			Company p1 = new Company();
			p1.setVatid("ABC" + i);
			p1.setName("Product Testowy " + i);
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
		CompanyDao dao = new CompanyDao();
		dao.deleteAll();
	}
	@Test
	public void testFindAllCompanies() throws SQLException {
		System.out.println("testFindAllCompanies");
		CompanyDao dao = new CompanyDao();
		List<Company> companies = dao.findAll();
		assertTrue(companies.size() >= 10);
	}
	@Test
	public void testFindCompany() throws SQLException{
		System.out.println("testfindCompany");
		CompanyDao dao = new CompanyDao();
		Company c = new Company();
		c.setName("company name");
		c.setVatid("vatid");
		dao.create(c);
		assertTrue(c.getId().longValue() > 0);
		Company cc = dao.find(c.getId());
		assertNotNull(cc);
	}
	@Test
	public void testCreateCompany() throws SQLException {
		System.out.println("testCreateCompany");
		CompanyDao dao = new CompanyDao();
		Company p = new Company();
		p.setName("company name");
		p.setVatid("vatid");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		dao.delete(p.getId());
	}
	@Test
	public void testUpdateCompany(){
		System.out.println("updateCompany");
		try{
		CompanyDao dao = new CompanyDao();
		//crate product
		Company p = new Company();
		p.setName("company name");
		p.setVatid("vatid");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		//update product
		p.setName("ABCD");
		dao.update(p);
		//find product
		Company pf = dao.find(p.getId());
		assertNotNull(pf);
		assertTrue(pf.getId().longValue() == p.getId().longValue());
		assertTrue(pf.getName().equals("ABCD"));
		//delete product
		dao.delete(pf.getId());
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
