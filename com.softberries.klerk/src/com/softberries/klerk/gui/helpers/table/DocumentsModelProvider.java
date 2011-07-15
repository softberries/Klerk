package com.softberries.klerk.gui.helpers.table;

import java.util.List;
import com.softberries.klerk.dao.to.*;
import com.softberries.klerk.dao.*;


public enum DocumentsModelProvider {
	INSTANCE;

	private List<Product> products;

	private DocumentsModelProvider() {
		products = new ProductDao().findAllProducts();
	}

	public List<Product> getProducts() {
		return products;
	}

}
