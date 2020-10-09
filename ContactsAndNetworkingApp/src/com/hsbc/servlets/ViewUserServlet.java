package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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

/**
 * Servlet implementation class ViewUserServlet
 * 
 * Used for two purposes:
 * 1) Displaying existing user information before updating
 * 2) Search users based on field and value and checking validation of users before displaying them.
 * 
 */
public class ViewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw=response.getWriter();
		
		String field=request.getParameter("field");
		String value=request.getParameter("value");
		String userName=request.getParameter("userName");
		
		UserDao userDao=new UserDaoImpl();
		
		
		JSONObject obj;
		StringWriter out=new StringWriter();
		
		if(userName.equals("")) {			//Displaying existing user information before updating
			
			obj = new JSONObject();
			ArrayList<User> userList = (ArrayList<User>) userDao.getUser(field, value);
			User user = userList.get(0);
			System.out.println(user.getUsername());				
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
//				obj.put("proile", value)
				obj.put("userName", user.getUsername());
				obj.put("message", "Successful");
			    obj.writeJSONString(out);
			
		}
		
		else {								//Search for users
			int id = userDao.getUserId(userName);
			//pw.println(id);
			System.out.println(id);
			ArrayList<User> list = (ArrayList<User>)userDao.getUser(field, value);
			JSONArray array = new JSONArray();
			
			int userId,checkid,isblock=0;
			
			if(list.isEmpty()) {
				obj = new JSONObject();//No users found
				obj.put("message", "User is disabled or not available.");
				//obj.writeJSONString(out);
			}
			else {
				for(User u:list) {
					
					userId = userDao.getUserId(u.getUsername());
					System.out.println(userId);
					isblock=0;
					if(userId!=id) {			//Avoiding self id while searching
						
						if(userDao.isDeactivated(userId)) {
							obj = new JSONObject();
							System.out.println("this id"+userId+"is deactivated");
							obj.put("message", "User is deactivated.");
							array.add(obj);
							//obj.writeJSONString(out);
						}
						else {
							
							List<User> blockedlist= userDao.getBlockedUsers(userId);
							
							for(User blockuser:blockedlist) {
								checkid = userDao.getUserId(blockuser.getUsername());
								System.out.println(checkid);
								if(id==checkid) {
									obj = new JSONObject();
									obj.put("message", "Sorry, User has blocked you.");
									array.add(obj);
									//obj.writeJSONString(out);
									isblock=1;
									break;
								}
							}
							if(isblock==0){
								obj = new JSONObject();
								System.out.println(u.getFullName());
								obj.put("fullName", u.getFullName());
								obj.put("email", u.getEmail());
								obj.put("phoneNumber", u.getPhoneNumber());
								obj.put("gender", u.getGender());
								obj.put("dateOfBirth", u.getDateOfBirth());
								obj.put("address", u.getAddress());
								obj.put("city", u.getCity());
								obj.put("state", u.getState());
								obj.put("country", u.getCountry());
								obj.put("company", u.getCompany());
								obj.put("userName", u.getUsername());
								
								array.add(obj);
							}		
						
						}
					
					}
				}
				
				JSONObject users=new JSONObject();
				users.put("usersList", array);
				//	array.writeJSONString(out);
				users.writeJSONString(out);
			
			}
		}
		
		String jsonText=out.toString();		
		pw.println(jsonText);
		pw.close();
		
	}
}
