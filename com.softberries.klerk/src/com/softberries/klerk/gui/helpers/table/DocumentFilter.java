package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.softberries.klerk.dao.to.Document;

public class DocumentFilter extends SimpleKlerkFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Document p = (Document) element;
		if (p.getNotes().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getPlaceCreated().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getTitle().matches("(?i).*" + searchString + ".*")) {
			return true;
		}

		return false;
	}
}