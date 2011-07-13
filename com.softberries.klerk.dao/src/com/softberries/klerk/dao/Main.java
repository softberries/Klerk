package com.softberries.klerk.dao;

import com.softberries.klerk.dao.entity.Product;


public class Main {

	public static void main(String art[]){
		ProductDAO dao = new ProductDAO();
		Product p = new Product();
		p.setCode("ABC");
		p.setName("testowy produkt");
		dao.createProduct(p);
	}
}
