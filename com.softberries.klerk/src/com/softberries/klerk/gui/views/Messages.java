package com.softberries.klerk.gui.views;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$
	public static String CategoriesView_all_Categories;
	public static String CategoriesView_documents;
	public static String CategoriesView_inventory;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
