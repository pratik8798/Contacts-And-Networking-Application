package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hsbc.dao.DBUtility;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;

public class ViewPendingRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = DBUtility.getConnection();
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String userName = request.getParameter("userName");
//		System.out.println(userName);
//		int userId=MapUserNameToUserId(userName);
//		
//		UserDaoImpl getFriend = new UserDaoImpl();
//		HashMap<User,String> pendingFriendRequest = getFriend.showPendingRequest(userId);
//		
//		JSONArray array = new JSONArray();
//		
//		for (Map.Entry mapElement : pendingFriendRequest.entrySet()) {
//			User u = (User) mapElement.getKey();
//			JSONObject obj = new JSONObject();
//			obj.put("userName", u.getUsername());
//			obj.put("fullName", u.getFullName());
////			obj.put("userId", u.getUserId());
//			obj.put("message", mapElement.getValue());
//			array.add(obj);
//		}
//		
//		PrintWriter pw = response.getWriter();
//		pw.println(array.toJSONString());
//		pw.close();
	
//		
//	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		System.out.println(userName);
		int userId=MapUserNameToUserId(userName);
		
		UserDaoImpl getFriend = new UserDaoImpl();
		HashMap<User,String> pendingFriendRequest = getFriend.showPendingRequest(userId);
		
		JSONArray array = new JSONArray();
		PrintWriter pw = response.getWriter();
   	
   		
		for (Map.Entry mapElement : pendingFriendRequest.entrySet()) {
			User u = (User) mapElement.getKey();
			JSONObject obj = new JSONObject();
			obj.put("userName", u.getUsername());
			obj.put("fullName", u.getFullName());
			obj.put("userId", u.getUserId());
			obj.put("message", mapElement.getValue());
			array.add(obj);
		}
		
//		PrintWriter pw = response.getWriter();
//		pw.println(array.toJSONString());
//		pw.close();
		
		StringWriter out = new StringWriter();
		array.writeJSONString(out);
		String jsonText = out.toString();
		pw.println(jsonText);
		pw.close();
		
//		doGet(request, response);
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
