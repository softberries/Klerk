package com.softberries.klerk.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.Person;

public class DocumentDao {

	public List<Document> findAllDocuments(){
		List<Document> documents = new ArrayList<Document>();
		for(int i = 1; i< 300;i++){
			Document d = new Document();
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
			documents.add(d);
		}
		return documents;
	}
}
