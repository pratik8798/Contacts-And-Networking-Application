package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hsbc.dao.AdminDao;
import com.hsbc.dao.AdminDaoImpl;
import com.hsbc.domain.User;


/**
 *
 * Servlet to view list of most active users
 * Receives : None
 * Output : usersList
 * 				usersList : JSONArray of users object
 * 				user object consists of : userId, userName, Location, active hours sorted as per active hours
 * 
 * 
 */
public class ViewMostActiveUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
PrintWriter pw=response.getWriter();
		
		AdminDao adminDao=new AdminDaoImpl();
		
		HashMap<User, Double> hash=adminDao.listOfMostActiveUsers(); 
		
		JSONArray array=new JSONArray();
		String jsonText="";
		
		for(Map.Entry<User, Double> s:hash.entrySet() )
		{
			User u=s.getKey();
			double activeHours=s.getValue();
			
			JSONObject obj=new JSONObject();

			
			obj.put("userId",u.getUserId() );
			obj.put("userName", u.getUsername());
			obj.put("location", u.getCity()+", "+u.getState()+", "+u.getCountry() );
			obj.put("activeHours", activeHours);
			array.add(obj);
		}

		
		JSONObject mostActiveUsers=new JSONObject();
		mostActiveUsers.put("mostActiveUsersList", array);
		
		
		StringWriter out=new StringWriter();
		mostActiveUsers.writeJSONString(out);
		jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();

	}

}
