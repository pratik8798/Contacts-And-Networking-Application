package com.hsbc.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsDeactivatedUtility {
	public static boolean isDeactivated(int userId)
	{
		boolean flag=false;
		
		Connection con=DbUtility.getConnection();
		
		String query="Select TimeOfDeactivation from DeactivatedUsers where userId=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setInt(1, userId);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
			{
				flag=true;
			}
			
			
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return flag;
	}
}
