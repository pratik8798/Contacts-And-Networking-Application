/**
 * 
 * Servlet to view list of possible users to be deleted
 * Receives : None
 * Output : deletedUsersList
 * 				deletedUsersList : JSONArray of users object
 * 				user object consists of : userId, userName, Location, active hours
 * 
 * 
 */

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

public class ViewAllDeletedUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw=response.getWriter();
		
		AdminDao adminDao=new AdminDaoImpl();
		
		HashMap<User, Double> hash=adminDao.listOfPossibleDeletedUsers(); 
		
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

		
		JSONObject deletedUsers=new JSONObject();
		deletedUsers.put("deletedUsersList", array);
		
		
		StringWriter out=new StringWriter();
		deletedUsers.writeJSONString(out);
		jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();

		}

}
