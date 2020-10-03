package com.hsbc.dao;

import java.util.List;

import com.hsbc.domain.Admin;
import com.hsbc.domain.User;

public interface AdminDao {
	Admin getAdmin(String userName,String password);
	List<User> getTotalUsers();				//int count or list of users
	void disableUser(int userId);			//username or user id;
	void deleteUser(int userId);			//username or user id;
	List<User> listOfPossibleDisabledUsers();	//should be hashmap of users and whether disabled or not
	List<User> listOfPossibleDeletedUsers();
}
