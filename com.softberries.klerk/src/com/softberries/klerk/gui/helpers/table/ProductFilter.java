package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.softberries.klerk.dao.to.Product;

public class ProductFilter extends ViewerFilter {

	private String searchString;

	public void setSearchText(String s) {
		// Search must be a substring of the existing value
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Product p = (Product) element;
		if (p.getCode().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getDescription().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getName().matches("(?i).*" + searchString + ".*")) {
			return true;
		}

		return false;
	}
}