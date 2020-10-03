/**
 * 
 * Current use : temporary UI
 * 
 * 
 */

package com.hsbc.main;

import java.util.ArrayList;

import com.hsbc.dao.AdminDao;
import com.hsbc.dao.AdminDaoImpl;
import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.Admin;
import com.hsbc.domain.User;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AdminDao adminDao=new AdminDaoImpl();
		
		//adminDao.disableUser(109);
		/*
		 * 
		 * ArrayList<User> list=(ArrayList<User>)
		 * adminDao.listOfPossibleDisabledUsers(); for(User u:list) {
		 * System.out.println(u.getUserId()+"\t"+u.getUsername()); }
		 */
		Admin admin=adminDao.getAdmin("admin1", "admin1");
		if(admin!=null)
		{
			System.out.println("Name : "+admin.getName()+" "
					+ "Email : "+admin.getEmail()+" "
					+ "Phone number : "+admin.getPhoneNumber()
			);
			
		}
	}

}
