package com.softberries.klerk.dao.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.softberries.klerk.dao.AddressDao;
import com.softberries.klerk.dao.CompanyDao;
import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Company;

public class AddressDaoTest {
	@BeforeClass
	public static void beforeClass() throws Exception{
		System.out.println("beforeClass()");
		AddressDao dao = new AddressDao();
		CompanyDao daoC = new CompanyDao();
		Company c = new Company();
		c.setName("name");
		c.setVatid("vatid");
		daoC.create(c);
		for(int i = 1; i< 11;i++){
			Address p = new Address();
			p.setCity("Elblag");
			p.setCompany_id(new Long(1));
			p.setCountry("Poland");
			p.setFlatNumber("5");
			p.setHouseNumber("3");
			p.setPostCode("post code");
			p.setStreet("street");
			p.setCompany_id(c.getId());
			dao.create(p);
		}
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		System.out.println("afterClass()");
		AddressDao dao = new AddressDao();
		dao.deleteAll();
		CompanyDao daoc = new CompanyDao();
		daoc.deleteAll();
	}

	/**
	 * Test method for {@link com.softberries.klerk.dao.test.AddressDao#findAllProducts()}.
	 * @throws SQLException 
	 */
	@Test
	public void testFindAllProducts() throws SQLException {
		System.out.println("testFindAllAddresses");
		AddressDao dao = new AddressDao();
		List<Address> products = dao.findAll();
		assertTrue(products.size() >= 10);
	}

	/**
	 * Test method for {@link com.softberries.klerk.dao.test.AddressDao#createProduct(com.softberries.klerk.dao.test.to.Address)}.
	 * @throws SQLException 
	 */
	@Test
	public void testCreateProduct() throws SQLException {
		System.out.println("testCreateProduct");
		AddressDao dao = new AddressDao();
		Address p = new Address();
		p.setCity("Elblag");
		CompanyDao daoC = new CompanyDao();
		Company c = new Company();
		c.setName("name");
		c.setVatid("vatid");
		daoC.create(c);
		p.setCompany_id(c.getId());
		p.setCountry("Poland");
		p.setFlatNumber("5");
		p.setHouseNumber("3");
		p.setPostCode("post code");
		p.setStreet("street");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		dao.delete(p.getId());
	}
	@Test
	public void testFindProductById() throws SQLException{
		System.out.println("findProductById");
		AddressDao dao = new AddressDao();
		//create Address
		Address p = new Address();
		p.setCity("Elblag");
		CompanyDao daoC = new CompanyDao();
		Company c = new Company();
		c.setName("name");
		c.setVatid("vatid");
		daoC.create(c);
		p.setCompany_id(c.getId());
		p.setCountry("Poland");
		p.setFlatNumber("5");
		p.setHouseNumber("3");
		p.setPostCode("post code");
		p.setStreet("street");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		//find Address
		Address pf = dao.find(p.getId());
		assertNotNull(pf);
		//clean the db
		dao.delete(p.getId());
	}
	@Test
	public void testUpdateProduct() throws SQLException{
		System.out.println("updateAddress");
		AddressDao dao = new AddressDao();
		//crate Address
		Address p = new Address();
		p.setCity("Elblag");
		CompanyDao daoC = new CompanyDao();
		Company c = new Company();
		c.setName("name");
		c.setVatid("vatid");
		daoC.create(c);
		p.setCompany_id(c.getId());
		p.setCountry("Poland");
		p.setFlatNumber("5");
		p.setHouseNumber("3");
		p.setPerson_id(null);
		p.setPostCode("post code");
		p.setStreet("street");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		//update Address
		p.setStreet("ABCD");
		dao.update(p);
		//find Address
		Address pf = dao.find(p.getId());
		assertNotNull(pf);
		assertTrue(pf.getId().longValue() == p.getId().longValue());
		assertTrue(pf.getStreet().equals("ABCD"));
		//delete Address
		dao.delete(pf.getId());
	}
}
