package com.softberries.klerk.gui.helpers.table;

import java.sql.SQLException;
import java.util.List;

import com.softberries.klerk.dao.to.*;
import com.softberries.klerk.dao.*;
import com.softberries.klerk.gui.editors.GenericKlerkEditor;


public enum DocumentsModelProvider {
	INSTANCE;

	private List<Document> documents;

	private DocumentsModelProvider() {
		
	}

	public List<Document> getDocuments(int DOC_TYPE, final boolean refresh) {
		if(!refresh && documents == null){
			return documents;
		}else{
			try {
				documents = new DocumentDao(GenericKlerkEditor.DB_FOLDER_PATH).findAllByType(DOC_TYPE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return documents;
		}
	}

}
