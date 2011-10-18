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

/**
 * Non mutable property class for displaying {@code Product} or {@code Document} properties
 * properties on the {@code ProductDetailsView} or {@code DocumentDetailsView}
 * @author kris
 *
 */
public class Property {

	/**
	 * Name of the property
	 */
	private final String name;
	/**
	 * Value of the property
	 */
	private final String value;
	
	public Property(String name, String value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
