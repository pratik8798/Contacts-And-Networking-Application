package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;
import com.hsbc.utility.DbUtility;

/**
 * Servlet implementation class ViewBlockedUserServlet
 */

public class ViewBlockedUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con = DbUtility.getConnection();
	public int MapUserNameToUserId(String userName) {
   		try {
			Statement stmt=con.createStatement();
			String st = "Select userId from Users where userName='"+userName+"'";
			ResultSet rs = stmt.executeQuery(st);
			if(rs.next()) {
				int id= rs.getInt("userId");
				return id;
			}
			
   		} catch (SQLException e) {
   			e.printStackTrace();
		}
   		return 0;
   	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


	try {
		PrintWriter pw = response.getWriter();

		String userName = request.getParameter("userName");
		System.out.println(userName);
		int userId1 = MapUserNameToUserId(userName);
		System.out.println(userId1);
		UserDao user = new UserDaoImpl(); 
	   JSONArray array = new JSONArray();
	  
	  ArrayList<User> list=(ArrayList<User>) user.getBlockedUsers(userId1);
	  
	  for (User u : list) 
	  { 
		  JSONObject obj = new JSONObject();
	  
	   obj.put("userId",u.getUserId() ); 
	   obj.put("fullName",u.getFullName());
	   obj.put("city", u.getCity());
	   obj.put("country", u.getCountry());
	  
	  array.add(obj); 
	  }
	  
	   pw = response.getWriter(); 
	   pw.println(array.toJSONString());
	   pw.close();
	  
	  } catch(Exception e)
	{
		  e.printStackTrace();
	  
	}
	  }
	  


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			PrintWriter pw = response.getWriter();

			String userName = request.getParameter("userName");
			
			int userId1 = MapUserNameToUserId(userName);
			
			UserDao user = new UserDaoImpl(); 
		   JSONArray array = new JSONArray();
		  
		  ArrayList<User> list=(ArrayList<User>) user.getBlockedUsers(userId1);
		  
		  for (User u : list) 
		  { 
			  JSONObject obj = new JSONObject();
		  
		   obj.put("userId",u.getUserId() ); 
		   obj.put("fullName",u.getFullName());
		  
		  array.add(obj); 
		  }
		  
		   pw = response.getWriter(); 
		   pw.println(array.toJSONString());
		   pw.close();
		  
		  } catch(Exception e)
		{
			  e.printStackTrace();
		  
		}
		  }
		  

		
	}


