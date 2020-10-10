package com.hsbc.utility;

import java.sql.*;

public class Encryption {
		
	public static String GeneratePassword(String password) {
		String newPassword=null;
		try {
			int defaultSalt=0;
			Connection con = DbUtility.getConnection();
			Statement stmt=con.createStatement();
			String st = "Select MAX(userId) from Users";
			ResultSet rs = stmt.executeQuery(st);
			if(rs.next()) {
				System.out.println("max user are "+rs.getInt(1));
				defaultSalt=rs.getInt(1)+1;
			}else {
				defaultSalt=101;
			}
			System.out.println(defaultSalt);
			newPassword=CreatePassword.createPassword(defaultSalt, password);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(newPassword);
		return newPassword;
	}
	
}
