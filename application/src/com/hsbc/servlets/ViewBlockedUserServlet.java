package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
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

public class ViewBlockedUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			try {
				PrintWriter pw = response.getWriter();

				String userName = request.getParameter("userName");
				System.out.println(userName);
				UserDao user = new UserDaoImpl();
				int userId1 = user.getUserId(userName);
				System.out.println(userId1);

				JSONArray array = new JSONArray();

				ArrayList<User> list = (ArrayList<User>) user.getBlockedUsers(userId1);
				JSONObject obj = new JSONObject();
				StringWriter out = new StringWriter();
				if (list.size() == 0) {
					obj.put("message", "No blocked user found.");
					obj.writeJSONString(out);
				} else {
					for (User u : list) {
						obj = new JSONObject();

						obj.put("userName", u.getUsername());
						obj.put("fullName", u.getFullName());
											
						if(u.getProfileImage()==null) {
							obj.put("profileImage",null);	
						}else {
						String base64Image = Base64.getEncoder().encodeToString(u.getProfileImage());
						obj.put("profileImage",base64Image);
						}

						array.add(obj);

					}
					JSONObject blockedUser = new JSONObject();
					blockedUser.put("friendList", array);
//			array.writeJSONString(out);
					blockedUser.writeJSONString(out);

				}
				String jsonText = out.toString();
				pw.println(jsonText);
				pw.close();

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

}
