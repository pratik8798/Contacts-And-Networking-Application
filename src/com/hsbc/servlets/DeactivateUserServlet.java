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
 * 
 * Servlet to deactivate user
 * 
 * Takes username and finds corresponding userid and calles the deactivateUser   
 * function of userDao.				
 * Receives : userName 
 * Output : message 
 * Author : Suhani Rathi
 */

public class DeactivateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw=response.getWriter();
		String userName = request.getParameter("userName");
		UserDao userDao=new UserDaoImpl();
		int userId = userDao.getUserId(userName);
		System.out.println(userId);
		
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
