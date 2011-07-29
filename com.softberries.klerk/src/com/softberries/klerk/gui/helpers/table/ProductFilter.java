package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.softberries.klerk.dao.to.Product;

public class ProductFilter extends SimpleKlerkFilter {

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