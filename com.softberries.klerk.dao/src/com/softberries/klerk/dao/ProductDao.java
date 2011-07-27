package com.softberries.klerk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import com.softberries.klerk.dao.to.Product;

public class ProductDao {

	public List<Product> findAllProducts(){
		List<Product> products = new ArrayList<Product>();
		for(int i = 1; i< 300;i++){
			Product p1 = new Product();
			p1.setCode("ABC" + i);
			p1.setName("Product Testowy " + i);
			p1.setDescription("Opis produktu testowego numer " + i);
			products.add(p1);
		}
		return products;
	}
	public void init(){
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/.klerk/klerk", "sa", "");
	        // add application code here
	        conn.close();
	        System.out.println("connection closed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] art){
		ProductDao dao = new ProductDao();
		dao.init();
	}
}
