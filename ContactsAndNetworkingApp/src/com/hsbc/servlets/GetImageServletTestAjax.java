package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class GetImageServletTestAjax
 */
public class GetImageServletTestAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("userName");	
		//System.out.println(name);


		Connection con=null;
		
		String jsonText=null;
		PrintWriter pw=response.getWriter();
		
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con=DriverManager.getConnection("jdbc:derby:F:\\CODEFURY\\ContactsAndNetworkingApp\\resources\\testdb");
			System.out.println("Successfully connected");
			String query="select pic from pictures where name = ?";
			
			PreparedStatement stmt=con.prepareStatement(query);
			stmt.setString(1, name);
			
			ResultSet rs=stmt.executeQuery();
			
			if(rs.next())
			{
				
				byte[] profileImage=rs.getBytes(1);
				//response.setContentType("image/jpg");
				
				JSONObject obj=new JSONObject();
				
				
				
				String base64Image = Base64.getEncoder().encodeToString(profileImage);
				
				obj.put("image", base64Image);

				
				
				StringWriter out=new StringWriter();
				obj.writeJSONString(out);
				jsonText=out.toString();
				
			
		        System.out.println("Found Image");
		        

			}
			else
			{
				JSONObject obj=new JSONObject();

				obj.put("message", "Username not found");

				
				
				StringWriter out=new StringWriter();
				obj.writeJSONString(out);
				jsonText=out.toString();
				
				
				
			}
			
			pw.println(jsonText);
			pw.close();
			
	        con.close();
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	


	}

}
