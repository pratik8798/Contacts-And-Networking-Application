/* Name: Sahil Sharma 
 * This servlet accepts the userName of the user and 
 * the contact email of the contact. Then it passes the userId and contactId
 * received from getUserId and getContactId respectively to the removeContact method
 * which deletes the contact realtion of the user.*/

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
 * Servlet implementation class DeleteContactServlet
 */
public class DeleteContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteContactServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		String userName=request.getParameter("userName");
		String email=request.getParameter("email");
		
		UserDao contactDao = new UserDaoImpl();
		
		int userId = contactDao.getUserId(userName);
		int contactId = contactDao.getContactId(email);
		
		JSONObject obj=new JSONObject();
		String message = contactDao.removeContact(userId, contactId);
		if(message.equals("success"))
			obj.put("message", "Contact deleted successfully");
		else
			obj.put("message", "No Contact To Delete");
			
			System.out.println(obj.toJSONString());
		
		StringWriter out=new StringWriter();
		obj.writeJSONString(out);
		String jsonText=out.toString();
		
		pw.println(jsonText);
		pw.close();
	}

}
