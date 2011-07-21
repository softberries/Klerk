package com.softberries.klerk.gui.helpers.table.editingsupport;

import org.eclipse.jface.viewers.TableViewer;

import com.softberries.klerk.calc.DocumentCalculator;
import com.softberries.klerk.dao.to.DocumentItem;

public class DocumentItemPriceGrossAllES extends DocumentItemCellEditingSupport{

	public DocumentItemPriceGrossAllES(TableViewer viewer) {
		super(viewer);
	}

	@Override
	protected Object getValue(Object element) {
		return ((DocumentItem) element).getPriceGrossAll();
	}

	@Override
	protected void setValue(Object element, Object value) {
		DocumentItem di = ((DocumentItem) element);
		di = new DocumentCalculator().calculateByPriceGrossAll(di, value);
		viewer.refresh();
	}

}
