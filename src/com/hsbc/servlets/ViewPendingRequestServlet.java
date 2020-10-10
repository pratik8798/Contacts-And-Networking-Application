package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;

/*The Servlet is mapped to the URL viewPendingRequest and recieves a post request with parameter userName 
 * and returns a JSON object containing users who are pending to be friends. This list is received from the 
 * dao method which is called in the servlet ,the ShowPendingRequest function
 * Author: Vaibhav Narkhede
 */

public class ViewPendingRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		System.out.println(userName);
		UserDaoImpl getFriend = new UserDaoImpl();
		int userId = getFriend.getUserId(userName);

		HashMap<User, String> pendingFriendRequest = getFriend.showPendingRequest(userId);

		JSONArray array = new JSONArray();
		PrintWriter pw = response.getWriter();
		StringWriter out = new StringWriter();
		if (pendingFriendRequest.size() == 0) {
			JSONObject obj = new JSONObject();
			obj.put("message", "No Pending Friend Requests");
			obj.writeJSONString(out);

		} else {
			for (Map.Entry mapElement : pendingFriendRequest.entrySet()) {
				User u = (User) mapElement.getKey();
				JSONObject obj = new JSONObject();
				obj.put("userName", u.getUsername());
				obj.put("fullName", u.getFullName());
				if(u.getProfileImage()==null) {
					obj.put("profileImage",null);	
				}else {
				String base64Image = Base64.getEncoder().encodeToString(u.getProfileImage());
				obj.put("profileImage",base64Image);
				}
				obj.put("message", mapElement.getValue());
				array.add(obj);
			}
			JSONObject users=new JSONObject();
			users.put("friendList", array);
//			array.writeJSONString(out);
			users.writeJSONString(out);
		}

		String jsonText = out.toString();
		pw.println(jsonText);
		pw.close();

	}

}
