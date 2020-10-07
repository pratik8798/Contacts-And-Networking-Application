package com.hsbc.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsDisabledUtility {
	public static boolean isDisabled(int userId)
	{
		boolean flag=false;
		
		Connection con=DbUtility.getConnection();
		
		String query="Select isDisabled from DisabledUsers where userId=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setInt(1, userId);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				flag=rs.getBoolean(1);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return flag;
	}
}
