package com.hsbc.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class SaveImageServletTestAjax
 */
@MultipartConfig(maxFileSize = 16177215)
public class SaveImageServletTestAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static int uploadProfilePicture(String name,InputStream file)	{
		int row=0;
		
		String query = "INSERT INTO pictures "
				+ "(name,pic)"
				+ " values (?, ?)";


		Connection con;
		
		try {
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				con=DriverManager.getConnection("jdbc:derby:F:\\CODEFURY\\ContactsAndNetworkingApp\\resources\\testdb");
				System.out.println("Successfully connected");
				
				PreparedStatement pstmt=con.prepareStatement(query);
				pstmt.setString(1, name);
				if(file!=null)
				{
					pstmt.setBlob(2, file);
				}
				row=pstmt.executeUpdate();
				
				
				con.close();
			} catch (ClassNotFoundException e) {
		
				e.printStackTrace();
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
				return row;
		}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("In");
		String name=request.getParameter("name");
		Part filePart=request.getPart("profilePhoto");
		
		InputStream inputStream = null; 
		PrintWriter pw=response.getWriter();
		
		String jsonText=null;
		JSONObject obj=new JSONObject();
		
		if (filePart != null) 
		{ 
			System.out.println(name);
            System.out.println(filePart.getName()); 
            System.out.println(filePart.getSize());
            
            inputStream = filePart.getInputStream(); 
            
            int row=uploadProfilePicture(name, inputStream);
            if(row>0)
            {
            	System.out.println("Upload successful");
            	

				obj.put("message", "Upload successful");
            }
            else
            {
            	System.out.println("Upload not successful");
            	obj.put("message", "Upload not successful");
            }
			
		}
		else
		{
			System.out.println("Not received file : ");
			obj.put("message", "Not received file");
		}
		
		StringWriter out=new StringWriter();
		obj.writeJSONString(out);
		jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
		
        
	}
}
