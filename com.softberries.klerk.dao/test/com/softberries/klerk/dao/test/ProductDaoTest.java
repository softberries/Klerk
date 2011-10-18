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

	private static final String databaseFilePath = "~/.klerk/klerk_test";
	
	@BeforeClass
	public static void beforeClass() throws Exception{
		System.out.println("beforeClass()");
		
		for(int i = 1; i< 11;i++){
			createProduct(i);
		}
	}

	/**
	 * @param i
	 * @throws SQLException
	 */
	public static Product createProduct(int i) throws SQLException {
		ProductDao dao = new ProductDao(databaseFilePath);
		Product p1 = new Product();
		p1.setCode("ABC" + i);
		p1.setName("Product Testowy " + i);
		p1.setDescription("Opis produktu testowego numer " + i);
		dao.create(p1);
		return p1;
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void afterClass() throws Exception {
		System.out.println("afterClass()");
		ProductDao dao = new ProductDao(databaseFilePath);
		dao.deleteAll();
	}

	/**
	 * Test method for {@link com.softberries.klerk.dao.test.ProductDao#findAllProducts()}.
	 * @throws SQLException 
	 */
	@Test
	public void testFindAllProducts() throws SQLException {
		System.out.println("testFindAllProducts");
		ProductDao dao = new ProductDao(databaseFilePath);
		List<Product> products = dao.findAll();
		assertTrue(products.size() >= 10);
	}

	/**
	 * Test method for {@link com.softberries.klerk.dao.test.ProductDao#createProduct(com.softberries.klerk.dao.test.to.Product)}.
	 * @throws SQLException 
	 */
	@Test
	public void testCreateProduct() throws SQLException {
		System.out.println("testCreateProduct");
		ProductDao dao = new ProductDao(databaseFilePath);
		Product p = new Product();
		p.setCode("ABC");
		p.setName("NAME");
		p.setDescription("DESCRIPTION");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		dao.delete(p.getId());
	}
	@Test
	public void testFindProductById() throws SQLException{
		System.out.println("findProductById");
		ProductDao dao = new ProductDao(databaseFilePath);
		//create product
		Product p = new Product();
		p.setName("TO_DEL");
		p.setCode("TO_DEL");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		//find product
		Product pf = dao.find(p.getId());
		assertNotNull(pf);
		//clean the db
		dao.delete(p.getId());
	}
	@Test
	public void testUpdateProduct() throws SQLException{
		System.out.println("updateProduct");
		ProductDao dao = new ProductDao(databaseFilePath);
		//crate product
		Product p = new Product();
		p.setName("TO_DEL");
		p.setCode("TO_DEL");
		dao.create(p);
		assertTrue(p.getId().longValue() > 0);
		//update product
		p.setCode("ABCD");
		dao.update(p);
		//find product
		Product pf = dao.find(p.getId());
		assertNotNull(pf);
		assertTrue(pf.getId().longValue() == p.getId().longValue());
		assertTrue(pf.getCode().equals("ABCD"));
		//delete product
		dao.delete(pf.getId());
	}

}
