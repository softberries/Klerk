package com.softberries.klerk.gui.helpers.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.softberries.klerk.dao.to.DocumentItem;

public class DocumentItemComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public DocumentItemComparator() {
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
		DocumentItem p1 = (DocumentItem) e1;
		DocumentItem p2 = (DocumentItem) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = p1.getProduct().getCode().compareTo(p2.getProduct().getCode());
			break;
		case 1:
			rc = p1.getProduct().getName().compareTo(p2.getProduct().getName());
			break;
		case 2:
			rc = p1.getQuantity().compareTo(p2.getQuantity());
			break;
		case 3:
			rc = p1.getPriceNetSingle().compareTo(p2.getPriceNetSingle());
			break;
		case 4:
			rc = p1.getTaxValue().compareTo(p2.getTaxValue());
			break;
		case 5:
			rc = p1.getPriceTaxSingle().compareTo(p2.getPriceTaxSingle());
			break;
		case 6:
			rc = p1.getPriceGrossSingle().compareTo(p2.getPriceGrossSingle());
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