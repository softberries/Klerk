package com.softberries.klerk.gui.helpers;

import org.eclipse.swt.graphics.Image;

import com.softberries.klerk.Activator;

public interface IImageKeys {

	public static final String ALL_CATEGORIES = "icons/networking.png";
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
	public static final String CATEGORY_INVOICES = "icons/networking.png";
	public static final String CATEGORY_PRODUCTS = "icons/networking.png";
	public static final String CATEGORY_COMPANIES = "icons/networking.png";
	public static final String CATEGORY_PEOPLE = "icons/networking.png";
	public static final String CATEGORY_DOCUMENTS = "icons/networking.png";
	public static final String CATEGORY_INVENTORY = "icons/networking.png";
	public static final String CATEGORY_ADMINISTRATION = "icons/networking.png";
}
