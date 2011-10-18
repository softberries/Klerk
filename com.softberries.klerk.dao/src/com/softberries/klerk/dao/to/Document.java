package com.softberries.klerk.dao.to;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Document implements Serializable{

	private Long id;
	private Long creator_id;
	private Long buyer_id;
	private Long seller_id;
	private String title;
	private String notes;
	private Date createdDate;
	private Date transactionDate;
	private Date dueDate;
	private String placeCreated;
	private Person creator;
	private Company buyer;
	private Company seller;
	private String paymentMethod;
	private List<DocumentItem> items;
	private List<VatLevelItem> vatLevelItems;
	private int documentType;
	
	public Document(){
		buyer = new Company();
		seller = new Company();
	}
	
	
	public Long getCreator_id() {
		return creator_id;
	}


	public void setCreator_id(Long creator_id) {
		this.creator_id = creator_id;
	}


	public Long getBuyer_id() {
		return buyer_id;
	}


	public void setBuyer_id(Long buyer_id) {
		this.buyer_id = buyer_id;
	}


	public Long getSeller_id() {
		return seller_id;
	}


	public void setSeller_id(Long seller_id) {
		this.seller_id = seller_id;
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
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the placeCreated
	 */
	public String getPlaceCreated() {
		return placeCreated;
	}
	/**
	 * @param placeCreated the placeCreated to set
	 */
	public void setPlaceCreated(String placeCreated) {
		this.placeCreated = placeCreated;
	}
	/**
	 * @return the creator
	 */
	public Person getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(Person creator) {
		this.creator = creator;
	}
	/**
	 * @return the items
	 */
	public List<DocumentItem> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(List<DocumentItem> items) {
		this.items = items;
	}
	public Company getBuyer() {
		return buyer;
	}
	public void setBuyer(Company buyer) {
		this.buyer = buyer;
	}
	public Company getSeller() {
		return this.seller;
	}
	public void setSeller(Company seller) {
		this.seller = seller;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public List<VatLevelItem> getVatLevelItems() {
		return vatLevelItems;
	}
	public void setVatLevelItems(List<VatLevelItem> vatLevelItems) {
		this.vatLevelItems = vatLevelItems;
	}
	public int getDocumentType() {
		return documentType;
	}
	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}
	
	
	
}
