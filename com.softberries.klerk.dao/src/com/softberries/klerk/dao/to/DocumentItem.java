package com.softberries.klerk.dao.to;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DocumentItem {

	private Long id;
	private Long document_id;
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
	public Long getDocument_id() {
		return document_id;
	}
	/**
	 * @param documentId the documentId to set
	 */
	public void setDocument_id(Long documentId) {
		propertyChangeSupport.firePropertyChange("documentId", this.document_id, this.document_id = documentId);
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
		setProduct_id(product.getId());
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((document_id == null) ? 0 : document_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((priceGrossAll == null) ? 0 : priceGrossAll.hashCode());
		result = prime
				* result
				+ ((priceGrossSingle == null) ? 0 : priceGrossSingle.hashCode());
		result = prime * result
				+ ((priceNetAll == null) ? 0 : priceNetAll.hashCode());
		result = prime * result
				+ ((priceNetSingle == null) ? 0 : priceNetSingle.hashCode());
		result = prime * result
				+ ((priceTaxAll == null) ? 0 : priceTaxAll.hashCode());
		result = prime * result
				+ ((priceTaxSingle == null) ? 0 : priceTaxSingle.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result
				+ ((product_id == null) ? 0 : product_id.hashCode());
		result = prime * result
				+ ((product_name == null) ? 0 : product_name.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result
				+ ((taxValue == null) ? 0 : taxValue.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentItem other = (DocumentItem) obj;
		//if two objects have the same id and the same documentId then they
		//are considered to be equal
		if(other.getId() != null && this.getId() != null){
			if(other.getId().longValue() == this.getId().longValue()){
				if(other.getDocument_id() != null && this.getDocument_id() != null){
					if(other.getDocument_id().longValue() == this.getDocument_id().longValue()){
						return true;
					}
				}
			}
		}
		return false;
	}

	
}
