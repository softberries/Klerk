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
import com.softberries.klerk.dao.to.Product;

public class ProductComparator extends SimpleKlerkComparator {
	
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Product p1 = (Product) e1;
		Product p2 = (Product) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getCode().compareTo(p2.getCode());
			break;
		case 1:
			rc = p1.getDescription().compareTo(p2.getDescription());
			break;
		case 2:
			rc = p1.getName().compareTo(p2.getName());
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
