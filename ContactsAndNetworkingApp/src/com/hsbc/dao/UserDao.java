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
	
	int getUserId(String userName);
	
	void addUser(User user);
	User getUser(String field,String value);		// field=Name/Location/Company value 
	void deactivateUser(int userId);				//decide userId or userName
	void updateUser(User user);						//decide userId,field,value or user object

	void addNewContact(int userId,Contact contact);			//addNewContact or addContact
	Contact getContact(String field,String value);	// field=Name/Location/Company value
	List<Contact> getAllContacts();
	void removeContact(int userId,int contactId);					//decide userId or userName
	void updateContact(Contact contact);			//decide userId,field,value or Contact object
	
	User getOneFriend(int userId1,int userId2);		
	List<User> getAllFriends(int userId);
	void removeFriend(int userId1,int userId2);
	void blockFriend(int userId1,int userId2);
	List<User> showPendingRequests(int userId1);				//check name 's'
	void ignoreFriendRequest(int senderId,int receiverId);			
	void acceptFriendRequest(int userId1,int userId2);
	List<User> getBlockedUsers(int userId);					//check name 's'
	void unblockUser(int blocker,int blocked);			//userId1 will unblock userId2
	void sendFriendRequest(int senderId, int receiverId,int message);
	
	boolean isDisabled(int userId1);
	boolean isDeactivated(int userId1);
	
	
	
	
}
