package com.hsbc.dao;


import java.util.LinkedHashMap;
import java.util.List;

import com.hsbc.domain.Admin;
import com.hsbc.domain.User;

public interface AdminDao {
	Admin getAdmin(String userName,String password);
	List<User> getTotalUsers();				//int count or list of users
	boolean disableUser(int userId);			//username or user id;
	boolean deleteUser(int userId);			//username or user id;
	LinkedHashMap<User,Boolean> listOfPossibleDisabledUsers();	//should be hashmap of users and whether disabled or not
	LinkedHashMap<User, Double> listOfPossibleDeletedUsers();
	LinkedHashMap<User, Double> listOfMostActiveUsers();
}
