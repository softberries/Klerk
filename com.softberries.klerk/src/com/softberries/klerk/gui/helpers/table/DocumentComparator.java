package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.softberries.klerk.dao.to.Document;

public class DocumentComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public DocumentComparator() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}

	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

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
			rc = p1.getDateCreated().compareTo(p2.getDateCreated());
			break;
		case 3:
			rc = p1.getNotes().compareTo(p2.getNotes());
			break;
		case 4:
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