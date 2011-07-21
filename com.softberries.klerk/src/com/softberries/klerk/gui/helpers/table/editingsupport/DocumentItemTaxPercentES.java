package com.softberries.klerk.gui.helpers.table.editingsupport;

import org.eclipse.jface.viewers.TableViewer;

import com.softberries.klerk.calc.DocumentCalculator;
import com.softberries.klerk.dao.to.DocumentItem;

public class DocumentItemTaxPercentES extends DocumentItemCellEditingSupport{

	public DocumentItemTaxPercentES(TableViewer viewer) {
		super(viewer);
	}

	@Override
	protected Object getValue(Object element) {
		return ((DocumentItem) element).getTaxValue();
	}

	@Override
	protected void setValue(Object element, Object value) {
		DocumentItem di = ((DocumentItem) element);
		di = new DocumentCalculator().calculateByTaxPercent(di, value);
		viewer.refresh();
	}
}
