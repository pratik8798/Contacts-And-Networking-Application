package com.hsbc.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

/**
 * Servlet implementation class GetImageServletTest
 */
public class GetImageServletTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name=request.getParameter("userName");	
		//System.out.println(name);

		RequestDispatcher rd=null;
		Connection con=null;
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
				
				/*
				OutputStream os = response.getOutputStream();
		        os.write(profileImage);
		        os.flush();
		        os.close();
		        */
				
				String base64Image = Base64.getEncoder().encodeToString(profileImage);
				request.setAttribute("image", base64Image);
		        System.out.println("Found Image");
		        

			}
			else
			{
				request.setAttribute("message", "Username not found");
				
			}
			
			rd=request.getRequestDispatcher("ShowImage.jsp");
	        rd.forward(request, response);
			
	        con.close();
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	

		
	}

}
