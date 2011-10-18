/*******************************************************************************
 * Copyright (c) 2011 Softberries Krzysztof Grajek.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Softberries Krzysztof Grajek - initial API and implementation
 ******************************************************************************/
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
