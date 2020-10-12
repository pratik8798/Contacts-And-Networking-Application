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
import com.hsbc.exceptions.ContactNotExistException;

public class ViewAllContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	
	public ViewAllContactServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    @SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try{
    		PrintWriter pw=response.getWriter();
    	
    	String userName=request.getParameter("userName");
		
    	UserDao contactDao = new UserDaoImpl();
		int userId = contactDao.getUserId(userName);
		ArrayList<Contact> contactList=(ArrayList<Contact>) contactDao.getAllContacts(userId);
		
		JSONArray array=new JSONArray();
		StringWriter out=new StringWriter();
		String jsonText="";
		

		if(contactList.size()==0) {
			JSONObject obj=new JSONObject();
			obj.put("message", "No Contacts To Display");
			obj.writeJSONString(out);
		}
		
		else {
			for(Contact u:contactList)
			{
				JSONObject obj=new JSONObject();
				
				obj.put("location", u.getCity()+", "+u.getState()+", "+u.getCountry() );
				obj.put("fullName", u.getFullName());
				obj.put("address", u.getAddress());
				obj.put("company", u.getCompany());
				obj.put("email", u.getEmail());
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
			}
			
			JSONObject contacts = new JSONObject();
			contacts.put("contactList", array);
			contacts.writeJSONString(out);
			System.out.println(contacts.toJSONString());
		}
		
		jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
	}catch(ContactNotExistException e) {
		e.showMessage();
	}
    }

}
