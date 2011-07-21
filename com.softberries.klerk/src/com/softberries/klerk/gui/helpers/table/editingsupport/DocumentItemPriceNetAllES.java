package com.softberries.klerk.gui.helpers.table.editingsupport;
import org.eclipse.jface.viewers.TableViewer;

import com.softberries.klerk.calc.DocumentCalculator;
import com.softberries.klerk.dao.to.DocumentItem;


public class DocumentItemPriceNetAllES extends DocumentItemCellEditingSupport {

	public DocumentItemPriceNetAllES(TableViewer viewer) {
		super(viewer);
	}
	@Override
	protected Object getValue(Object element) {
		return ((DocumentItem) element).getPriceNetAll();
	}

	@Override
	protected void setValue(Object element, Object value) {
		DocumentItem di = ((DocumentItem) element);
		di = new DocumentCalculator().calculateByPriceNetAll(di, value);
		viewer.refresh();
	}
}
