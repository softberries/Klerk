package com.softberries.klerk.dao.test;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Company;

public class CompanyDaoTest {
	private static final String databaseFilePath = "~/.klerk/klerk_test";
	
	@BeforeClass
	public static void beforeClass() throws Exception{
		System.out.println("beforeClass()");
		
		for(int i = 1; i< 11;i++){
			createCompany(i);
		}
	}

	/**
	 * @param i
	 * @throws SQLException
	 */
	public static Company createCompany(int i) throws SQLException {
		CompanyDao dao = new CompanyDao(databaseFilePath);
		Company p1 = new Company();
		p1.setVatid("ABC" + i);
		p1.setName("Product Testowy " + i);
		p1.setEmail("email " + i);
		p1.setMobile("mobile " + i);
		p1.setTelephone("telepone " + i);
		Address adr = new Address();
		adr.setCity("asdfasd");
		adr.setCountry("adsfasdF");
		adr.setFlatNumber("2");
		adr.setHouseNumber("23");
		adr.setPostCode("1234");
		adr.setStreet("Asdfadsf");
		List<Address> adrs = new ArrayList<Address>();
		adrs.add(adr);
		p1.setAddresses(adrs);
		dao.create(p1);
		return p1;
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		System.out.println("afterClass()");
		CompanyDao dao = new CompanyDao(databaseFilePath);
		dao.deleteAll();
	}
	@Test
	public void testFindAllCompanies() throws SQLException {
		System.out.println("testFindAllCompanies");
		CompanyDao dao = new CompanyDao(databaseFilePath);
		List<Company> companies = dao.findAll();
		assertTrue(companies.size() >= 10);
	}
	@Test
	public void testFindCompany() throws SQLException{
		System.out.println("testfindCompany");
		CompanyDao dao = new CompanyDao(databaseFilePath);
		Company c = createCompany(234);
		assertTrue(c.getId().longValue() > 0);
		Company cc = dao.find(c.getId());
		assertNotNull(cc);
	}
	@Test
	public void testCreateCompany() throws SQLException {
		System.out.println("testCreateCompany");
		CompanyDao dao = new CompanyDao(databaseFilePath);
		Company p = createCompany(22);
		assertTrue(p.getId().longValue() > 0);
		dao.delete(p.getId());
	}
	@Test
	public void testUpdateCompany(){
		System.out.println("updateCompany");
		try{
		CompanyDao dao = new CompanyDao(databaseFilePath);
		//crate company
		Company p = createCompany(2322);
		assertTrue(p.getId().longValue() > 0);
		//update company
		p.setName("ABCD");
		dao.update(p);
		//find company
		Company pf = dao.find(p.getId());
		assertNotNull(pf);
		assertTrue(pf.getId().longValue() == p.getId().longValue());
		assertTrue(pf.getName().equals("ABCD"));
		//delete company
		dao.delete(pf.getId());
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
