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

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public LogoutServlet() {
        super();

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		UserDao userDao=new UserDaoImpl();
		String userName = request.getParameter("userName"); //extracting username
		int userId = userDao.getUserId(userName); //getting userId
		System.out.println("...."+userName+" "+userId);
		userDao.updateActivity(userId, false, true);//updating the current timestamp
		
		PrintWriter pw=response.getWriter();
		JSONObject obj=new JSONObject();
		obj.put("message", "Successful");
		
		
		StringWriter out=new StringWriter();
		obj.writeJSONString(out);
		String jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
		
	}

}
