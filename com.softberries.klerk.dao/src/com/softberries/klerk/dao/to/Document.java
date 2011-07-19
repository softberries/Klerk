package com.softberries.klerk.dao.to;

import java.io.Serializable;
import java.util.Date;

public class Document implements Serializable{

	private String title;
	private String notes;
	private Date createdDate;
	private Date transactionDate;
	private Date dueDate;
	private String placeCreated;
	private Person creator;
	
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date dateCreated) {
		this.createdDate = dateCreated;
	}
	public String getPlaceCreated() {
		return placeCreated;
	}
	public void setPlaceCreated(String placeCreated) {
		this.placeCreated = placeCreated;
	}
	public Person getCreator() {
		return creator;
	}
	public void setCreator(Person creator) {
		this.creator = creator;
	}
	
	
}
