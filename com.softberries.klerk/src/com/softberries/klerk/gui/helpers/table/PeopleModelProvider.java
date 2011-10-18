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
