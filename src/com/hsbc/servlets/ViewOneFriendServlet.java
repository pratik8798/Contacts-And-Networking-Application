package com.hsbc.servlets;
import com.hsbc.domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.UserDaoImpl;

import java.sql.*;
import java.util.Base64;
public class ViewOneFriendServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName1 = request.getParameter("userName1");
   		String userName2 = request.getParameter("userName2");
   		System.out.println(userName1 +" "+userName2);
   		
   		UserDaoImpl getFriend = new UserDaoImpl();
   		int userId1=getFriend.getUserId(userName1);
   		int userId2=getFriend.getUserId(userName2);
   		System.out.println(userId2);
   		PrintWriter pw = response.getWriter();
   		JSONObject obj = new JSONObject();
   		try {
			User u = getFriend.getOneFriend(userId1,userId2);
			System.out.println(u);
			if (u == null) {
				obj.put("message", "Friend not found");
				
			}else {
			
			obj.put("fullName",u.getFullName());
			obj.put("email",u.getEmail());
			obj.put("phoneNumber",u.getPhoneNumber());
			obj.put("gender",u.getGender());
			obj.put("dateOfBirth",u.getDateOfBirth().toString());
			obj.put("address",u.getAddress());
			obj.put("city",u.getCity());
			obj.put("state",u.getState());
			obj.put("country",u.getCountry());
			obj.put("company",u.getCompany());
			obj.put("userName",u.getUsername());
			if(u.getProfileImage()==null) {
				obj.put("profileImage",null);	
			}else {
			String base64Image = Base64.getEncoder().encodeToString(u.getProfileImage());
			obj.put("profileImage",base64Image);
			}
			
			
			
			}
			System.out.println(obj.toJSONString());
			
			StringWriter out = new StringWriter();
			obj.writeJSONString(out);
			String jsonText = out.toString();

			pw.println(jsonText);
			pw.close();

			
		}catch(Exception e ) {
			e.printStackTrace();
		}
//   		catch (notYourFriendExcpetion e) {
//			e.showExceptionMessage();
//		}
		
	}
   	
//   	public int MapUserNameToUserId(String userName) {
//   		try {
//			Statement stmt=con.createStatement();
//			String st = "Select * from Users where userName='"+userName+"'";
//			ResultSet rs = stmt.executeQuery(st);
//			if(rs.next()) {
//				return rs.getInt(1);
//			}
//			
//   		} catch (SQLException e) {
//   			e.printStackTrace();
//		}
//   		return 0;
//   	}
   	
   	
}
