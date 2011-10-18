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
package com.softberries.klerk.dao.to;

public class VatLevelItem {

	private String vatLevel;
	private String netValue;
	private String vatValue;
	private String grossValue;
	
	public String getVatLevel() {
		return vatLevel;
	}
	public void setVatLevel(String vatLevel) {
		this.vatLevel = vatLevel;
	}
	public String getNetValue() {
		return netValue;
	}
	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}
	public String getVatValue() {
		return vatValue;
	}
	public void setVatValue(String vatValue) {
		this.vatValue = vatValue;
	}
	public String getGrossValue() {
		return grossValue;
	}
	public void setGrossValue(String grossValue) {
		this.grossValue = grossValue;
	}
}
