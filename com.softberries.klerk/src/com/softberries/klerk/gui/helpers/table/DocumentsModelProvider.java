package com.softberries.klerk.gui.helpers.table;

import java.util.List;

import com.softberries.klerk.dao.test.*;
import com.softberries.klerk.dao.to.*;
import com.softberries.klerk.dao.*;


public enum DocumentsModelProvider {
	INSTANCE;

	private List<Document> documents;

	private DocumentsModelProvider() {
		documents = new DocumentDao().findAllDocuments();
	}

	public List<Document> getDocuments() {
		return documents;
	}

}
