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

import com.hsbc.dao.AdminDao;
import com.hsbc.dao.AdminDaoImpl;
import com.hsbc.domain.User;
import com.hsbc.utility.IsDeactivatedUtility;
import com.hsbc.utility.IsDisabledUtility;

/**
 *
 * Servlet to view list of users
 * Receives : None
 * Output : usersList
 * 				usersList : JSONArray of users object
 * 				user object consists of : userId, userName, Location
 * 
 * 
 */
public class ViewAllUsersAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		
		AdminDao adminDao=new AdminDaoImpl();
		ArrayList<User> list=(ArrayList<User>) adminDao.getTotalUsers();
		
		JSONArray array=new JSONArray();
		String jsonText="";
		for(User u:list)
		{
			if(!IsDisabledUtility.isDisabled(u.getUserId()))
			{
				JSONObject obj=new JSONObject();
				
				obj.put("userId",u.getUserId() );
				obj.put("userName", u.getUsername());
				obj.put("location", u.getCity()+", "+u.getState()+", "+u.getCountry() );
				obj.put("active", !IsDeactivatedUtility.isDeactivated(u.getUserId()));
				array.add(obj);

			}
		}
		
		
		JSONObject users=new JSONObject();
		users.put("usersList", array);
		
		
		StringWriter out=new StringWriter();
		users.writeJSONString(out);
		jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
		
		/*
		String text="";
		text+="<tr>"+
				"<th> User Id </th>"+
				"<th> User Name </th>"+
				"<th> User Location </th>"+
				"</tr>";
		for(User u:list)
		{
			text+="<tr>"+
					"<td>"+ u.getUserId() +"</td>"+
					"<td>"+ u.getUsername() +"</td>"+
					"<td>"+ u.getCity()+", "+u.getState()+", "+u.getCountry() +"</td>"+
					"</tr>";
		}
				
		pw.println("<html>");
			pw.println("<body>");
				
				pw.println("<h2>List of all users </h2><br>");
				pw.println("<table border=2>");
					pw.println(text);
				pw.println("</table>");
					
					
			pw.println("</body>");
		pw.println("</html>");
		pw.close();
		
		*/
		
	}

}
