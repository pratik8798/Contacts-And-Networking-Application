package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;


import com.hsbc.dao.UserDaoImpl;

/* The Servlet is mapped to URl removeFriend and receives a post request with two parameters 
 * username1 and username2 and calls the removeFriend dao function to remove the username2 as the friend of username1
 * and returns a message conveying if the removal was successful or the if they were not friends in actual.
 * Author: Vaibhav Narkhede
 */

public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String userName1 = request.getParameter("userName1");
		String userName2 = request.getParameter("userName2");
		System.out.println(userName1 +" "+userName2);
		UserDaoImpl getFriend = new UserDaoImpl();
		int userId1=getFriend.getUserId(userName1);
		int userId2=getFriend.getUserId(userName2);
		
		
		String mssg=getFriend.removeFriend(userId1, userId2);
		if (mssg.equals("success")) {
			mssg = "Successful";
		} else {
			mssg = "Unsuccessful";
		}
		JSONObject obj = new JSONObject();
   		obj.put("message", mssg);
   		PrintWriter pw = response.getWriter();
		System.out.println(obj.toJSONString());
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString();
		pw.println(jsonText);
		pw.close();
		
	}

}
