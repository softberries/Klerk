package com.softberries.klerk.gui.helpers.table;

import java.util.List;

import com.softberries.klerk.dao.ProductDao;
import com.softberries.klerk.dao.to.Product;

public enum ProductsModelProvider {
	INSTANCE;

	private List<Product> products;

	private ProductsModelProvider() {
		products = new ProductDao().findAllProducts();
	}

	public List<Product> getProducts() {
		return products;
	}

}
