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

public class SinglePurchaseEditor extends SingleDocumentEditor {

	public static final String ID = "com.softberries.klerk.gui.editors.SinglePurchaseEditor"; //$NON-NLS-1$

	@Override
	public int getDocumentType() {
		return IDocumentType.INVOICE_PURCHASE;
	}
}
