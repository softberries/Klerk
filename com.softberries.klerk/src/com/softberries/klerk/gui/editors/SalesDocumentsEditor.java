/*******************************************************************************
 * Copyright (c) 2011 Softberries Krzysztof Grajek.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Softberries Krzysztof Grajek - initial API and implementation
 ******************************************************************************/
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
