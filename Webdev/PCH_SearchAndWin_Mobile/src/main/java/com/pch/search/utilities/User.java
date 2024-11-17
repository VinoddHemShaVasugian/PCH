package com.pch.search.utilities;

public class User {
	private String title;
	private String firstname;
	private String middlename;
	private String lastname;
	private String street;
	private String building;
	private String city;
	private String state;
	private String statecode;
	private String country;
	private String countrycode;
	private String zip;
	private String phone;
	private String email;
	private String password;
	private String adminuser;
	private String adminpassword;
	private String dob_Month;
	private String dob_Day;
	private String dob_Year;
	
	private String keepSignedIn;
	private String SearchSubscription;
	private String pchDotComSubscription; 
	
	public void setKeepSignedIn(String keepSignedIn) {
		this.keepSignedIn = keepSignedIn;
	}


	public void setSearchSubscription(String SearchSubscription) {
		this.SearchSubscription = SearchSubscription;
	}


	public void setPchDotComSubscription(String pchDotComSubscription) {
		this.pchDotComSubscription = pchDotComSubscription;
	}

	
	
	public boolean keepSignedIn(){
		if(keepSignedIn==null){
			return true;
		}
		
		if(keepSignedIn.equalsIgnoreCase("N")){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isSubscribedToSearch(){
		if(SearchSubscription==null){
			return true;
		}
		if(SearchSubscription.equalsIgnoreCase("N")){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isSubscribedToPchDotCom(){
		if(pchDotComSubscription==null){
			return true;
		}
		
		if(pchDotComSubscription.equalsIgnoreCase("N")){
			return false;
		}else{
			return true;
		}
	}
	
	
	public String getDob_Month() {
		return dob_Month;
	}

	public void setDob_Month(String dob_Month) {
		this.dob_Month = dob_Month;
	}

	public String getDob_Day() {
		return dob_Day;
	}

	public void setDob_Day(String dob_Day) {
		this.dob_Day = dob_Day;
	}

	public String getDob_Year() {
		return dob_Year;
	}

	public void setDob_Year(String dob_Year) {
		this.dob_Year = dob_Year;
	}

	
	
	public String toString(){
		return (String.format("\nName = %s %s %s %s\nStreet = %s\nBuilding = %s\nCity = %s\nState = %s\nCountry = %s\nZip = %s\nEmail = %s\nPassword = %s\nDate Of Birth = %s", 
				title,firstname,middlename,lastname,street,building,city,state,country,zip,email,password,getDob_Day()+getDob_Month()+getDob_Year()));
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
		//this.firstname=Common.getRandomUserName("FN-");
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
		//this.lastname=Common.getRandomUserName("LN-");
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminuser() {
		return adminuser;
	}

	public void setAdminuser(String adminuser) {
		this.adminuser = adminuser;
	}

	public String getAdminpassword() {
		return adminpassword;
	}

	public void setAdminpassword(String adminpassword) {
		this.adminpassword = adminpassword;
	}

}
