package com.softberries.klerk.gui.editors;

import com.softberries.klerk.dao.to.IDocumentType;

public class SinglePurchaseEditor extends SingleDocumentEditor {

	public static final String ID = "com.softberries.klerk.gui.editors.SinglePurchaseEditor"; //$NON-NLS-1$

	@Override
	public int getDocumentType() {
		return IDocumentType.INVOICE_PURCHASE;
	}
	
}
