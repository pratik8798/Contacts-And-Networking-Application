package com.hsbc.utility;

import java.io.File;

public class FileUtility {
	public static File getFile()
	{
		String path="C:\\Users\\mohit\\Documents\\GitHub\\Contacts-And-Networking-Application\\Contacts-And-Networking-Application\\ContactsAndNetworkingApp\\resources\\Admin.xml";
		File inputFile=new File(path);
		System.out.println("Path : "+ path);
		return inputFile;
		
	}
}
