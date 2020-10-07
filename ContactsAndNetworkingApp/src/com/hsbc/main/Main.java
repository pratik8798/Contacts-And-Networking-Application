/**
 * 
 * Current use : temporary UI
 * 
 * 
 */

package com.hsbc.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hsbc.dao.AdminDao;
import com.hsbc.dao.AdminDaoImpl;
import com.hsbc.dao.UserDao;
import com.hsbc.dao.UserDaoImpl;
import com.hsbc.domain.Admin;
import com.hsbc.domain.User;
import com.hsbc.utility.IsDeactivatedUtility;
import com.hsbc.utility.IsDisabledUtility;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		UserDao userDao=new UserDaoImpl();
		userDao.unblockUser(102, 104);
		*/
		
		/*
		 * AdminDao adminDao=new AdminDaoImpl();
		 * 
		 * adminDao.deleteUser(10);
		adminDao.disableUser(10);
		
		Admin admin=adminDao.getAdmin("admin1", "admin1");
		if(admin!=null)
		{
			System.out.println("Name : "+admin.getName()+" "
					+ "Email : "+admin.getEmail()+" "
					+ "Phone number : "+admin.getPhoneNumber()
			);
			
		}
		
		AdminDao adminDao=new AdminDaoImpl();
		 HashMap<User, Integer> hash=adminDao.listOfPossibleDeletedUsers(); 
		for(Map.Entry<User, Integer> s:hash.entrySet() )
		{
			User u=s.getKey();
			int activeHours=s.getValue();
			System.out.println(u.getUserId()+"\t"+u.getUsername()+"\t"+u.getCity()+", "+u.getState()+", "+u.getCountry()+"\t"+activeHours);
		}
		
		System.out.println("*********************************");
		 HashMap<User, Boolean> hash1=adminDao.listOfPossibleDisabledUsers(); 
		for(Map.Entry<User, Boolean> s:hash1.entrySet() )
		{
			User u=s.getKey();
			Boolean b=s.getValue();
			System.out.println(u.getUserId()+"\t"+u.getUsername()+"\t"+u.getCity()+", "+u.getState()+", "+u.getCountry()+"\t"+b);
		}
		
		
		 */
		//System.out.println(IsDisabledUtility.isDisabled(109));
		System.out.println(IsDeactivatedUtility.isDeactivated(109));
	}

}
