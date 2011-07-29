package com.softberries.klerk.gui.helpers.table;

import java.sql.SQLException;
import java.util.List;

import com.softberries.klerk.dao.PeopleDao;
import com.softberries.klerk.dao.to.Person;

public enum PeopleModelProvider  {
	INSTANCE;

	private List<Person> people;

	private PeopleModelProvider() {
		try {
			people = new PeopleDao().findAllPeople();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Person> getPeople() {
		return people;
	}
}
