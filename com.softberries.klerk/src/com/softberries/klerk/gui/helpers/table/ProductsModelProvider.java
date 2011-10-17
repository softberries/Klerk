package com.softberries.klerk.gui.helpers.table;

import java.sql.SQLException;
import java.util.List;

import com.softberries.klerk.dao.ProductDao;
import com.softberries.klerk.dao.to.Product;
import com.softberries.klerk.gui.editors.GenericKlerkEditor;

public enum ProductsModelProvider {
	INSTANCE;

	private List<Product> products;

	private ProductsModelProvider() {
		try {
			products = new ProductDao(GenericKlerkEditor.DB_FOLDER_PATH).findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Product> getProducts() {
		return products;
	}

}
