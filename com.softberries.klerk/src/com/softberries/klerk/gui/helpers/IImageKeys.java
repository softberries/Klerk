package com.softberries.klerk.gui.helpers;

import org.eclipse.swt.graphics.Image;

import com.softberries.klerk.Activator;

public interface IImageKeys {

	public static final String ALL_CATEGORIES = "icons/png/database.png";
	public static final String MAIN_CATEGORY = "icons/document_library.png";
	public static final String DELETE_DOC_ITEM = "icons/del_doc_item.png";
	public static final String CHECKED_KEY = "icons/checked.gif";
	public static final String UNCHECKED_KEY = "icons/unchecked.gif";
	//ready images
	public static final Image CHECKED = Activator.getImageDescriptor(IImageKeys.CHECKED_KEY).createImage();
	public static final Image UNCHECKED = Activator.getImageDescriptor(IImageKeys.UNCHECKED_KEY).createImage();
	public static final String ADD_ITEM = "icons/networking.png";
	public static final String EDIT_ITEM = "icons/networking.png";
	public static final String REFRESH_ALL = "icons/networking.png";
	public static final String CATEGORY_INVOICES = "icons/png/invoices.png";
	public static final String CATEGORY_PRODUCTS = "icons/png/products.png";
	public static final String CATEGORY_COMPANIES = "icons/png/companies.png";
	public static final String CATEGORY_PEOPLE = "icons/png/people.png";
	public static final String CATEGORY_DOCUMENTS = "icons/png/documents.png";
	public static final String CATEGORY_INVENTORY = "icons/png/inventory.png";
	public static final String CATEGORY_ADMINISTRATION = "icons/png/admin.png";
	public static final String CATEGORY_INVOICES_PURCHASE = "icons/png/invoices_purchases.png";
	public static final String CATEGORY_INVOICES_SALES = "icons/png/invoices_sales.png";
}
