package com.hsbc.utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("deprecation")
public class DbUtility {

	
	static Connection con;
	
	
	
	public static Connection getConnection() {
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
		return con;
	}
}
