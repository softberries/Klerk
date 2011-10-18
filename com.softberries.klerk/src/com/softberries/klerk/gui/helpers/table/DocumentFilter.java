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

public class DocumentFilter extends SimpleKlerkFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Document p = (Document) element;
		if (p.getNotes().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getPlaceCreated().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getTitle().matches("(?i).*" + searchString + ".*")) {
			return true;
		}

		return false;
	}
}
