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
import com.hsbc.domain.Contact;

@MultipartConfig(maxFileSize = 16177215)
public class CreateContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CreateContactServlet() {
    	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		String fullName = request.getParameter("fullName");
		System.out.println("email " + request.getParameter("email"));
		String email = request.getParameter("email");
//		System.out.println("phone number 1 = "+ request.getParameter("phoneNumber"));
		System.out.println("phone number 2 = " + request.getParameter("phoneNumber"));
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
		System.out.println(userName+" "+gender+" "+email);
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

		
		
		Contact contact = new Contact();
		contact.setFullName(fullName);
		contact.setEmail(email);
		contact.setGender(gender);
		contact.setPhoneNumber(phoneNumber);
		contact.setAddress(address);
		contact.setCity(city);
		contact.setState(state);
		contact.setCountry(country);
		contact.setCompany(company);
		contact.setDateOfBirth(dateOfBirth);
		contact.setProfileImage(profileBytes);
		
		UserDao contactDao = new UserDaoImpl();
		
		try {
			contactDao.addNewContact(contactDao.getUserId(userName), contact);
			obj.put("message", "Successful");
			System.out.println("SuccessFully Added Contact");
		}catch(SQLException e) {
			e.printStackTrace();
			obj.put("message", "Contact with this Email Already Added");
		}
		catch(Exception e) {
			e.printStackTrace();
			obj.put("message", "Contact Creation Failed");
		}
		
		
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString();

		pw.println(jsonText);
		pw.close();
		
	}

}
