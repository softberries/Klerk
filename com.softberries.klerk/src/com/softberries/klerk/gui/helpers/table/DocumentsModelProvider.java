package com.softberries.klerk.gui.helpers.table;

import java.sql.SQLException;
import java.util.List;

import com.softberries.klerk.dao.to.*;
import com.softberries.klerk.dao.*;


public enum DocumentsModelProvider {
	INSTANCE;

	private List<Document> documents;

	private DocumentsModelProvider() {
		try {
			documents = new DocumentDao().findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Document> getDocuments() {
		return documents;
	}

}
