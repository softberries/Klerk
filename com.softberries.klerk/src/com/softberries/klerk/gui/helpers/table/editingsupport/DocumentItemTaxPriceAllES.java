package com.softberries.klerk.gui.helpers.table.editingsupport;

import org.eclipse.jface.viewers.TableViewer;

import com.softberries.klerk.calc.DocumentCalculator;
import com.softberries.klerk.dao.to.DocumentItem;

public class DocumentItemTaxPriceAllES extends DocumentItemCellEditingSupport{

	public DocumentItemTaxPriceAllES(TableViewer viewer) {
		super(viewer);
	}

	@Override
	protected Object getValue(Object element) {
		return ((DocumentItem) element).getPriceTaxAll();
	}

	@Override
	protected void setValue(Object element, Object value) {
		DocumentItem di = ((DocumentItem) element);
		di = new DocumentCalculator().calculateByPriceTaxAll(di, value);
		viewer.refresh();
	}

}
