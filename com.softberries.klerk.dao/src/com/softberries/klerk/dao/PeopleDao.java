package com.softberries.klerk.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.softberries.klerk.dao.to.Person;

public class PeopleDao extends GenericDao<Person>{

	@Override
	public List<Person> findAll()  throws SQLException{
		return new ArrayList<Person>();
	}
	@Override
	public Person find(Long id)  throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update(Person t)  throws SQLException{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Long id)  throws SQLException{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteAll()  throws SQLException{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void create(Person t) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
