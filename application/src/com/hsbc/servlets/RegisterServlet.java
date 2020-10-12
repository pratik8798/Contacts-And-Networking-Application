package com.hsbc.servlets;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;

@MultipartConfig(maxFileSize = 16177215)
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// http://localhost:8080/ContactsAndNetworkingApp/register?fullName=Mansi&email=abc&phoneNumber=98111111&gender=male&dateOfBirth=2020-02-09&address=Shimla&city=HP&state=HP&country=India&company=hsbc&userName=mansi&password=123456

		User user = new User();
		String fullName = request.getParameter("fullName");
		System.out.println("email " + request.getParameter("email"));
		String email = request.getParameter("email");
//		System.out.println("phone number 1 = "+ request.getParameter("phoneNumber"));
		Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
		System.out.println("phone number 2 = " + phoneNumber);

		String gender = request.getParameter("gender");
		String strDate = request.getParameter("dateOfBirth");
		Date dateOfBirth = Date.valueOf(strDate);

		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		String company = request.getParameter("company");
		String userName = request.getParameter("userName");

		// hashing of password
		String password = request.getParameter("password");
		// String profileImage = request.getParameter("profileImage");
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
		user.setPassword(password);

		user.setProfileImage(profileBytes);

		UserDao userDao = new UserDaoImpl();
				try{
					userDao.addUser(user);
					obj.put("message", "Successful");
					System.out.println("Successful");
					userDao.updateActivity(userDao.getUserId(user.getUsername()), true, false);
				}
				catch (SQLException e) {
					
					obj.put("message", "This Email or Username is already registered");
					System.out.println("Error");
					e.printStackTrace();
								
				}
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString();

		pw.println(jsonText);
		pw.close();

	}

}
