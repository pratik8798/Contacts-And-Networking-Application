/**
 * 
 * 
 * Name : Contact.java
 * Use : Contact entity extending Person class
 * 
 */


package com.hsbc.domain;

import java.util.Date;

public class Contact extends Person{

	public Contact(int userId, String fullName, String email, long phoneNumber, String gender, Date dateOfBirth,
			String address, String city, String state, String country, String company) {
		
		super(userId, fullName, email, phoneNumber, gender, dateOfBirth, address, city, state, country, company);
		
	}
	
}
