package com.hsbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hsbc.domain.Contact;
import com.hsbc.domain.User;
import com.hsbc.utility.DbUtility;
import com.hsbc.utility.IsDeactivatedUtility;
import com.hsbc.utility.IsDisabledUtility;

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
	public String ignoreFriendRequest(int senderId, int receiverId) {

		Connection con = DbUtility.getConnection();
		try {

			// delete from the table if recevier has having sender id as a pending request
			// else return exception
			String query = "delete from PendingFriendList where userOne=? and userTwo=?";
			PreparedStatement pst = con.prepareStatement(query);

			pst.setInt(1, senderId);
			pst.setInt(2, receiverId);

			int c = pst.executeUpdate();

			if (c != 0)
				return "Success";
		
		}
		
		catch (SQLException se) {
			se.printStackTrace();
		}
		
		return "";
		
		

	}
	@Override
	public String acceptFriendRequest(int userId1, int userId2) {

		Connection con = DbUtility.getConnection();
		String query = "insert into FRIENDLIST values(?,?)";

		try {

			PreparedStatement psmt = con.prepareStatement(query);

			psmt.setInt(1, userId1);
			psmt.setInt(2, userId2);

			int c=psmt.executeUpdate();
			if(c!=0)
				
			{
				return "Success";
			}
			

		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return "";

	}
	

	@Override
	public List<User> getBlockedUsers(int userId) { // need a userId of the user for which we want to get blocked user
													// as an argument
		//
		Connection con = DbUtility.getConnection();
		List<User> list = new ArrayList<>();
		List<User> list2 = new ArrayList<>();
		
		try {

			String st = "Select userTwo from BLOCKEDUSERS where userOne=" + userId;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(st);
			while (rs.next()) {
				int blockedUserId = rs.getInt(1);// these are the blocked user id's for particular user
				if (!IsDisabledUtility.isDisabled(blockedUserId) && !IsDeactivatedUtility.isDeactivated(blockedUserId)) {
					String userIdstr = Integer.toString(blockedUserId);
					list = (List<User>) getUser("userId", userIdstr); // getting the user details using the blocked userI
					list2.add(list.get(0));
				}
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list2;

	}

	@Override
	public String unblockUser(int userId, int userId2) { // Getting the two userId's i.e sender and receiver
		System.out.println(userId+"---->"+userId2);
		Connection con = DbUtility.getConnection();
		try {
			// We need to unblock the user by deleting it form db only for the current user
			// like userOne
			String query = "delete from BlockedUsers where userTwo=? and userOne=?";
			String query2 = "Select count(*) from BLOCKEDUSERS where userTwo=" + userId2;
			
			PreparedStatement pst = con.prepareStatement(query);

			pst.setInt(1, userId);
			pst.setInt(2, userId2);
			
			if(!IsDisabledUtility.isDisabled(userId2)) {
			
			int count = 0;
			int c1 = 0;
			
			Statement stmt = con.createStatement();
		
			ResultSet rs1 = stmt.executeQuery(query2);
			
			int c = pst.executeUpdate();
			
			while (rs1.next()) {
				count = rs1.getInt(1);	
			}
			
			System.out.println(count);
			
			PreparedStatement pst2 = null;
			
			if (count <= 3)
			{		
				String query3 = "delete from DISABLEDUSERS where userId=?";  // checking if the user is disabled																// deleting
				pst2 = con.prepareStatement(query3);
				pst2.setInt(1,userId2);	
				c1 = pst2.executeUpdate();	
			}
			
			System.out.println("c" +c);
			
			System.out.println("c1"+c1);

			if (c != 0 ) {
			
				return "Success";
			}
			con.close();
			}
			
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return "";

	}

	@Override
	public String sendFriendRequest(int senderId, int receiverId, String message){

		Connection con = DbUtility.getConnection();
		String query = "insert into PendingFriendList values(?,?,?)";
		boolean isBlock = false;
		boolean isdeactivate = false;
		
		try {
			isdeactivate = IsDeactivatedUtility.isDeactivated(receiverId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<User> listofBlocked = getBlockedUsers(receiverId); // got all the blocked user of particular user using
																// receiver userid

		for (User u : listofBlocked) {
			if (u.getUserId() == senderId) // check if the sender is in block list of user or not
			{
				isBlock = true;
			}
		}
		
		if (!isBlock && !isdeactivate) { // if the user is not blocked and the receiver is not deactivated
			try {

				PreparedStatement psmt = con.prepareStatement(query);

				psmt.setInt(1, senderId);
				psmt.setInt(2, receiverId);
				psmt.setString(3, message);

				int c=psmt.executeUpdate();
				
				if(c!=0)
				{
					return "Success";
				}
			}

			catch (SQLException e1) {

				e1.printStackTrace();
			}
			
		}
		return "";
	
	}
	

}
