/**
 * 
 * 
 * 
 * Name : Person.java
 * Use : Base class for User and Contact
 * To-do : image
 * 
 * 
 * 
 */
package com.hsbc.domain;

import java.util.Date;

public class Person {
	private int userId;							//decide userId or just id;
	private String fullName;
	private String email;
	private long phoneNumber;
	private String gender;
	private Date dateOfBirth;
	private String address;
	private String city;
	private String state;
	private String country;
	private String company;
	
	public Person() {
	
	}
	
	public Person(int userId, String fullName, String email, long phoneNumber, String gender, Date dateOfBirth,
			String address, String city, String state, String country, String company) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.company = company;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}


}
