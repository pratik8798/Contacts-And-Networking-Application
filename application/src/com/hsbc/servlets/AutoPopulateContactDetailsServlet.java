package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.Contact;
import com.hsbc.domain.User;
import com.hsbc.exceptions.ContactNotExistException;

/**
 * Servlet implementation class AutoPopulateContactDetailsServlet
 */
public class AutoPopulateContactDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("IN the method");

		UserDao cdao = new UserDaoImpl();
		PrintWriter pw = response.getWriter();
		JSONArray array = new JSONArray();
		StringWriter out = new StringWriter();
		String jsonText = "";
		JSONObject obj = null;

		String email = request.getParameter("email");
		
		
		
		System.out.println(email);
		
		

		ArrayList<User> list = (ArrayList<User>) cdao.getUser("email", email);
		System.out.println(list.size());
		if (list.size() != 0) {
			User u = list.get(0);

			obj = new JSONObject();

			obj.put("city", u.getCity());
			obj.put("state", u.getState());
			obj.put("country", u.getCountry());
//			+ ", " + u.getState() + ", " + u.getCountry());
			obj.put("fullName", u.getFullName());
			obj.put("email", u.getEmail());
			obj.put("address", u.getAddress());
			obj.put("company", u.getCompany());
			obj.put("dateOfBirth", u.getDateOfBirth().toString());
			obj.put("gender", u.getGender());
			obj.put("phoneNumber", u.getPhoneNumber());
			if(u.getProfileImage()==null) {
				obj.put("profileImage",null);	
			}else {
			String base64Image = Base64.getEncoder().encodeToString(u.getProfileImage());
			obj.put("profileImage",base64Image);
			}

			array.add(obj);
		} else {
			try {
				ArrayList<Contact> listC = (ArrayList<Contact>) cdao.getContactForAutoPopulation(email);
				System.out.println(listC.size());
				if (listC.size() != 0) {
					Contact u = listC.get(0);

					obj = new JSONObject();

					obj.put("location", u.getCity() + ", " + u.getState() + ", " + u.getCountry());
					obj.put("fullName", u.getFullName());
					obj.put("email", u.getEmail());
					obj.put("address", u.getAddress());
					obj.put("company", u.getCompany());
					obj.put("dateOfBirth", u.getDateOfBirth().toString());
					obj.put("gender", u.getGender());
					obj.put("phoneNumber", u.getPhoneNumber());
					if(u.getProfileImage()==null) {
						obj.put("profileImage",null);	
					}else {
					String base64Image = Base64.getEncoder().encodeToString(u.getProfileImage());
					obj.put("profileImage",base64Image);
					}
					array.add(obj);
				} else {
					obj = new JSONObject();
					obj.put("message", "Contact Details Not Available");
				}
			} catch (ContactNotExistException e) {
				obj = new JSONObject();
				obj.put("message", "Contact Details Not Available");
			}
		}
		obj.writeJSONString(out);

		jsonText = out.toString();

		pw.println(jsonText);
		pw.close();

	}

}
