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
import com.hsbc.domain.Admin;

/**
 * 
 * Servlet to get admin details
 * 
 * Receives : userName and password
 * Output : message 
 * 					1. Invalid username or password
 * 				OR
 * 			admin object consisting of : name, email, phoneNumber
 * 
 * 
 */
public class ViewAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		
		AdminDao adminDao=new AdminDaoImpl();
		
		Admin admin=adminDao.getAdmin(userName, password);
		JSONObject obj=new JSONObject();
		if(admin==null)
		{
			obj.put("message", "Invalid username or password");
		}
		else
		{
			obj.put("name", admin.getName());
			obj.put("email", admin.getEmail());
			obj.put("phoneNumber", admin.getPhoneNumber());
			obj.put("message", "Successful Welcome "+admin.getName());
		}
		
		PrintWriter pw=response.getWriter();
		
		StringWriter out=new StringWriter();
		obj.writeJSONString(out);
		String jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
		/*
		 * Testing code
		PrintWriter pw=response.getWriter();
		
		if(admin==null)
		{
			pw.write("Invalid username or password");
		}
		else
		{
			pw.write("Welcome "+userName);
		}
		
		pw.close();
		*/
	}

}
