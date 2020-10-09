package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;


/**
 * Servlet implementation class DeactivateUserServlet
 */
public class DeactivateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw=response.getWriter();
		int userId=Integer.parseInt(request.getParameter("userId"));
		
		UserDao userDao=new UserDaoImpl();
		
		JSONObject obj=new JSONObject();
		userDao.deactivateUser(userId);
		
		obj.put("message", "User deactivated successfully");
		
		
		StringWriter out=new StringWriter();
		obj.writeJSONString(out);
		String jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
		
		
	}

}
