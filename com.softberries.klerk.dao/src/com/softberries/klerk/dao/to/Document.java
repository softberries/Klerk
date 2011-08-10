package com.softberries.klerk.dao.to;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Document implements Serializable{

	private Long id;
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
		return seller;
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
	
	
	
}
