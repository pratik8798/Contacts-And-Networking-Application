/**
 * 
 * Servlet to disable a user
 * 
 * Receives : userId
 * Output : message 
 * 					1. User disabled successfully
 * 					2. User not found 
 * 
 * 
 * 
 * 
 */


package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.AdminDao;
import com.hsbc.dao.AdminDaoImpl;

public class DisableUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		int userId=Integer.parseInt(request.getParameter("userId"));
		
		System.out.println(userId);
		AdminDao adminDao=new AdminDaoImpl();
		
		JSONObject obj=new JSONObject();
		if(adminDao.disableUser(userId))
		{
			System.out.println("User"+userId+" disabled successfully");
			obj.put("message", "User disabled successfully");
		}
		else
		{
			System.out.println("User not found");
			obj.put("message", "User not found");
		}
		
		
		
		StringWriter out=new StringWriter();
		obj.writeJSONString(out);
		String jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
	
	}

}
