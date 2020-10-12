package com.hsbc.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.User;

public class SearchUserServet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchUserServet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String userName1 = request.getParameter("userName1");
   		String userName2 = request.getParameter("userName2");
   		System.out.println(userName1 +" "+userName2);
   		
   		UserDao getFriend = new UserDaoImpl();
   		int userId1=getFriend.getUserId(userName1);
   		int userId2=getFriend.getUserId(userName2);
   		System.out.println("Reciever id"+userId2);
   		PrintWriter pw = response.getWriter();
   		JSONObject obj = new JSONObject();
   		try {
			User u = getFriend.getOneFriend(userId1,userId2);
			boolean flag=false;
			System.out.println(u);
			if (u == null) {
				
				HashMap<User,String> pendingFriendRequest = getFriend.showPendingRequest(userId2);
				for (Map.Entry mapElement : pendingFriendRequest.entrySet()) {
					 u = (User) mapElement.getKey();
					System.out.println("UserName after ending check "+u.getUsername());
					if(u.getUsername().equals(userName1)) {
						flag=true;
						System.out.println("UserName in pending check "+u.getUsername());
						obj.put("message","Pending Request");	
						break;
					}
				}
					if(flag==false)
					{
						HashMap<User,String> pendingFriendRequest2 = getFriend.showPendingRequest(userId1);
						for (Map.Entry mapElement : pendingFriendRequest2.entrySet()) {
							 u = (User) mapElement.getKey();
							System.out.println("UserName after ending check "+u.getUsername());
							if(u.getUsername().equals(userName2)) {
								flag=true;
								System.out.println("UserName in pending check "+u.getUsername());
								obj.put("message","Request already in pending list");	
								break;
							}
						}
						if(flag==false) {
							ArrayList<User> list = (ArrayList<User>) getFriend.getBlockedUsers(userId1);
							for (User u1 : list) {
								if(u1.getUsername().equals(userName2)) {
									flag=true;
									System.out.println("UserName in pending check "+u.getUsername());
									obj.put("message","Blocked User");	
									break;
								}
							}
							if(flag==false) {
								obj.put("message","Can send request");	
							}
							}
						
							
						}
					}
			
			else
			{
				obj.put("message","Already Friend");	
			}
		
			pw.println(obj.toJSONString());
			pw.close();  
			}
   	 catch(Exception e)
		{
			  e.printStackTrace();
		  
		}
	}

}
