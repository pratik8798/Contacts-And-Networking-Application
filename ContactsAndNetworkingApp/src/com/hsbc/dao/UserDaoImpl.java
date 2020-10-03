package com.hsbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.hsbc.domain.Contact;
import com.hsbc.domain.User;

public class UserDaoImpl implements UserDao{

	@Override
	public int getUserId(String userName) {
		Connection con=DbUtility.getConnection();
		String query="SELECT userId from Users where userName=?";
		int userId=0;
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, userName);
			ResultSet rs=ps.executeQuery();
			
			while (rs.next()) {
				userId=rs.getInt(1);
			}
			
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
		
		
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deactivateUser(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewContact(int userId, Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Contact getContact(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getAllContacts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeContact(int userId, int contactId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getOneFriend(int userId1, int userId2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllFriends(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeFriend(int userId1, int userId2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blockFriend(int userId1, int userId2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> showPendingRequests(int userId1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ignoreFriendRequest(int senderId, int receiverId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptFriendRequest(int userId1, int userId2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getBlockedUsers(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unblockUser(int blocker, int blocked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendFriendRequest(int senderId, int receiverId, int message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDisabled(int userId1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDeactivated(int userId1) {
		// TODO Auto-generated method stub
		return false;
	}

}
