package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;
import com.softberries.klerk.dao.to.Person;


public class PersonComparator extends SimpleKlerkComparator {
	
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Person p1 = (Person) e1;
		Person p2 = (Person) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getFirstName().compareTo(p2.getFirstName());
			break;
		case 1:
			rc = p1.getLastName().compareTo(p2.getLastName());
			break;
		case 2:
			rc = p1.getTelephone().compareTo(p2.getTelephone());
			break;
		case 3:
			rc = p1.getMobile().compareTo(p2.getMobile());
			break;
		case 4:
			rc = p1.getEmail().compareTo(p2.getEmail());
			break;
		case 5:
			rc = p1.getWww().compareTo(p2.getWww());
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