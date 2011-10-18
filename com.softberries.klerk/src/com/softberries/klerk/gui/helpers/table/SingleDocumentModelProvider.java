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
package com.softberries.klerk.gui.helpers.table;

import java.util.ArrayList;
import java.util.List;

import com.softberries.klerk.calc.DocumentCalculator;
import com.softberries.klerk.dao.to.Document;
import com.softberries.klerk.dao.to.VatLevelItem;

public class SingleDocumentModelProvider {

	private Document doc;
	
	public SingleDocumentModelProvider(Document d){
		this.doc = d;
	}
	public List<Property> getProperties(){
		List<Property> result = new ArrayList<Property>();
		Property name = new Property("Title:",this.doc.getTitle());
		result.add(name);
		Property noItems = new Property("No of Items:", ""+this.doc.getItems().size());
		result.add(noItems);
		DocumentCalculator calc = new DocumentCalculator();
		List<VatLevelItem> list = calc.getSummaryTaxLevelItems(this.doc.getItems());
		StringBuilder sb = new StringBuilder();
		for(VatLevelItem vi : list){
			sb.append(vi.getVatLevel());
			sb.append(" ");
		}
		Property vatLevels = new Property("Vat levels:",sb.toString());
		result.add(vatLevels);
		Property net = new Property("Net:", calc.getNetPrice(this.doc.getItems()).toString());
		result.add(net);
		Property gross = new Property("Gross:", calc.getGrossPrice(this.doc.getItems()).toString());
		result.add(gross);
		return result;
	}
}
