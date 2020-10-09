package com.hsbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
	public void addUser(User user) throws SQLException {
		
			Connection con = DbUtility.getConnection();
			PreparedStatement myStmt = null;
			myStmt = con.prepareStatement("insert into users"
					+ " (fullName, email, phoneNumber,gender,dateOfBirth,address,city,state,country,company,profileImage,userName,password)"
					+ " values (?, ?,?, ?, ?, ?, ?, ?, ?, ?, null, ?, ?)");

			myStmt.setString(1, user.getFullName());
			myStmt.setString(2, user.getEmail());
			myStmt.setLong(3, user.getPhoneNumber());
			myStmt.setString(4, user.getGender());
			myStmt.setDate(5, (Date) user.getDateOfBirth());
			myStmt.setString(6, user.getAddress());
			myStmt.setString(7, user.getCity());
			myStmt.setString(8, user.getState());
			myStmt.setString(9, user.getCountry());
			myStmt.setString(10, user.getCompany());
			// myStmt.setBlob(11, user.getprofileImage());
			myStmt.setString(11, user.getUsername());
			myStmt.setString(12, user.getPassword());
			// execute SQL
			myStmt.executeUpdate();

		

	}

	@Override
	public List<User> getUser(String field, String value) {

		Connection con = DbUtility.getConnection();
		System.out.println(value);
		System.out.println(field);
		List<User> list = new ArrayList<>();

		try {
			String sql = "";
			if (field.equals("userName")) {
				sql = "select * from users where userName=?";
			} else if (field.equals("userId")) {

				sql = "select * from users where userId=?";
			} else if (field.equals("fullName")) {
				// only the current user
				sql = "select * from users where fullName=?";
			} else if (field.equals("location")) {
				sql = "select * from users where location=?";
			} else if (field.equals("company")) {
				sql = "select * from users where company=?";
			} else if (field.equals("email")) {
				sql = "select * from users where email=?";
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			System.out.println(sql);

			if (field.equals("userId")) {
				int id = Integer.parseInt(value);
				stmt.setInt(1, id);
			} else {
				stmt.setString(1, value);
			}

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				int userId = rs.getInt("userId");
				if (!IsDisabledUtility.isDisabled(userId)) {
					User user = new User();
					user.setFullName(rs.getString("fullName"));
					user.setEmail(rs.getString("email"));
					user.setPhoneNumber(rs.getLong("phoneNumber"));
					user.setGender(rs.getString("gender"));
					user.setDateOfBirth(rs.getDate("dateOfBirth"));
					user.setAddress(rs.getString("address"));
					user.setCity(rs.getString("city"));
					user.setState(rs.getString("state"));
					user.setCountry(rs.getString("country"));
					user.setCompany(rs.getString("company"));
					// profileImage
					user.setUsername(rs.getString("userName"));
					user.setPassword(rs.getString("password")); // hashing
					list.add(user);
				}

			}
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void deactivateUser(int userId) {
		try {
			Connection con = DbUtility.getConnection(); 
			
			PreparedStatement myStmt = null;
			myStmt =con.prepareStatement("insert into deactivatedUsers"
					+ " (userId,timeOfDeactivation)"
					+" values (?, CURRENT TIMESTAMP)");
			myStmt.setInt(1,userId);
			myStmt.executeUpdate();	
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(User user) {
		try {
			Connection con = DbUtility.getConnection();
			PreparedStatement myStmt = null;
			// prepare statement
			myStmt=con.prepareStatement("update users set fullName = ?, email = ?, phoneNumber = ?,gender = ?,dateOfBirth = ?,address = ?,city = ?,state = ?,country = ?,company = ?,userName = ?,password = ? "
					+"where userId = ?");
			//myStmt.setString(1, theUser.getFirstName());
			myStmt.setString(1,user.getFullName());
			myStmt.setString(2, user.getEmail());
			myStmt.setLong(3, user.getPhoneNumber());
			myStmt.setString(4,user.getGender());
			myStmt.setDate(5,(Date) user.getDateOfBirth());
			myStmt.setString(6, user.getAddress());
			myStmt.setString(7, user.getCity());
			myStmt.setString(8, user.getState());
			myStmt.setString(9, user.getCountry());
			//myStmt.setBlob(11, user.getprofileImage());
			myStmt.setString(10, user.getCompany());
			myStmt.setString(11,user.getUsername());
			myStmt.setString(12,user.getPassword());
			
			myStmt.setInt(13, user.getUserId());
			
			// execute SQL
			int i;
			i=myStmt.executeUpdate();	
			System.out.println(i);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

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
	public User getOneFriend(int userId1,int userId2){
		Connection con =DBUtility.getConnection();
		try {
			Statement stmt=con.createStatement();
			String st = "Select * from FriendList where friendOne="+userId1+" and friendTwo="+userId2;
			ResultSet rs = stmt.executeQuery(st);
//			ResultSet rs2=null; 
			if(rs.next()) {
				int friendId=rs.getInt(2);
				String st2 = "select * from Users where userId="+friendId;
				ResultSet rs2= stmt.executeQuery(st2);
				if(rs2.next()) {
					User friend = new User();
					friend.setUserId(rs2.getInt(1));
					friend.setFullName(rs2.getString(2));
					friend.setEmail(rs2.getString(3));
					friend.setPhoneNumber(rs2.getLong(4));
					friend.setGender(rs2.getString(5));
					friend.setDateOfBirth(rs2.getDate(6));
					friend.setAddress(rs2.getString(7));
					friend.setCity(rs2.getString(8));
					friend.setState(rs2.getString(9));
					friend.setCountry(rs2.getString(10));
					friend.setCompany(rs2.getString(11));
					friend.setUsername(rs2.getString(13));
				return friend;
				}
			}
//			else {
//				throw new NotYourFriendException("you both are not friends .Sorry but you cannot see the person's details. To explore more please send him a friend request");
//			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}return null;
		
	}
	
	@Override
	public List<User> getAllFriends(int userId){
		Connection con =DBUtility.getConnection();
		List<User> list = new ArrayList<User>();
		try {
			Statement stmt=con.createStatement();
			String st = "Select * from FriendList where friendOne="+userId;
			ResultSet rs = stmt.executeQuery(st);
			ResultSet rs2=null;
			while(rs.next()) {

				int friendId=rs.getInt(2);
//				Statement stmt3= con.createStatement();
//				String st3 = "Select * from DeactivatedUsers where userId="+friendId;
//				ResultSet rs3=stmt3.executeQuery(st3);
//				if(!rs3.next()) {
					if(!isDeactivated(friendId)) {////
//					Statement stmt4= con.createStatement();
//					String st4 = "Select * from  DisabledUsers where userId="+friendId;
//					ResultSet rs4=stmt4.executeQuery(st4);
					if(!isDisabled(friendId)) {
						Statement stmt5= con.createStatement();
						String st5 = "Select * from  BlockedUsers where userone="+userId+" and usertwo="+friendId;
						ResultSet rs5=stmt5.executeQuery(st5);
						if(!rs5.next()) {
							Statement stmt2 = con.createStatement();
							String st2 = "select * from Users where userId="+friendId;
							rs2= stmt2.executeQuery(st2);
							if(rs2.next()) {
								User friend = new User();
								friend.setUserId(rs2.getInt(1));
								friend.setFullName(rs2.getString(2));
								friend.setEmail(rs2.getString(3));
								friend.setPhoneNumber(rs2.getLong(4));
								friend.setGender(rs2.getString(5));
								friend.setDateOfBirth(rs2.getDate(6));
								friend.setAddress(rs2.getString(7));
								friend.setCity(rs2.getString(8));
								friend.setState(rs2.getString(9));
								friend.setCountry(rs2.getString(10));
								friend.setCompany(rs2.getString(11));
								friend.setUsername(rs2.getString(13));
								list.add(friend);
							}
						}
					}//
				}
			}
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String removeFriend(int userId1, int userId2) {
		Connection con = DBUtility.getConnection();
		try {
			String st="delete from FriendList where friendOne=? and FriendTwo=?";
			PreparedStatement pst=con.prepareStatement(st);
			pst.setInt(1, userId1);
			pst.setInt(2, userId2);
			int numberOfRows=pst.executeUpdate();
			
			st="delete from BlockedUsers where userone=? and userTwo=?";
			pst=con.prepareStatement(st);
			pst.setInt(1, userId1);
			pst.setInt(2, userId2);
			pst.executeUpdate();
			System.out.println(numberOfRows);
			if(numberOfRows>0) {
				return "success";
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public String blockFriend(int userId1,int userId2) {
		Connection con = DBUtility.getConnection();
		String mssg="";
		try {
			Statement stmt= con.createStatement();
			String st="select * from BlockedUsers where userOne="+userId1+" and userTwo="+userId2;
			ResultSet rs = stmt.executeQuery(st);
			if(rs.next()) {
			}else {
				String query="insert into BlockedUsers values (?,?)";
				PreparedStatement pst=con.prepareStatement(query);
				pst.setInt(1, userId1);
				pst.setInt(2, userId2);
				int numberOfRows=pst.executeUpdate();
				if(numberOfRows>0) {
					mssg="success";
				}
				Statement stmt2=con.createStatement();
				String st2 = "select userTwo,count(userTwo) from BlockedUsers group by userTwo having userTwo="+userId2;
				ResultSet rs2 =stmt2.executeQuery(st2);
				if(rs2.next()) {
					int numberOfTimesBlocked=rs2.getInt(2);
					if(numberOfTimesBlocked>3) {
//						Statement stmt3=con.createStatement();
//						String st3="select * from DisabledUsers where userId="+userId2;
//						ResultSet rs3=stmt3.executeQuery(st3);
//						if(rs3.next()) {
//						}else {
							if(!isDisabled(userId2)) {
								System.out.println("inserting in disabled");
								query="insert into DisabledUsers values (?,FALSE)";
								pst=con.prepareStatement(query);
								pst.setInt(1, userId2);
								pst.executeUpdate();
//						}
							}	
					}
				}
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return mssg;
	}
	
	@Override
	public HashMap<User,String> showPendingRequest(int userId){
		Connection con =DBUtility.getConnection();
		
		HashMap<User,String> map = new  HashMap<User,String>();
		try {
			Statement stmt=con.createStatement();
			String st = "Select * from PendingFriendList where userOne="+userId;
			ResultSet rs = stmt.executeQuery(st);
			while(rs.next()) {
				int pendingId=rs.getInt(2);
				Statement stmt2=con.createStatement();
				String st2 = "Select * from Users where userId="+pendingId;
				ResultSet rs2 = stmt2.executeQuery(st2);
				if(rs2.next()) {
					User u = new User();
					u.setUsername(rs2.getString("userName"));
					u.setFullName(rs2.getString("fullName"));
//					u.setUserId(rs2.getInt("userId"));
					String message = rs.getString("message");
					map.put(u, message);
				}
			}
			
		return map;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return map;
		
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

	@Override
	public boolean activateUser(int userId) {

		Connection con=DbUtility.getConnection();
		String query="delete from deactivatedUsers where userId = ?";
		int count=0;
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, userId);
			count=ps.executeUpdate();
			if(count==0)
			{
				//System.out.println("User not found exception");
			}
			else
			{
				System.out.println("Activated user : "+userId);
			}
			
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count!=0;
	}
	
	@Override
	public void updateActivity(int userId,boolean loginOrRegister,boolean loginOrLogout) {
		// remove active hours
		//login = false register = true 
		//login = false logout = true
		Connection con=DbUtility.getConnection();
		
		if(loginOrRegister)
		{
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String query="insert into activity values(?,?,?,?)";
			
			try {
				
				PreparedStatement ps=con.prepareStatement(query);
				ps.setInt(1, userId);
				ps.setTimestamp(2, timestamp);
				ps.setTimestamp(3, timestamp);
				ps.setInt(4, 0);
				
				int c=ps.executeUpdate();
				if(c!=0)
				{
					System.out.println("Activity inserted for id : "+userId);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		else
		{
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String query="update activity set timestamp=? where userId = ?";		//change activity to current login/logout time
			
			int count=0,count2=0;
			try {
				
				
				
				if(loginOrLogout)
				{
					// logging out requires activeHours update
					
					String query1="select timestamp,activeHours from activity where userId=?";
					PreparedStatement ps1=con.prepareStatement(query1);
					ps1.setInt(1, userId);
					ResultSet rs=ps1.executeQuery();
					
					Timestamp loginTimestamp=null;
					double activeHours=0;
					while(rs.next())
					{
						loginTimestamp=rs.getTimestamp(1);
						activeHours=rs.getDouble(2);
					}
					
					System.out.println("Login Timestamp : "+loginTimestamp);
					System.out.println("Logout Timestamp : "+timestamp);
					System.out.println("Active hours before : "+activeHours);
					
					long milliseconds1 = loginTimestamp.getTime();
					long milliseconds2 = timestamp.getTime();
					
					long diff = milliseconds2 - milliseconds1;
					float diffSeconds = (float)diff / 1000;
					float diffMinutes = (float)diff / (60 * 1000);
					float diffHours = (float)diff / (60 * 60 * 1000);
					float diffDays = (float)diff / (24 * 60 * 60 * 1000);
					
					System.out.println("Hours : "+diffHours);
					System.out.println("Active hours after : "+(activeHours+diffHours));
					
					activeHours+=diffHours;
					
					String query2="update activity set activeHours=?where userId=?";
					PreparedStatement ps2=con.prepareStatement(query2);
					ps2.setDouble(1, activeHours);
					ps2.setInt(2, userId);
					count2=ps2.executeUpdate();
					ps2.close();


				}
				
				//update timestamp same for login and logout
				PreparedStatement ps=con.prepareStatement(query);
				

				ps.setTimestamp(1, timestamp);
				ps.setInt(2, userId);
				
				
				count=ps.executeUpdate();
				if(count==0)
				{
					//System.out.println("User not found exception");
				}
				else
				{
					System.out.println("Timestamp updated for id : "+userId);
				}
				
				
				ps.close();
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

	}
}
