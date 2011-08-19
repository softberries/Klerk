package com.softberries.klerk.dao.to;

public class Address {

	private Long id;
	private String country;
	private String city;
	private String street;
	private String postCode;
	private String houseNumber;
	private String flatNumber;
	private String notes;
	private boolean main;
	private Long person_id;
	private Long company_id;
	
	/**
	 * Initialize default values to prevent further NPE's
	 */
	public Address(){
		this.id = new Long(0);
		this.country = "";
		this.city = "";
		this.street = "";
		this.postCode = "";
		this.houseNumber = "";
		this.flatNumber = "";
		this.notes = "";
		this.main = false;
		this.person_id = new Long(0);
		this.company_id = new Long(0);
	}
	
	
	public Long getPerson_id() {
		return person_id;
	}
	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
	}
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the main
	 */
	public boolean isMain() {
		return main;
	}
	/**
	 * @param main the main to set
	 */
	public void setMain(boolean main) {
		this.main = main;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}
	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	/**
	 * @return the flatNumber
	 */
	public String getFlatNumber() {
		return flatNumber;
	}
	/**
	 * @param flatNumber the flatNumber to set
	 */
	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
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
	@Override
	public String toString() {
		return this.getStreet() + ", " + this.getHouseNumber() + "/" + this.getFlatNumber() + " " + this.getPostCode() + " " + this.getCity();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Address){
			Address adr = (Address)obj;
			//if its a new object check all the fields
			if(adr.getId() != null && adr.getId().longValue() == 0){
				return adr.getCity() == this.getCity() &&
						adr.getCompany_id().equals(this.getCompany_id()) &&
						adr.getCountry().equals(this.getCountry()) &&
						adr.getFlatNumber().equals(this.getFlatNumber()) &&
						adr.getHouseNumber().equals(this.getHouseNumber()) &&
						adr.getNotes().equals(this.getNotes()) &&
						adr.getPerson_id().equals(this.getPerson_id()) &&
						adr.getPostCode().equals(getPostCode()) && 
						adr.getStreet().equals(this.getStreet());
			}
			if(adr.getId() != null && this.getId() != null && adr.getId().longValue() == this.getId().longValue()){
				return true;
			}else{
				return false;
			}
		}else{
			return super.equals(obj);
		}
	}
	
	
	
	
	
}
