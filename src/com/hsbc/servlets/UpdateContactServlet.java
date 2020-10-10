package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.Contact;

/**
 * Servlet implementation class UpdateContactservlet
 */
public class UpdateContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateContactServlet() {
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
			String status = contactDao.updateContact(contact);
			if(status=="Update Successful") {
				obj.put("message", "Contact Successfully Updated");
				System.out.println("SuccessFully Added Contact");
			}
			else {
				obj.put("message", "Update Contact Details Failed");
				System.out.println("Contact Not Updated");
			}
		}catch(Exception e) {
			obj.put("message", "Contact Already Added");
			e.printStackTrace();
		}
		
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString();

		pw.println(jsonText);
		pw.close();
		
	}

}
