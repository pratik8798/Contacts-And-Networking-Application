package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.Contact;

public class CreateContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CreateContactServlet() {
    	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("userName");
		String fullName = request.getParameter("fullName");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		String company = request.getParameter("company");
		String dOB = request.getParameter("dateOfBirth");
		Date dateOfBirth = Date.valueOf(dOB);
		
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
		
		PrintWriter pw=response.getWriter();
		JSONObject obj = new JSONObject();
		UserDao contactDao = new UserDaoImpl();
		
		try {
			contactDao.addNewContact(contactDao.getUserId(userName), contact);
			obj.put("message", "Contact Successfully Added");
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
