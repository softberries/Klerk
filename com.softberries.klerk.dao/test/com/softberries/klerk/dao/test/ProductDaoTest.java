package com.softberries.klerk.dao.test;
/**
 * 
 */


import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.softberries.klerk.dao.ProductDao;
import com.softberries.klerk.dao.to.Product;

/**
 * @author kris
 *
 */
public class ProductDaoTest {

	@BeforeClass
	public static void beforeClass() throws Exception{
		System.out.println("beforeClass()");
		ProductDao dao = new ProductDao();
		for(int i = 1; i< 11;i++){
			Product p1 = new Product();
			p1.setCode("ABC" + i);
			p1.setName("Product Testowy " + i);
			p1.setDescription("Opis produktu testowego numer " + i);
			dao.createProduct(p1);
		}
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		System.out.println("afterClass()");
		ProductDao dao = new ProductDao();
		dao.deleteAllProducts();
	}

	/**
	 * Test method for {@link com.softberries.klerk.dao.test.ProductDao#findAllProducts()}.
	 * @throws SQLException 
	 */
	@Test
	public void testFindAllProducts() throws SQLException {
		System.out.println("testFindAllProducts");
		ProductDao dao = new ProductDao();
		List<Product> products = dao.findAllProducts();
		assertTrue(products.size() >= 10);
	}

	/**
	 * Test method for {@link com.softberries.klerk.dao.test.ProductDao#createProduct(com.softberries.klerk.dao.test.to.Product)}.
	 * @throws SQLException 
	 */
	@Test
	public void testCreateProduct() throws SQLException {
		System.out.println("testCreateProduct");
		ProductDao dao = new ProductDao();
		Product p = new Product();
		p.setCode("ABC");
		p.setName("NAME");
		p.setDescription("DESCRIPTION");
		dao.createProduct(p);
		assertTrue(p.getId().longValue() > 0);
		dao.deleteProduct(p.getId());
	}
	@Test
	public void testFindProductById() throws SQLException{
		System.out.println("findProductById");
		ProductDao dao = new ProductDao();
		//create product
		Product p = new Product();
		p.setName("TO_DEL");
		p.setCode("TO_DEL");
		dao.createProduct(p);
		assertTrue(p.getId().longValue() > 0);
		//find product
		Product pf = dao.findProduct(p.getId());
		assertNotNull(pf);
		//clean the db
		dao.deleteProduct(p.getId());
	}

}
