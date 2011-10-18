package com.softberries.klerk.gui.editors;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.softberries.klerk.dao.to.IDocumentType;
import com.softberries.klerk.gui.helpers.CompanyFactory;

public class SinglePurchaseEditor extends SingleDocumentEditor {

	public static final String ID = "com.softberries.klerk.gui.editors.SinglePurchaseEditor"; //$NON-NLS-1$

	@Override
	public int getDocumentType() {
		return IDocumentType.INVOICE_PURCHASE;
	}
}
