package com.softberries.klerk.gui.editors;

import com.softberries.klerk.dao.to.IDocumentType;

public class SalesDocumentsEditor extends DocumentsEditor{

	public static final String ID = "com.softberries.klerk.gui.editors.SalesDocumentsEditor"; //$NON-NLS-1$
	
	public SalesDocumentsEditor(int DOC_TYPE) {
		super(DOC_TYPE);
	}
	public SalesDocumentsEditor(){
		super(IDocumentType.INVOICE_SALE);
	}
	@Override
	public int getDocumentType() {
		return IDocumentType.INVOICE_SALE;
	}
	
}
