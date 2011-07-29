package com.softberries.klerk.dao.to;

import java.util.List;

public class Company {

	private Long id;
	private String name;
	private String vatid;
	private String telephone;
	private String mobile;
	private String email;
	private String www;
	private List<Address> addresses;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the vatid
	 */
	public String getVatid() {
		return vatid;
	}
	/**
	 * @param vatid the vatid to set
	 */
	public void setVatid(String vatid) {
		this.vatid = vatid;
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
	
	
}
