/* Name: Sahil Sharma
 * This exception is thrown when there is no contact entry for the desired input.*/

package com.hsbc.exceptions;

public class ContactNotExistException extends Exception{
	
	public void showMessage() {
		System.out.println("This contact does not exist");
	}

}
