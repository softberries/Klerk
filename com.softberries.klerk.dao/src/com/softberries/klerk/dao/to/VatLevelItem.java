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
