package com.softberries.klerk.dao.to;

public class DocumentItem {

	private Long id;
	private Long documentId;
	private Product product;
	private String priceNet;
	private String priceGross;
	private String priceTax;
	private String taxValue;
	private String quantity;
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
	 * @return the priceNet
	 */
	public String getPriceNet() {
		return priceNet;
	}
	/**
	 * @param priceNet the priceNet to set
	 */
	public void setPriceNet(String priceNet) {
		this.priceNet = priceNet;
	}
	/**
	 * @return the priceGross
	 */
	public String getPriceGross() {
		return priceGross;
	}
	/**
	 * @param priceGross the priceGross to set
	 */
	public void setPriceGross(String priceGross) {
		this.priceGross = priceGross;
	}
	/**
	 * @return the priceTax
	 */
	public String getPriceTax() {
		return priceTax;
	}
	/**
	 * @param priceTax the priceTax to set
	 */
	public void setPriceTax(String priceTax) {
		this.priceTax = priceTax;
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
