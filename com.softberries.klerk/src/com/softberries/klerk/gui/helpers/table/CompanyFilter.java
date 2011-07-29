package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;

import com.softberries.klerk.dao.to.Company;

public class CompanyFilter extends SimpleKlerkFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Company c = (Company) element;
		if (c.getName().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (c.getEmail().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (c.getWww().matches("(?i).*" + searchString + ".*")) {
			return true;
		}

		return false;
	}
}
