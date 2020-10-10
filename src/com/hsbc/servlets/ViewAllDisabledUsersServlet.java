package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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
 * Servlet to view list of possible users to be disabled *
 * 
 * Receives : None
 * Output : disabledUsersList
 * 				disabledUsersList : JSONArray of users object
 * 				user object consists of : userId, userName, Location
 */
public class ViewAllDisabledUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		
		AdminDao adminDao=new AdminDaoImpl();
		
		HashMap<User,Boolean> hash=adminDao.listOfPossibleDisabledUsers(); 
		
		
		JSONArray array=new JSONArray();
		String jsonText="";
		
		for(Map.Entry<User, Boolean> s:hash.entrySet() )
		{
			User u=s.getKey();
			boolean b=s.getValue();
			
			JSONObject obj=new JSONObject();

			
			//System.out.println(u.getUserId()+"\t"+u.getUsername()+"\t"+b);
			obj.put("userId",u.getUserId() );
			obj.put("userName", u.getUsername());
			obj.put("location", u.getCity()+", "+u.getState()+", "+u.getCountry() );
			obj.put("isDisabled", b);
			array.add(obj);
		}

		
		JSONObject disabledUsers=new JSONObject();
		disabledUsers.put("disabledUsersList", array);
		
		
		StringWriter out=new StringWriter();
		disabledUsers.writeJSONString(out);
		jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
		/*
		String text="";
		text+="<tr>"+
				"<th> User Id </th>"+
				"<th> User Name </th>"+
				"<th> User Location </th>"+
				"<th> Is disabled? </th>"+
				"</tr>";
		
		for(Map.Entry<User, Boolean> s:hash.entrySet() )
		{
			User u=s.getKey();
			boolean b=s.getValue();
			//System.out.println(u.getUserId()+"\t"+u.getUsername()+"\t"+b);
			text+="<tr>"+
					"<td>"+ u.getUserId() +"</td>"+
					"<td>"+ u.getUsername() +"</td>"+
					"<td>"+ u.getCity()+", "+u.getState()+", "+u.getCountry() +"</td>"+
					"<td>"+ b +"</td>"+
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
