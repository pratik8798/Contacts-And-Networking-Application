package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;

/*The servlet is mapped to URL viewAllFriend and receives a post request witha a parameter Username whose all 
 * friends are needed and returns a Json object containing list of users. This functionality is achieved by calling 
 * up the getAllFriends function of the dao class.
 * Author: Vaibhav Narkhede
 */

public class ViewAllFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		UserDaoImpl getFriend = new UserDaoImpl();
		int userId = getFriend.getUserId(userName);
		PrintWriter pw = response.getWriter();
		JSONObject obj = new JSONObject();
		List<User> friendList = getFriend.getAllFriends(userId);
		JSONArray array = new JSONArray();
		StringWriter out = new StringWriter();
		if (friendList.size() == 0) {
			obj.put("message", "You don't have any friends");
			obj.writeJSONString(out);
		} else {
			for (User u : friendList) {
				obj = new JSONObject();
				System.out.println(u.getFullName());
				obj.put("fullName", u.getFullName());
				obj.put("email", u.getEmail());
				obj.put("phoneNumber", u.getPhoneNumber());
				obj.put("gender", u.getGender());
				obj.put("dateOfBirth", u.getDateOfBirth().toString());
				obj.put("address", u.getAddress());
				obj.put("city", u.getCity());
				obj.put("state", u.getState());
				obj.put("country", u.getCountry());
				obj.put("company", u.getCompany());
				obj.put("userName", u.getUsername());
				if(u.getProfileImage()==null) {
					obj.put("profileImage",null);	
				}else {
				String base64Image = Base64.getEncoder().encodeToString(u.getProfileImage());
				obj.put("profileImage",base64Image);
				}
				
				array.add(obj);
			}
			JSONObject friends=new JSONObject();
			friends.put("friendList", array);
//			array.writeJSONString(out);
			friends.writeJSONString(out);
		}
		System.out.println(array.toJSONString());
		
		String jsonText = out.toString();

		pw.println(jsonText);
		pw.close();

		
	}

}
