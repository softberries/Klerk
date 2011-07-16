package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.softberries.klerk.dao.to.Product;

public class ProductComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public ProductComparator() {
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