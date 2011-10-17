package com.softberries.klerk.gui.helpers.table;

import java.sql.SQLException;
import java.util.List;

import com.softberries.klerk.dao.PeopleDao;
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.gui.editors.GenericKlerkEditor;

public enum PeopleModelProvider  {
	INSTANCE;

	private List<Person> people;

	private PeopleModelProvider() {
		try {
			people = new PeopleDao(GenericKlerkEditor.DB_FOLDER_PATH).findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Person> getPeople() {
		return people;
	}
}
