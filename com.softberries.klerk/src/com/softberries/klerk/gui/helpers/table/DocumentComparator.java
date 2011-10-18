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

import org.eclipse.jface.viewers.Viewer;
import com.softberries.klerk.dao.to.Document;

public class DocumentComparator extends SimpleKlerkComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Document p1 = (Document) e1;
		Document p2 = (Document) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getTitle().compareTo(p2.getTitle());
			break;
		case 1:
			rc = p1.getCreator().getFirstName().compareTo(p2.getCreator().getFirstName());
			break;
		case 2:
			rc = p1.getCreatedDate().compareTo(p2.getCreatedDate());
			break;
		case 3:
			rc = p1.getTransactionDate().compareTo(p2.getTransactionDate());
			break;
		case 4:
			rc = p1.getDueDate().compareTo(p2.getDueDate());
			break;
		case 5:
			rc = p1.getNotes().compareTo(p2.getNotes());
			break;
		case 6:
			rc = p1.getPlaceCreated().compareTo(p2.getPlaceCreated());
			break;
		default:
			rc = 0;
		}
		// If descending order, flip the direction
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}

}
