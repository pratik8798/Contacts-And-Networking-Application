/**
 * 
 * Servlet to view list of possible users to be deleted
 * Receives : None
 * Output : deletedUsersList
 * 				deletedUsersList : JSONArray of users object
 * 				user object consists of : userId, userName, Location
 * 
 * 
 */

package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
		
		LinkedHashMap<User, Double> hash=adminDao.listOfPossibleDeletedUsers(); 
		
		JSONArray array=new JSONArray();
		String jsonText="";
		
		for(Map.Entry<User, Double> s:hash.entrySet() )
		{
			User u=s.getKey();
			double activeHours=s.getValue();
			
			JSONObject obj=new JSONObject();

			
			//System.out.println(u.getUserId()+"\t"+u.getUsername()+"\t"+b);
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

		
		/*
		String text="";
		text+="<tr>"+
				"<th> User Id </th>"+
				"<th> User Name </th>"+
				"<th> User Location </th>"+
				"<th> Active hours </th>"+
				"</tr>";
		
		for(Map.Entry<User, Integer> s:hash.entrySet() )
		{
			User u=s.getKey();
			int activeHours=s.getValue();
			//System.out.println(u.getUserId()+"\t"+u.getUsername()+"\t"+activeHours);
			text+="<tr>"+
					"<td>"+ u.getUserId() +"</td>"+
					"<td>"+ u.getUsername() +"</td>"+
					"<td>"+ u.getCity()+", "+u.getState()+", "+u.getCountry() +"</td>"+
					"<td>"+ activeHours+"</td>"+
					"</tr>";
		}
		
		
		pw.println("<html>");
			pw.println("<body>");
				
				pw.println("<h2>List of all users that can be disabled </h2><br>");
				pw.println("<table border=2>");
					pw.println(text);
				pw.println("</table>");
					
					
			pw.println("</body>");
		pw.println("</html>");
		pw.close();
		
		*/
		}

}
