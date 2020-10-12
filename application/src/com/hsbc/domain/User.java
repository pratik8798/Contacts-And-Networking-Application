
/**
 * 
 * Name : User.java
 * Use : User entity extending Person class
 * 
 * 
 * 
 */

package com.hsbc.domain;

import java.sql.Timestamp;
import java.util.Date;

public class User extends Person{
	private String userName;
	private String password;
	private Timestamp timestamp;
	private int activeHours;
	
	
	
	public User() {
		
	}
	
	
	public User(int userId, String fullName, String email, long phoneNumber, String gender, Date dateOfBirth,
			String address, String city, String state, String country, String company,String userName, String password, Timestamp timestamp, int activeHours) {
		
		super(userId, fullName, email, phoneNumber, gender, dateOfBirth, address, city, state, country, company);
		
		this.userName = userName;
		this.password = password;
		this.timestamp = timestamp;
		this.activeHours = activeHours;
		
		
	}




	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public int getActiveHours() {
		return activeHours;
	}


	public void setActiveHours(int activeHours) {
		this.activeHours = activeHours;
	}
	
	
	
	
}
