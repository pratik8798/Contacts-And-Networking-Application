package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hsbc.dao.DBUtility;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;

public class ViewAllFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con =DBUtility.getConnection();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName=request.getParameter("userName");
		int userId = MapUserNameToUserId(userName);
		UserDaoImpl getFriend = new UserDaoImpl();
		
		List<User> friendList = getFriend.getAllFriends(userId);
		JSONArray array = new JSONArray();
		for (User u : friendList) {
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

			array.add(obj);
		}
		PrintWriter pw = response.getWriter();
		pw.println(array.toJSONString());
		pw.close();
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String userName=request.getParameter("userName");
//		int userId = MapUserNameToUserId(userName);
//		UserDaoImpl getFriend = new UserDaoImpl();
//		
//		List<User> friendList = getFriend.getAllFriends(userId);
//		JSONArray array = new JSONArray();
//		for (User u : friendList) {
//			JSONObject obj = new JSONObject();
//
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
//			array.add(obj);
//		}
//		PrintWriter pw = response.getWriter();
//		pw.println(array.toJSONString());
//		pw.close();
//		doGet(request, response);
//	}

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
