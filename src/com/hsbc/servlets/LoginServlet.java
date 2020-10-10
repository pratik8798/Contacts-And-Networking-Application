package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;
import com.hsbc.utility.Decryption;
import com.hsbc.utility.IsDeactivatedUtility;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		UserDao userDao = new UserDaoImpl();
		ArrayList<User> userList = (ArrayList<User>) userDao.getUser("userName", userName);

		JSONObject obj = new JSONObject();

		if (userList.size() == 0) {
			obj.put("message", "Kindly check your credentials as either your username is invalid or you are disabled by the admin");
		} else {
			User user = userList.get(0);
			System.out.println(user.getUsername());
			System.out.println(user.getPassword());
			boolean flag = Decryption.verifyPassword(password, userDao.getUserId(userName));

			if(flag) {
				
				
				obj.put("fullName", user.getFullName());
				obj.put("email", user.getEmail());
				obj.put("phoneNumber", user.getPhoneNumber());
				obj.put("gender", user.getGender());
				obj.put("dateOfBirth",user.getDateOfBirth().toString());
				obj.put("address", user.getAddress());
				obj.put("city", user.getCity());
				obj.put("state", user.getState());
				obj.put("country", user.getCountry());
				obj.put("company", user.getCompany());
				if(user.getProfileImage()==null) {
					obj.put("profileImage",null);	
				}else {
				String base64Image = Base64.getEncoder().encodeToString(user.getProfileImage());
				obj.put("profileImage",base64Image);
				}
				obj.put("userName", user.getUsername());
				obj.put("message", "Successful");
				userDao.updateActivity(userDao.getUserId(user.getUsername()), false, false);
				if(IsDeactivatedUtility.isDeactivated(userDao.getUserId(user.getUsername()))) {
					userDao.activateUser(userDao.getUserId(user.getUsername()));
				}

			} else {
				obj.put("message", "You have entered an Invalid username or password. Kindly check and try again.");
			}

		}
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString();

		pw.println(jsonText);
		pw.close();

	}

}
