package com.hsbc.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtility {

	
static Connection con;
	
	static {
		try {
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				con=DriverManager.getConnection("jdbc:derby:F:/HSBC/derbyDB/networkingdb");
				
			} catch (ClassNotFoundException e) {
	
				e.printStackTrace();
			}
			
			} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return con;
	}
}
