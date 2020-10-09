package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.DBUtility;
import com.hsbc.dao.UserDaoImpl;

public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = DBUtility.getConnection();

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String userName1 = request.getParameter("userName1");
//   		String userName2 = request.getParameter("userName2");
//   		System.out.println(userName1 +" "+userName2);
//   		int userId1=MapUserNameToUserId(userName1);
//   		int userId2=MapUserNameToUserId(userName2);
//   		
//   		UserDaoImpl getFriend = new UserDaoImpl();
//   		String mssg=getFriend.removeFriend(userId1, userId2);
//   		if(mssg.equals("success")) {
//   			mssg="successfully removed your friend from your friendlist";
//   		}else {
//   			mssg="you are not  friends !";
//   		}
//   		
//   		JSONObject obj = new JSONObject();
//   		obj.put("message", mssg);
//   		PrintWriter pw = response.getWriter();
//		System.out.println(obj.toJSONString());
//		pw.println(obj.toJSONString());
//		pw.close();
//   		
//	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String userName1 = request.getParameter("userName1");
		String userName2 = request.getParameter("userName2");
		System.out.println(userName1 +" "+userName2);
		int userId1=MapUserNameToUserId(userName1);
		int userId2=MapUserNameToUserId(userName2);
		
		UserDaoImpl getFriend = new UserDaoImpl();
		String mssg=getFriend.removeFriend(userId1, userId2);
		if(mssg.equals("success")) {
   			mssg="successfully removed your friend from your friendlist";
   		}else {
   			mssg="you are not  friends !";
   		}
		
		JSONObject obj = new JSONObject();
   		obj.put("message", mssg);
   		PrintWriter pw = response.getWriter();
		System.out.println(obj.toJSONString());
		pw.println(obj.toJSONString());
		pw.close();
		
		
//			doGet(request, response);
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
