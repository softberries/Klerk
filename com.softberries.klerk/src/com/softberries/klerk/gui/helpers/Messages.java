package com.softberries.klerk.gui.helpers;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.softberries.klerk.gui.helpers.messages"; //$NON-NLS-1$
	public static String CategoriesView_all_Categories;
	public static String CategoriesView_companies;
	public static String CategoriesView_documents;
	public static String CategoriesView_inventory;
	public static String CategoriesView_invoices;
	public static String CategoriesView_messages;
	public static String CategoriesView_products;
	public static String DatabasePreferencePage_Database_connection_settings;
	public static String DatabasePreferencePage_database_name;
	public static String DatabasePreferencePage_database_password;
	public static String DatabasePreferencePage_database_url;
	public static String DatabasePreferencePage_database_username;
	public static String DocumentsEditor_creator;
	public static String DocumentsEditor_date_created;
	public static String DocumentsEditor_due_date;
	public static String DocumentsEditor_notes;
	public static String DocumentsEditor_place_created;
	public static String DocumentsEditor_search;
	public static String DocumentsEditor_title;
	public static String DocumentsEditor_transaction_date;
	public static String ProductsEditor_Code;
	public static String ProductsEditor_Description;
	public static String ProductsEditor_Name;
	public static String ProductsEditor_Search;
	public static String SingleDocumentEditor_Base_Price;
	public static String SingleDocumentEditor_Buyer;
	public static String SingleDocumentEditor_created_by;
	public static String SingleDocumentEditor_Code;
	public static String SingleDocumentEditor_Created_Date;
	public static String SingleDocumentEditor_due_date;
	public static String SingleDocumentEditor_INVOICE;
	public static String SingleDocumentEditor_Invoice_Items;
	public static String SingleDocumentEditor_Invoice_main_properties;
	public static String SingleDocumentEditor_Invoice_Summary;
	public static String SingleDocumentEditor_Main;
	public static String SingleDocumentEditor_Name;
	public static String SingleDocumentEditor_Notes;
	public static String SingleDocumentEditor_Preferences;
	public static String SingleDocumentEditor_Price_Gross;
	public static String SingleDocumentEditor_Price_Net;
	public static String SingleDocumentEditor_Quantity;
	public static String SingleDocumentEditor_Selected;
	public static String SingleDocumentEditor_Seller;
	public static String SingleDocumentEditor_Summary;
	public static String SingleDocumentEditor_Tax;
	public static String SingleDocumentEditor_Tax_Percent;
	public static String SingleDocumentEditor_Title;
	public static String SingleDocumentEditor_Transaction_Date;
	public static String SingleProductEditor_Description;
	public static String SingleProductEditor_Main;
	public static String SingleProductEditor_Name;
	public static String SingleProductEditor_PRODUCT;
	public static String SingleProductEditor_Product_Description;
	public static String SingleProductEditor_Product_main_properties;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
