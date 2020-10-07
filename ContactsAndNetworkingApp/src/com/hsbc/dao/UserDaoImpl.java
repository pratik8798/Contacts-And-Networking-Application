package com.hsbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.hsbc.domain.Contact;
import com.hsbc.domain.User;
import com.hsbc.utility.DbUtility;

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
	public void unblockUser(int userId, int userId2) { // In this i also need the two
		System.out.println("unblock" +userId +" "+userId2);					// userIds
		Connection con = DbUtility.getConnection();
		try {
		// We need to unblock the user by deleting it form db only for the current user
		// like userOne
		String query = "delete from BlockedUsers where userTwo=? and userOne=?";
		PreparedStatement pst = con.prepareStatement(query);
		
		pst.setInt(1, userId2);
		pst.setInt(2, userId);
		if(!isDisabled(userId2)) {
		
			int count = 0;
			int c1 = 0;
			String query3 = "Select count(*) from BLOCKEDUSERS where userTwo=" + userId2;
			
			Statement stmt = con.createStatement();
			
			ResultSet rs1 = stmt.executeQuery(query3);
			
			while (rs1.next()) {
				count = rs1.getInt(1);	
			}
			
			System.out.println("count"+count);
			PreparedStatement pst2 = null;
			int c = pst.executeUpdate();
			if (count <= 3)
			{		
				String query2 = "delete from DISABLEDUSERS where userId=?";  // checking if the user is disabled																// deleting
				System.out.println(query2);
				
				pst2 = con.prepareStatement(query2);
				pst2.setInt(1,userId2);	
				c1 = pst2.executeUpdate();	
				
			}
			
			System.out.println("c1"+c1);
			con.close();
			if (c == 0 && c1==0) {
				System.out.println("No user");
			}
		}
			
		} catch (SQLException se) {
			se.printStackTrace();
		}

}
	@Override
	public void sendFriendRequest(int senderId, int receiverId, int message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDisabled(int userId1) {
		Connection con = DbUtility.getConnection();
		try {

			String st = "Select isDisabled  from DISABLEDUSERS where userId=" + userId1;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(st);
			boolean isdisable = false;
			
			
			if (rs.next()) {
				isdisable = rs.getBoolean(1);
				con.close();
				return isdisable;
			} else {
				con.close();
				return false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isDeactivated(int userId1) {
		// TODO Auto-generated method stub
		return false;
	}

}
