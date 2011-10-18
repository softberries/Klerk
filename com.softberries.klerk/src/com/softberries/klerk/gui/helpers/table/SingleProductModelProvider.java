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

import com.softberries.klerk.dao.to.Product;

public class SingleProductModelProvider {

	private Product product;
	
	public SingleProductModelProvider(Product p){
		this.product = p;
	}
	public List<Property> getProperties(){
		List<Property> result = new ArrayList<Property>();
		Property name = new Property("Name:",this.product.getName());
		result.add(name);
		Property code = new Property("Code:", this.product.getCode());
		result.add(code);
		Property desc = new Property("Description:", this.product.getDescription());
		result.add(desc);
		return result;
	}
}
