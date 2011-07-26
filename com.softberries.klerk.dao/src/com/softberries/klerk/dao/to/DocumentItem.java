package com.softberries.klerk.dao.to;

public class DocumentItem {

	private Long id;
	private Long documentId;
	private Product product;
	private String priceNetSingle;
	private String priceGrossSingle;
	private String priceTaxSingle;
	private String priceNetAll;
	private String priceGrossAll;
	private String priceTaxAll;
	private String taxValue;
	private String quantity;
	private boolean selected;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getPriceNetSingle() {
		return priceNetSingle;
	}
	public void setPriceNetSingle(String priceNetSingle) {
		this.priceNetSingle = priceNetSingle;
	}
	public String getPriceGrossSingle() {
		return priceGrossSingle;
	}
	public void setPriceGrossSingle(String priceGrossSingle) {
		this.priceGrossSingle = priceGrossSingle;
	}
	public String getPriceTaxSingle() {
		return priceTaxSingle;
	}
	public void setPriceTaxSingle(String priceTaxSingle) {
		this.priceTaxSingle = priceTaxSingle;
	}
	public String getPriceNetAll() {
		return priceNetAll;
	}
	public void setPriceNetAll(String priceNetAll) {
		this.priceNetAll = priceNetAll;
	}
	public String getPriceGrossAll() {
		return priceGrossAll;
	}
	public void setPriceGrossAll(String priceGrossAll) {
		this.priceGrossAll = priceGrossAll;
	}
	public String getPriceTaxAll() {
		return priceTaxAll;
	}
	public void setPriceTaxAll(String priceTaxAll) {
		this.priceTaxAll = priceTaxAll;
	}
	/**
	 * @return the documentId
	 */
	public Long getDocumentId() {
		return documentId;
	}
	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * @return the taxValue
	 */
	public String getTaxValue() {
		return taxValue;
	}
	/**
	 * @param taxValue the taxValue to set
	 */
	public void setTaxValue(String taxValue) {
		this.taxValue = taxValue;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
}
