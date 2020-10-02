package com.hsbc.dao;

import java.util.List;

import com.hsbc.domain.Admin;
import com.hsbc.domain.User;

public interface AdminDao {
	Admin getAdmin(String userName);
	List<User> getTotalUsers();				//int count or list of users
	void disableUser(String userName);		//username or user id;
	void deleteUser(String userName);		//username or user id;
}
