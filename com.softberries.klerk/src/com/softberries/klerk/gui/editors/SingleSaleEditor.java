package com.softberries.klerk.gui.editors;

import com.softberries.klerk.dao.to.IDocumentType;

public class SingleSaleEditor extends SingleDocumentEditor {

	public static final String ID = "com.softberries.klerk.gui.editors.SingleSaleEditor"; //$NON-NLS-1$

	@Override
	public int getDocumentType() {
		return IDocumentType.INVOICE_SALE;
	}
}
