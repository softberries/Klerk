package com.softberries.klerk.dao.to;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DocumentItem {

	private Long id;
	private Long documentId;
	private Product product;
	private Long product_id;
	private String product_name;
	private String priceNetSingle;
	private String priceGrossSingle;
	private String priceTaxSingle;
	private String priceNetAll;
	private String priceGrossAll;
	private String priceTaxAll;
	private String taxValue;
	private String quantity;
	private boolean selected;
	
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
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
		propertyChangeSupport.firePropertyChange("selected", this.selected, this.selected = selected);
	}
	public String getPriceNetSingle() {
		return priceNetSingle;
	}
	public void setPriceNetSingle(String priceNetSingle) {
		propertyChangeSupport.firePropertyChange("priceNetSingle", this.priceNetSingle, this.priceNetSingle = priceNetSingle);
	}
	public String getPriceGrossSingle() {
		return priceGrossSingle;
	}
	public void setPriceGrossSingle(String priceGrossSingle) {
		propertyChangeSupport.firePropertyChange("priceGrossSingle", this.priceGrossSingle, this.priceGrossSingle = priceGrossSingle);
	}
	public String getPriceTaxSingle() {
		return priceTaxSingle;
	}
	public void setPriceTaxSingle(String priceTaxSingle) {
		propertyChangeSupport.firePropertyChange("priceTaxSingle", this.priceTaxSingle, this.priceTaxSingle = priceTaxSingle);
	}
	public String getPriceNetAll() {
		return priceNetAll;
	}
	public void setPriceNetAll(String priceNetAll) {
		propertyChangeSupport.firePropertyChange("priceNetAll", this.priceNetAll, this.priceNetAll = priceNetAll);
	}
	public String getPriceGrossAll() {
		return priceGrossAll;
	}
	public void setPriceGrossAll(String priceGrossAll) {
		propertyChangeSupport.firePropertyChange("priceGrossAll", this.priceGrossAll, this.priceGrossAll = priceGrossAll);
	}
	public String getPriceTaxAll() {
		return priceTaxAll;
	}
	public void setPriceTaxAll(String priceTaxAll) {
		propertyChangeSupport.firePropertyChange("priceTaxAll", this.priceTaxAll, this.priceTaxAll = priceTaxAll);
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
		propertyChangeSupport.firePropertyChange("documentId", this.documentId, this.documentId = documentId);
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
		propertyChangeSupport.firePropertyChange("product", this.product, this.product = product);
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
		propertyChangeSupport.firePropertyChange("taxValue", this.taxValue, this.taxValue = taxValue);
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
		propertyChangeSupport.firePropertyChange("quantity", this.quantity, this.quantity = quantity);
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	
}
