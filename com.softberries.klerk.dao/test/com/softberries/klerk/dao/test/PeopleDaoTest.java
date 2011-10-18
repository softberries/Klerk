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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.softberries.klerk.dao.PeopleDao;
import com.softberries.klerk.dao.to.Address;
import com.softberries.klerk.dao.to.Person;

public class PeopleDaoTest {
	private static final String databaseFilePath = "~/.klerk/klerk_test";
	
	@BeforeClass
	public static void beforeClass() throws Exception{
		System.out.println("beforeClass()");
		for(int i = 1; i< 11;i++){
			createPerson(i);
		}
	}

	/**
	 * @param dao
	 * @param i
	 * @throws SQLException
	 */
	public static Person createPerson(int i) throws SQLException {
		PeopleDao dao = new PeopleDao(databaseFilePath);
		Person p1 = new Person();
		p1.setFirstName("ABC" + i);
		p1.setLastName("Product Testowy " + i);
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
		PeopleDao dao = new PeopleDao(databaseFilePath);
		dao.deleteAll();
	}
	@Test
	public void testFindAllPeople() throws SQLException {
		System.out.println("testFindAllPeople");
		PeopleDao dao = new PeopleDao(databaseFilePath);
		List<Person> people = dao.findAll();
		assertTrue(people.size() >= 10);
	}
	@Test
	public void testFindPerson() throws SQLException{
		System.out.println("testfindPerson");
		PeopleDao dao = new PeopleDao(databaseFilePath);
		Person c = createPerson(1234);
		assertTrue(c.getId().longValue() > 0);
		Person cc = dao.find(c.getId());
		assertNotNull(cc);
	}
	@Test
	public void testCreatePerson() throws SQLException {
		System.out.println("testCreatePerson");
		PeopleDao dao = new PeopleDao(databaseFilePath);
		Person p = createPerson(213);
		assertTrue(p.getId().longValue() > 0);
		dao.delete(p.getId());
	}
	@Test
	public void testUpdatePerson(){
		System.out.println("updatePerson");
		try{
		PeopleDao dao = new PeopleDao(databaseFilePath);
		//crate person
		Person p = createPerson(23);
		assertTrue(p.getId().longValue() > 0);
		//update person
		p.setFirstName("ABCD");
		dao.update(p);
		//find person
		Person pf = dao.find(p.getId());
		assertNotNull(pf);
		assertTrue(pf.getId().longValue() == p.getId().longValue());
		assertTrue(pf.getFirstName().equals("ABCD"));
		//delete person
		dao.delete(pf.getId());
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
