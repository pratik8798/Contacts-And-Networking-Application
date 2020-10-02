/**
 * Name : UserDao.java
 * Use : Interface for UserDaoImpl
 * Issues : naming issues, userId or userName conflict
 */
package com.hsbc.dao;

import java.util.List;

import com.hsbc.domain.Contact;
import com.hsbc.domain.User;

public interface UserDao {
	void addUser(User user);
	User getUser(String field,String value);		// field=Name/Location/Company value 
	void deactivateUser(int userId);				//decide userId or userName
	void updateUser(User user);						//decide userId,field,value or user object

	void addNewContact(Contact contact);			//addNewContact or addContact
	Contact getContact(String field,String value);	// field=Name/Location/Company value
	List<Contact> getAllContacts();
	void removeContact(int userId);					//decide userId or userName
	void updateContact(Contact contact);			//decide userId,field,value or Contact object
	
	void getOneFriend(int userId);
	List<User> getAllFriends();
	void removeFriend(int userId);
	void blockFriend(int userId);
	List<User> showPendingRequests();				//check name 's'
	void ignoreFriendRequest(int userId);			
	void addFriend(int userId1,int userId2);
	List<User> getBlockedUsers();					//check name 's'
	void unblockUser(int userId);
	void sendFriendRequest(User sender, User receiver);
	
	
	
	
}
