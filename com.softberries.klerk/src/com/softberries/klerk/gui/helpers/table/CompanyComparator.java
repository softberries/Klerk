package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;

import com.softberries.klerk.dao.to.Company;

public class CompanyComparator extends SimpleKlerkComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Company c1 = (Company) e1;
		Company c2 = (Company) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = c1.getName().compareTo(c2.getName());
			break;
		case 1:
			rc = c1.getVatid().compareTo(c2.getVatid());
			break;
		case 2:
			rc = c1.getEmail().compareTo(c2.getEmail());
			break;
		case 3:
			rc = c1.getWww().compareTo(c2.getWww());
			break;
		case 4:
			rc = c1.getTelephone().compareTo(c2.getTelephone());
			break;
		case 5:
			rc = c1.getMobile().compareTo(c2.getMobile());
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