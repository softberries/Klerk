package com.softberries.klerk.dao.to;

import java.io.Serializable;
import java.util.Date;

public class Document implements Serializable{

	private String title;
	private String notes;
	private Date dateCreated;
	private String placeCreated;
	private Person creator;
	
	
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
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
