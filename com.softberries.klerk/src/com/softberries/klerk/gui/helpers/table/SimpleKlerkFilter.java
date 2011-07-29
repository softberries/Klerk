package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.ViewerFilter;

public abstract class SimpleKlerkFilter extends ViewerFilter {
	
	protected String searchString;

	public void setSearchText(String s) {
		// Search must be a substring of the existing value
		this.searchString = ".*" + s + ".*";
	}
}
