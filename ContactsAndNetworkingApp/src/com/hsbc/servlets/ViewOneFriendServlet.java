package com.hsbc.servlets;
import com.hsbc.domain.User;
import com.hsbc.exceptions.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.DBUtility;
import com.hsbc.dao.UserDaoImpl;

import java.sql.*;
public class ViewOneFriendServlet extends HttpServlet {

	Connection con =DBUtility.getConnection();
	private static final long serialVersionUID = 1L;
       
//   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//   		String userName1 = request.getParameter("userName1");
//   		String userName2 = request.getParameter("userName2");
//   		System.out.println(userName1 +" "+userName2);
//   		int userId1=MapUserNameToUserId(userName1);
//   		int userId2=MapUserNameToUserId(userName2);
//   		
//   		UserDaoImpl getFriend = new UserDaoImpl();
//   		try {
//			User u = getFriend.getOneFriend(userId1,userId2);
//			if (u == null) {
//				response.setStatus(404);
//				return;
//			}
//			JSONObject obj = new JSONObject();
//			obj.put("userId",u.getUserId() );
//			obj.put("fullName",u.getFullName());
//			obj.put("email",u.getEmail());
//			obj.put("phoneNumber",u.getPhoneNumber());
//			obj.put("gender",u.getGender());
//			obj.put("dateOfBirth",u.getDateOfBirth());
//			obj.put("address",u.getAddress());
//			obj.put("city",u.getCity());
//			obj.put("state",u.getState());
//			obj.put("country",u.getCountry());
//			obj.put("company",u.getCompany());
//			obj.put("userName",u.getUsername());
//			
//			PrintWriter pw = response.getWriter();
//			System.out.println(obj.toJSONString());
//			pw.println(obj.toJSONString());
//			pw.close();
//			
//		} catch (NotYourFriendException e) {
//			e.showExceptionMessage();
//		}
//   		
//   	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName1 = request.getParameter("userName1");
   		String userName2 = request.getParameter("userName2");
   		System.out.println(userName1 +" "+userName2);
   		int userId1=MapUserNameToUserId(userName1);
   		int userId2=MapUserNameToUserId(userName2);
   		
   		UserDaoImpl getFriend = new UserDaoImpl();
   		try {
			User u = getFriend.getOneFriend(userId1,userId2);
			if (u == null) {
				response.setStatus(404);
				return;
			}
			JSONObject obj = new JSONObject();
			obj.put("userId",u.getUserId() );
			obj.put("fullName",u.getFullName());
			obj.put("email",u.getEmail());
			obj.put("phoneNumber",u.getPhoneNumber());
			obj.put("gender",u.getGender());
			obj.put("dateOfBirth",u.getDateOfBirth());
			obj.put("address",u.getAddress());
			obj.put("city",u.getCity());
			obj.put("state",u.getState());
			obj.put("country",u.getCountry());
			obj.put("company",u.getCompany());
			obj.put("userName",u.getUsername());
			
			PrintWriter pw = response.getWriter();
			System.out.println(obj.toJSONString());
			pw.println(obj.toJSONString());
			pw.close();
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
//   		catch (notYourFriendExcpetion e) {
//			e.showExceptionMessage();
//		}
		doGet(request, response);
	}
   	
   	public int MapUserNameToUserId(String userName) {
   		try {
			Statement stmt=con.createStatement();
			String st = "Select * from Users where userName='"+userName+"'";
			ResultSet rs = stmt.executeQuery(st);
			if(rs.next()) {
				return rs.getInt(1);
			}
			
   		} catch (SQLException e) {
   			e.printStackTrace();
		}
   		return 0;
   	}
   	
   	
}
