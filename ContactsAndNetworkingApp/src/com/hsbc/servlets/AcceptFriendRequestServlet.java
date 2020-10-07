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

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.utility.DbUtility;


public class AcceptFriendRequestServlet extends HttpServlet {

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


	
	
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();

		String userName1 = request.getParameter("userName1");
		String userName2=request.getParameter("userName2");
		System.out.println(userName1 + userName2);
		int userId1 = MapUserNameToUserId(userName1);
		int userId2=MapUserNameToUserId(userName2);
		System.out.println(userId1 +"---" +userId2);
		
		UserDao user = new UserDaoImpl();
		
	String msg=	user.acceptFriendRequest(userId1, userId2);
		

		if(msg.equals("Success")) {
		msg="Freind Added Succesfully to the FreindList";
		}else {
			msg="Freind Addition FAILED";
	}
		
		JSONObject obj = new JSONObject();
		obj.put("message", msg);
		
		System.out.println(obj.toJSONString());
		pw.println(obj.toJSONString());
		pw.close();   	
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();

		String userName1 = request.getParameter("userName1");
		String userName2=request.getParameter("userName2");
		System.out.println(userName1 + userName2);
		int userId1 = MapUserNameToUserId(userName1);
		int userId2=MapUserNameToUserId(userName2);
		System.out.println(userId1 +"---" +userId2);
		
		UserDao user = new UserDaoImpl();
		
	String msg=	user.acceptFriendRequest(userId1, userId2);
		

		if(msg.equals("success")) {
		msg="successfully added to your block list";
		}else {
			msg="friend was not added to the blocklist!";
	}
		
		JSONObject obj = new JSONObject();
		obj.put("message", msg);
		
		System.out.println(obj.toJSONString());
		pw.println(obj.toJSONString());
		pw.close(); 
	}

}
