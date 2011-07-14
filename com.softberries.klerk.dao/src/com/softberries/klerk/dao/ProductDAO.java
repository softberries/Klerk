package com.softberries.klerk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;

import com.softberries.klerk.dao.entity.Product;


public class ProductDAO {
	private static final String PERSISTENCE_UNIT = "com.softberries.klerk.pu";

	private EntityManagerFactory factory = null;
	
	/**
	 * <property name="eclipselink.ddl-generation" value="drop-and-create-tables"/>
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/klerk"/>
			<property name="javax.persistence.jdbc.user" value="root"/>
			<property name="javax.persistence.jdbc.password" value="adminadmin"/>
	 */
	public ProductDAO(){
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("eclipselink.ddl-generation", "drop-and-create-tables");
//		params.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
//		params.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/klerk");
//		params.put("javax.persistence.jdbc.user", "root");
//		params.put("javax.persistence.jdbc.password", "adminadmin");
		params.put(PersistenceUnitProperties.CLASSLOADER, ProductDAO.class.getClassLoader());
//		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, params);
		factory = new PersistenceProvider().createEntityManagerFactory( PERSISTENCE_UNIT, params);
	}
	public List<Product> findAllProducts(){
		EntityManager em = factory.createEntityManager();
		Query query = em.createQuery("select p from Product p");
		List<Product> products = query.getResultList();
		em.close();
		return products;
	}
	public void createProduct(String code, String name){
		EntityManager em = factory.createEntityManager();
//		Product p = new Product();
//		p.setName(name);
//		p.setCode(code);
		em.getTransaction().begin();
		Query query = em.createNativeQuery("insert into PRODUCT (name, code) values ('first product', 'zya')");
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
}
