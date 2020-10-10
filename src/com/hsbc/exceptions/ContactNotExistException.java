package com.hsbc.exceptions;

public class ContactNotExistException extends Exception{
	
	public void showMessage() {
		System.out.println("This contact does not exist");
	}

}
