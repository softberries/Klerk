package com.softberries.klerk.dao.to;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable{

	private Long id;
	private String firstName;
	private String lastName;
	private List<Address> addresses;
	private String telephone;
	private String mobile;
	private String email;
	private String www;
	private String fullName;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the addresses
	 */
	public List<Address> getAddresses() {
		return addresses;
	}
	
	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the www
	 */
	public String getWww() {
		return www;
	}
	/**
	 * @param www the www to set
	 */
	public void setWww(String www) {
		this.www = www;
	}
	/**
	 * Utility method to return full name of this {@code Person}
	 * @return
	 */
	public String getFullName() {
		return this.getFirstName() + " " + this.getLastName();
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
