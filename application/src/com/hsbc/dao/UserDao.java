/**
 * Name : UserDao.java
 * Use : Interface for UserDaoImpl
 */
package com.hsbc.dao;

import java.sql.SQLException;
import java.util.*;

import com.hsbc.domain.Contact;
import com.hsbc.domain.User;
import com.hsbc.exceptions.ContactNotExistException;

public interface UserDao {

	int getUserId(String userName);

	void addUser(User user) throws SQLException;

	List<User> getUser(String field, String value); // field=Name/Location/Company value

	

	void updateUser(User user); // decide userId,field,value or user object

	void addNewContact(int userId, Contact contact) throws SQLException; // addNewContact or addContact

	List<Contact> getContact(int userId, String field, String value) throws ContactNotExistException; // field=Name/Location/Company value

	List<Contact> getAllContacts(int userId) throws ContactNotExistException;

	String removeContact(int userId, int contactId); // decide userId or userName

	String updateContact(Contact contact); // decide userId,field,value or Contact object

	User getOneFriend(int userId1, int userId2);

	List<User> getAllFriends(int userId);

	String removeFriend(int userId1, int userId2);

	String blockFriend(int userId1, int userId2);

	HashMap<User, String> showPendingRequest(int userId); // check name's'

	String ignoreFriendRequest(int senderId, int receiverId);

	String acceptFriendRequest(int userId1, int userId2);

	List<User> getBlockedUsers(int userId); // check name 's'

	String unblockUser(int blocker, int blocked); // userId1 will unblock userId2

	String sendFriendRequest(int senderId, int receiverId, String message);
	
	boolean activateUser(int userId);

	void updateActivity(int userId, boolean loginOrRegister,boolean loginOrLogout);

	int getContactId(String email);

	List<Contact> getContactForAutoPopulation(String value) throws ContactNotExistException;

	void deactivateUser(int userId);
	

}
