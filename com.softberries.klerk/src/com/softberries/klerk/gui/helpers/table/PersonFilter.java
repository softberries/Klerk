package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.softberries.klerk.dao.to.Person;


public class PersonFilter extends SimpleKlerkFilter {

	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Person p = (Person) element;
		if (p.getFirstName().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getLastName().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getEmail().matches("(?i).*" + searchString + ".*")) {
			return true;
		}

		return false;
	}
}