package com.softberries.klerk.gui.editors;

import com.softberries.klerk.dao.to.IDocumentType;

public class PurchaseDocumentsEditor extends DocumentsEditor{

	public static final String ID = "com.softberries.klerk.gui.editors.PurchaseDocumentsEditor"; //$NON-NLS-1$
	
	public PurchaseDocumentsEditor(int DOC_TYPE) {
		super(DOC_TYPE);
	}
	public PurchaseDocumentsEditor(){
		super(IDocumentType.INVOICE_PURCHASE);
	}
	@Override
	public int getDocumentType() {
		return IDocumentType.INVOICE_PURCHASE;
	}

}
