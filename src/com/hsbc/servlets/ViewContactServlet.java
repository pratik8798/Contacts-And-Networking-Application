package com.hsbc.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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

public class ViewContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 public ViewContactServlet() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
    		PrintWriter pw=response.getWriter();
    	
    	String userName = request.getParameter("userName");
    	String field = request.getParameter("field");
    	String value = request.getParameter("value");
		
    	UserDao contactDao = new UserDaoImpl();
		int userId = contactDao.getUserId(userName);
		ArrayList<Contact> contactList=(ArrayList<Contact>) contactDao.getContact(userId, field, value);
		
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
				obj.put("email", u.getEmail());
				obj.put("address", u.getAddress());
				obj.put("company", u.getCompany());
				obj.put("dateOfBirth", u.getDateOfBirth().toString());
				obj.put("gender", u.getGender());
				obj.put("phoneNumber", u.getPhoneNumber());
				
				array.add(obj);
			}
			
			JSONObject contacts=new JSONObject();
			contacts.put("contactList", array);
			contacts.writeJSONString(out);
			
		}
		
		jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
	}catch(ContactNotExistException e) {
		e.showMessage();
	}
	}

}
