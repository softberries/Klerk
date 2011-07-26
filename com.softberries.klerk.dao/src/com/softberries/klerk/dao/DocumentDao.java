package com.softberries.klerk.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.DocumentItem;
import com.softberries.klerk.dao.to.Person;
import com.softberries.klerk.dao.to.Product;

public class DocumentDao {

	public List<Document> findAllDocuments(){
		List<Document> documents = new ArrayList<Document>();
		for(int i = 1; i< 300;i++){
			Document d = new Document();
			d.setId(new Long(i));
			Person p = new Person();
			p.setFirstName("Krzysztof");
			p.setLastName("Grajek");
			d.setCreator(p);
			d.setCreatedDate(new Date());
			d.setTransactionDate(new Date());
			d.setDueDate(new Date());
			d.setNotes("notes for the document " + i);
			d.setPlaceCreated("Gdańsk");
			d.setTitle("Faktura Sprzedaży " + i);
			List<DocumentItem> dis = new ArrayList<DocumentItem>();
			for(int j = 0; j < 10; j++){
				DocumentItem di = new DocumentItem();
				di.setDocumentId(d.getId());
				di.setId(new Long(j));
				di.setPriceGrossSingle("" + j + ".23");
				di.setPriceNetSingle("" + j + ".00");
				di.setPriceTaxSingle("0.23");
				di.setPriceGrossAll("" + j + ".23");
				di.setPriceNetAll("" + j + ".00");
				di.setPriceTaxAll("0.23");
				Product pr = new Product();
				pr.setCode("ABC"+j);
				pr.setDescription("description " + j);
				pr.setId(new Long(j));
				pr.setName("Product Name " + j);
				di.setProduct(pr);
				di.setQuantity("1.00");
				di.setTaxValue("23");
				dis.add(di);
			}
			d.setItems(dis);
			documents.add(d);
		}
		return documents;
	}
}
