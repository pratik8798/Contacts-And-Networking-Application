package com.hsbc.servlets;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;

/**
 * Servlet implementation class UpdateUserServlet
 */
@MultipartConfig(maxFileSize = 16177215)
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = new User();

		String fullName = request.getParameter("fullName");
		String email = request.getParameter("email");
		Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
		String gender = request.getParameter("gender");
		String strDate = request.getParameter("dateOfBirth");
		Date dateOfBirth = Date.valueOf(strDate);

		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		String company = request.getParameter("company");
		String userName = request.getParameter("userName");
		System.out.println( request.getPart("profileImage"));
		Part filePart = request.getPart("profileImage");

		InputStream inputStream = null;
		PrintWriter pw = response.getWriter();
		JSONObject obj = new JSONObject();
		byte[] profileBytes = null;

		if (filePart != null) {

			System.out.println(filePart.getName());
			System.out.println(filePart.getSize());
			inputStream = filePart.getInputStream();
			profileBytes = new byte[(int) filePart.getSize()];
			DataInputStream dis = new DataInputStream(inputStream);
			dis.readFully(profileBytes);
			System.out.println(profileBytes);

		} else {

			System.out.println("file  not found");
		}


		UserDao userDao = new UserDaoImpl();

		int userId = userDao.getUserId(userName);

		// pw.println(userId);

		user.setUserId(userId);
		user.setFullName(fullName);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setGender(gender);
		user.setDateOfBirth(dateOfBirth);
		user.setAddress(address);
		user.setCity(city);
		user.setState(state);
		user.setCountry(country);
		user.setCompany(company);
		user.setUsername(userName);

		user.setProfileImage(profileBytes);

		userDao.updateUser(user);

		obj.put("message", "Successful");

		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString();

		pw.println(jsonText);
		pw.close();

	}

}
