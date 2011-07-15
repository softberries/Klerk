package com.softberries.klerk.dao;

import java.util.ArrayList;
import java.util.List;

import com.softberries.klerk.dao.to.Product;

public class ProductDao {

	public List<Product> findAllProducts(){
		List<Product> products = new ArrayList<Product>();
		Product p1 = new Product();
		p1.setCode("ABC");
		p1.setName("Product Testowy 1");
		p1.setDescription("Opis produktu testowego numer 1");
		products.add(p1);
		Product p2 = new Product();
		p2.setCode("ABC");
		p2.setName("Product Testowy 1");
		p2.setDescription("Opis produktu testowego numer 1");
		products.add(p2);
		Product p3 = new Product();
		p3.setCode("ABC");
		p3.setName("Product Testowy 1");
		p3.setDescription("Opis produktu testowego numer 1");
		products.add(p3);
		Product p4 = new Product();
		p4.setCode("ABC");
		p4.setName("Product Testowy 1");
		p4.setDescription("Opis produktu testowego numer 1");
		products.add(p4);
		return products;
	}
}
