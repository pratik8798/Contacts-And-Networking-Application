package com.hsbc.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Decryption {
	public static boolean verifyPassword(String userPassKey, int userId)
	{
	
		userPassKey=CreatePassword.createPassword(userId, userPassKey);
		System.out.println(userPassKey);
		try {
			Connection con = DbUtility.getConnection();
			Statement stmt=con.createStatement();
			String st = "Select password from Users where userId="+userId;
			
			ResultSet rs = stmt.executeQuery(st);
			
			if(rs.next()) {
				if(userPassKey.equals(rs.getString(1))) {
					System.out.println("Success");
					return true;
				}else {
					System.out.println("Access denied !! wromg password");
					return false;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
