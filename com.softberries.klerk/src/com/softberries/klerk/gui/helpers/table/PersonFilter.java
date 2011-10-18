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
import com.softberries.klerk.dao.to.Person;


public class PersonFilter extends SimpleKlerkFilter {

	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Person p = (Person) element;
		if (p.getFirstName().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getLastName().matches("(?i).*" + searchString + ".*")) {
			return true;
		}
		if (p.getEmail().matches("(?i).*" + searchString + ".*")) {
			return true;
		}

		return false;
	}
}
