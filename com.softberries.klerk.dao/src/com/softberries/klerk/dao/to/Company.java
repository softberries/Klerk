package com.softberries.klerk.dao.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Company  implements Serializable{

	private Long id;
	private String name;
	private String vatid;
	private String telephone;
	private String mobile;
	private String email;
	private String www;
	private List<Address> addresses;
	private Address address; //main address;
	private String fullName;
	
	/**
	 * Default constructor
	 * init basic structures
	 */
	public Company(){
		//this prevents many NullPointerExceptions in the future
		this.addresses = new ArrayList<Address>();
	}
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
	/**
	 * Gets the main address of the company
	 * @return
	 */
	public Address getAddress() {
		if(this.addresses == null || this.addresses.size() < 1){
			return null;
		}
		for(Address a : this.addresses){
			if(a.isMain()){
				return a;
			}
		}
		return this.addresses.get(0);
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getFullName() {
		if(this.getName() == null || this.getVatid() == null){
			return "";
		}
		return this.toString();
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getName());
		builder.append(" Vat Id: ");
		builder.append(this.getVatid());
		return builder.toString();
	}
	
	
}
