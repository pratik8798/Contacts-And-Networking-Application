package com.hsbc.dao;


import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.hsbc.domain.Contact;
import com.hsbc.domain.User;
import com.hsbc.exceptions.ContactNotExistException;
import com.hsbc.utility.DbUtility;
import com.hsbc.utility.Encryption;
import com.hsbc.utility.IsDeactivatedUtility;
import com.hsbc.utility.IsDisabledUtility;

public class UserDaoImpl implements UserDao {

	@Override
	public int getUserId(String userName) {
		Connection con = DbUtility.getConnection();
		String query = "SELECT userId from Users where userName=?";
		int userId = 0;
		try {

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				userId = rs.getInt(1);
			}

			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;

	}
	
	@Override
	public int getContactId(String email) {
		Connection con = DbUtility.getConnection();
		String query = "SELECT contactId from contacts where email=?";
		int contactId = 0;
		try {

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				contactId = rs.getInt(1);
			}

			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return contactId;

	}

	@Override
	public void addUser(User user) throws SQLException {

		Connection con = DbUtility.getConnection();
		PreparedStatement myStmt = null;
		myStmt = con.prepareStatement("insert into users"
				+ " (fullName, email, phoneNumber,gender,dateOfBirth,address,city,state,country,company,profileImage,userName,password)"
				+ " values (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
		myStmt.setBytes(11, user.getProfileImage());
		myStmt.setString(12, user.getUsername());
		String password = Encryption.GeneratePassword(user.getPassword());
		myStmt.setString(13, password);
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
				sql = "select * from users where country=?";
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
			System.out.println(sql);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				int userId = rs.getInt("userId");
				if (!IsDisabledUtility.isDisabled(userId)) {
					User user = new User();
					System.out.println(rs.getString("fullName"));
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
					if (rs.getBytes("profileImage") == null) {
						user.setProfileImage(null);
					} else {
						byte[] profileImage = rs.getBytes("profileImage");
						user.setProfileImage(profileImage);
					}

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
			myStmt = con.prepareStatement(
					"insert into deactivatedUsers" + " (userId,timeOfDeactivation)" + " values (?, CURRENT TIMESTAMP)");
			myStmt.setInt(1, userId);
			myStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(User user) {
		try {
			Connection con = DbUtility.getConnection();
			PreparedStatement myStmt = null;
			// prepare statement
			myStmt = con.prepareStatement(
					"update users set fullName = ?, email = ?, phoneNumber = ?,gender = ?,dateOfBirth = ?,address = ?,city = ?,state = ?,country = ?,company = ?,userName = ?,password = ? "
							+ "where userId = ?");
			// myStmt.setString(1, theUser.getFirstName());
			myStmt.setString(1, user.getFullName());
			myStmt.setString(2, user.getEmail());
			myStmt.setLong(3, user.getPhoneNumber());
			myStmt.setString(4, user.getGender());
			myStmt.setDate(5, (Date) user.getDateOfBirth());
			myStmt.setString(6, user.getAddress());
			myStmt.setString(7, user.getCity());
			myStmt.setString(8, user.getState());
			myStmt.setString(9, user.getCountry());
			// myStmt.setBlob(11, user.getprofileImage());
			myStmt.setString(10, user.getCompany());
			myStmt.setString(11, user.getUsername());
			myStmt.setString(12, user.getPassword());

			myStmt.setInt(13, user.getUserId());

			// execute SQL
			int i;
			i = myStmt.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addNewContact(int userId, Contact contact) throws SQLException { // this method adds the contact into
																					// contact object and
		// also an entry in contactUser table
		Connection con = DbUtility.getConnection();
		int execute;
		String query = "insert into contacts (fullName, email, phoneNumber, gender, dateOfBirth, address, city, state, country, company) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, contact.getFullName());
		stmt.setString(2, contact.getEmail());
		stmt.setLong(3, contact.getPhoneNumber());
		stmt.setString(4, contact.getGender());
		stmt.setDate(5, (Date) contact.getDateOfBirth());
		stmt.setString(6, contact.getAddress());
		stmt.setString(7, contact.getCity());
		stmt.setString(8, contact.getState());
		stmt.setString(9, contact.getCountry());
		stmt.setString(10, contact.getCompany());
		execute = stmt.executeUpdate();

		int contactId = 0;
		query = "select contactid from contacts where email='" + contact.getEmail() + "'";
		stmt = con.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			contactId = rs.getInt(1);
		}

		System.out.println("ContactId=" + contactId + "   User ID=" + userId);

		String query2 = "insert into userContacts (userid, contactid) values(?,?)";
		stmt = con.prepareStatement(query2);
		stmt.setInt(1, userId);
		stmt.setInt(2, contactId);
		stmt.executeUpdate();

		con.close();

	}

	@Override
	public List<Contact> getContact(int userId, String field, String value) throws ContactNotExistException { 
		// This method returns the list of all contacts based on some filters
		Connection con = DbUtility.getConnection();
		List<Contact> contactList = new ArrayList<Contact>();
		try {
			String query;

			query = "select contactId from userContacts where userId=" + userId;
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			boolean flag = false;

			while (rs.next()) {
				flag = true;
				Contact contact = new Contact();
				query = "select * from contacts where " + field + "='" + value + "' and contactId="
						+ rs.getString("contactId");

				Statement stmt1 = con.createStatement();
				System.out.println(query);
				ResultSet rs2 = stmt1.executeQuery(query);
				while (rs2.next()) {
					System.out.println(rs2.getString("fullname"));
					contact.setFullName(rs2.getString("fullName"));
					contact.setEmail(rs2.getString("email"));
					contact.setGender(rs2.getString("gender"));
					contact.setAddress(rs2.getString("address"));
					contact.setCity(rs2.getString("city"));
					contact.setState(rs2.getString("state"));
					contact.setCountry(rs2.getString("country"));
					contact.setCompany(rs2.getString("company"));
					contact.setPhoneNumber(rs2.getLong("phoneNumber"));
					contact.setDateOfBirth(rs2.getDate("dateOfBirth"));
					contactList.add(contact);
				}

			}

			System.out.println(contactList.size());

			if (flag == false)
				throw new ContactNotExistException();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	@Override
	public List<Contact> getAllContacts(int userId) throws ContactNotExistException { // This method returns the list of
		// all contacts
		Connection con = DbUtility.getConnection();
		List<Contact> contactList = new ArrayList<Contact>();
		try {
			String query = "select contactId from usercontacts where userID=" + userId;
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			boolean flag = false;

			while (rs.next()) {
				flag = true;
				Contact contact = new Contact();
				Statement stmt1 = con.createStatement();
				query = "select * from contacts where contactId=" + rs.getInt("contactId");
				ResultSet rs2 = stmt1.executeQuery(query);
				while (rs2.next()) {
					contact.setFullName(rs2.getString("fullName"));
					contact.setEmail(rs2.getString("email"));
					contact.setGender(rs2.getString("gender"));
					contact.setAddress(rs2.getString("address"));
					contact.setCity(rs2.getString("city"));
					contact.setState(rs2.getString("state"));
					contact.setCountry(rs2.getString("country"));
					contact.setCompany(rs2.getString("company"));
					contact.setPhoneNumber(rs2.getLong("phoneNumber"));
					contact.setDateOfBirth(rs2.getDate("dateOfBirth"));
					contactList.add(contact);
				}

			}
			if (flag == false)
				throw new ContactNotExistException();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	@Override
	public String removeContact(int userId, int contactId) { // This method removes contact for a particular user as
		// well deleted from records if it has no other
		// connections
		Connection con = DbUtility.getConnection();
		String message = "";
		try {
			int count = 0;
			String query = "select count(contactId) from userContacts where contactId=" + contactId;
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}

			query = "delete from userContacts where contactId=" + contactId + " and userId=" + userId;
			System.out.println(query);
			stmt = con.prepareStatement(query);
			int x = stmt.executeUpdate();
			if (x > 0)
				message = "success";

			if (count == 1) {
				query = "delete from contacts where contactId=" + contactId;
				stmt = con.prepareStatement(query);
				stmt.executeUpdate();
			}

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String updateContact(Contact contact) {
		Connection con = DbUtility.getConnection();
		int executed;
		try {
			String query = "update Contacts set fullName=?, phoneNumber=?,"
					+ " gender=?, dateOfBirth=?, address=?, city=?, state=?, country=?, company=? where email='"
					+ contact.getEmail() + "'";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, contact.getFullName());
			stmt.setLong(2, contact.getPhoneNumber());
			stmt.setString(3, contact.getGender());
			stmt.setDate(4, (Date) contact.getDateOfBirth());
			stmt.setString(5, contact.getAddress());
			stmt.setString(6, contact.getCity());
			stmt.setString(7, contact.getState());
			stmt.setString(8, contact.getCountry());
			stmt.setString(9, contact.getCompany());
			executed = stmt.executeUpdate();
			con.close();
			if (executed == 1)
				return "Update Successful";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public User getOneFriend(int userId1, int userId2) {
		/* The function takes in two parameters userid1  as id of the person
		 *  and userid2 as the id of friend whose information he wants to see 
		 *  and returns the friend.
		 */
		Connection con = DbUtility.getConnection();
		try {
			Statement stmt = con.createStatement();
			String st = "Select * from FriendList where (friendOne=" + userId1 + " and friendTwo=" + userId2
					+ ") or (friendOne=" + userId2 + " and friendTwo=" + userId1 + ")";
			ResultSet rs = stmt.executeQuery(st);
//			ResultSet rs2=null; 
			if (rs.next()) {
				int friend1 = rs.getInt(1);
				int friend2 = rs.getInt(2);
				int friendId;
				if (friend1 != userId1) {
					friendId = friend1;
				} else {
					friendId = friend2;
				}
				String st2 = "select * from Users where userId=" + friendId;
				ResultSet rs2 = stmt.executeQuery(st2);
				if (rs2.next()) {
					User friend = new User();

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
					if (rs2.getBytes("profileImage") == null) {
						friend.setProfileImage(null);
					} else {
						byte[] profileImage = rs2.getBytes("profileImage");
						friend.setProfileImage(profileImage);
					}

					return friend;
				}
			}
//			else {
//				throw new NotYourFriendException("you both are not friends .Sorry but you cannot see the person's details. To explore more please send him a friend request");
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<User> getAllFriends(int userId) {
		/* The function takes in argument as the userId of the person and return all his friends 
		 * the friend can be only seen if he is not deactivated nor disabled nor blocked.
		 */
		Connection con = DbUtility.getConnection();
		List<User> list = new ArrayList<User>();
		try {
			Statement stmt = con.createStatement();
			String st = "Select * from FriendList where friendOne=" + userId + "or friendTwo=" + userId;
			System.out.println(st);
			ResultSet rs = stmt.executeQuery(st);
			ResultSet rs2 = null;
			while (rs.next()) {
				int friend1 = rs.getInt(1);
				int friend2 = rs.getInt(2);
				int friendId;
				if (friend1 != userId) {
					friendId = friend1;
				} else {
					friendId = friend2;
				}

				Statement stmt3 = con.createStatement();
				String st3 = "Select * from DeactivatedUsers where userId=" + friendId;
				ResultSet rs3 = stmt3.executeQuery(st3);
				if (!rs3.next()) {
					Statement stmt4 = con.createStatement();
					String st4 = "Select * from  DisabledUsers where userId=" + friendId;
					ResultSet rs4 = stmt4.executeQuery(st4);
					if (!rs4.next()) {
						Statement stmt5 = con.createStatement();
						String st5 = "Select * from  BlockedUsers where userone=" + userId + " and usertwo=" + friendId;
						ResultSet rs5 = stmt5.executeQuery(st5);
						if (!rs5.next()) {
							Statement stmt2 = con.createStatement();
							String st2 = "select * from Users where userId=" + friendId;
							rs2 = stmt2.executeQuery(st2);
							if (rs2.next()) {
								User friend = new User();
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
								System.out.println(rs2.getBytes("profileImage"));
								if (rs2.getBytes("profileImage") == null) {
									friend.setProfileImage(null);
								} else {
									byte[] profileImage = rs2.getBytes("profileImage");
									friend.setProfileImage(profileImage);
								}

								list.add(friend);
							}
						}
					} //
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String removeFriend(int userId1, int userId2) {
		/*the function takes in two parameters userid1, id of person who wants to remove userid2 as his friend.
		 * and returns String message if the removal is succesful or not.
		 */
		Connection con = DbUtility.getConnection();
		try {
			String st = "delete from FriendList where friendOne=? and FriendTwo=?";
			PreparedStatement pst = con.prepareStatement(st);
			pst.setInt(1, userId1);
			pst.setInt(2, userId2);
			int numberOfRows = pst.executeUpdate();

			st = "delete from BlockedUsers where userone=? and userTwo=?";
			pst = con.prepareStatement(st);
			pst.setInt(1, userId1);
			pst.setInt(2, userId2);
			pst.executeUpdate();
			System.out.println(numberOfRows);
			if (numberOfRows > 0) {
				return "success";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String blockFriend(int userId1, int userId2) {
		/*This function gives ability to user with userId1 to block his friend with userId2
		 * and it also makes a userId2 disabled if the userId2 is blocked by more than 3 people.
		 */
		Connection con = DbUtility.getConnection();
		String mssg = "";
		try {
			Statement stmt = con.createStatement();
			String st = "select * from BlockedUsers where userOne=" + userId1 + " and userTwo=" + userId2;
			ResultSet rs = stmt.executeQuery(st);
			if (rs.next()) {
			} else {
				String query = "insert into BlockedUsers values (?,?)";
				PreparedStatement pst = con.prepareStatement(query);
				pst.setInt(1, userId1);
				pst.setInt(2, userId2);
				int numberOfRows = pst.executeUpdate();
				if (numberOfRows > 0) {
					mssg = "success";
				}
				Statement stmt2 = con.createStatement();
				String st2 = "select userTwo,count(userTwo) from BlockedUsers group by userTwo having userTwo="
						+ userId2;
				ResultSet rs2 = stmt2.executeQuery(st2);
				if (rs2.next()) {
					int numberOfTimesBlocked = rs2.getInt(2);
					if (numberOfTimesBlocked > 3) {
						Statement stmt3 = con.createStatement();
						String st3 = "select * from DisabledUsers where userId=" + userId2;
						ResultSet rs3 = stmt3.executeQuery(st3);
						if (rs3.next()) {
						} else {
							System.out.println("inserting in disabled");
							query = "insert into DisabledUsers values (?,FALSE)";
							pst = con.prepareStatement(query);
							pst.setInt(1, userId2);
							pst.executeUpdate();
						}
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mssg;
	}

	@Override
	public HashMap<User, String> showPendingRequest(int userId) {
		/*The function takes in argument as the userId of the person and return a list of the users 
		 * who had sent him a friendRequest and is not yet approved.
		 */
		Connection con = DbUtility.getConnection();

		HashMap<User, String> map = new HashMap<User, String>();
		try {
			Statement stmt = con.createStatement();
			String st = "Select * from PendingFriendList where userTwo=" + userId; // recieverId

			ResultSet rs = stmt.executeQuery(st);
			while (rs.next()) {
				int pendingId = rs.getInt(1); // senderId
				Statement stmt2 = con.createStatement();
				String st2 = "Select * from Users where userId=" + pendingId;
				ResultSet rs2 = stmt2.executeQuery(st2);
				if (rs2.next()) {
					User u = new User();
					u.setUsername(rs2.getString("userName"));
					u.setFullName(rs2.getString("fullName"));
					byte[] profileImage = rs2.getBytes("profileImage");
					u.setProfileImage(profileImage);
					String message = rs.getString("message");
					map.put(u, message);
				}
			}

			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;

	}

	
	@Override
	/*
	receiverId: The user who is going to ignore the friend request
	senderId: The user whose request is getting ignored by the receiver
	*/
	public String ignoreFriendRequest(int receiverId, int senderId) {
		

		Connection con = DbUtility.getConnection();
		try {

			
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
	/*
	
	userId1: Receiver (The one who is going to accept the freind request)
	userId2: Sender (The one who is sending freind request)
	
	*/
	public String acceptFriendRequest(int userId1, int userId2) {

		
		Connection con = DbUtility.getConnection();
		String query = "insert into FRIENDLIST values(?,?)";//Inserting into the Receiver's freindlist by accepting the request of sender

		try {

			PreparedStatement psmt = con.prepareStatement(query);

			psmt.setInt(1, userId1);
			psmt.setInt(2, userId2);

			int c = psmt.executeUpdate();
			if (c != 0)

			{
				String query1 = "delete from PendingFriendList where userOne=? and userTwo=?";//Once the request is accepted deleting the request from pending list of receiver
				psmt = con.prepareStatement(query1);
				psmt.setInt(1, userId2);
				psmt.setInt(2, userId1);
				psmt.executeUpdate();
				return "Success";
			}

		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return "";

	}

	@Override
	/*
	This functions displays all the blocked users present in the list of user whose userId is received as an argument
	
	*/
	public List<User> getBlockedUsers(int userId) {
		Connection con = DbUtility.getConnection();
		List<User> list = new ArrayList<>();
		List<User> list2 = new ArrayList<>();

		try {

			String st = "Select userTwo from BLOCKEDUSERS where userOne=" + userId;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(st);
			while (rs.next()) {
				int blockedUserId = rs.getInt(1);// these are the blocked user id's for particular user
				if (!IsDisabledUtility.isDisabled(blockedUserId)
						&& !IsDeactivatedUtility.isDeactivated(blockedUserId)) {
					String userIdstr = Integer.toString(blockedUserId);
					list = getUser("userId", userIdstr);  // getting the user details using the blocked user
					list2.add(list.get(0));
				}
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list2;

	}

	@Override

/* Getting the two userId's i.e sender and receiver
   Getting Two Userid  Where userId=(The one who is unblocking ) usereId2=(The one who is getting Unblocked)
	*/
	public String unblockUser(int userId, int userId2) { 
		System.out.println(userId + "---->" + userId2); 
		Connection con = DbUtility.getConnection(); 
		try {
			
			String query = "delete from BlockedUsers where userTwo=? and userOne=?";
			String query2 = "Select count(*) from BLOCKEDUSERS where userTwo=" + userId2;

			PreparedStatement pst = con.prepareStatement(query);

			pst.setInt(1, userId2);
			pst.setInt(2, userId);

			if (!IsDisabledUtility.isDisabled(userId2)) {

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

				if (count <= 3) {
					String query3 = "delete from DISABLEDUSERS where userId=?"; // checking if the user is disabled //
																				
					pst2 = con.prepareStatement(query3);
					pst2.setInt(1, userId2);
					c1 = pst2.executeUpdate();
				}

				System.out.println("c" + c);

				System.out.println("c1" + c1);

				if (c != 0) {

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
	/*
	senderId:The user is which is sending request
	receiverId:The user which is receving request
	message:The message which sender wants to send while sending request
	*/
	
	public String sendFriendRequest(int senderId, int receiverId, String message) {
		Connection con = DbUtility.getConnection();
		String query = "insert into PendingFriendList values(?,?,?)";
		boolean isBlock = false;
		boolean isdeactivate = false;

		try {
			isdeactivate = IsDeactivatedUtility.isDeactivated(receiverId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<User> listofBlocked = getBlockedUsers(receiverId);// Getting the blocked users of receivers using receiverId

		for (User u : listofBlocked) {
			if (u.getUserId() == senderId) // check if the sender is in block list of user or not
			{
				isBlock = true;
			}
		}

		if (!isBlock && !isdeactivate) { //Condition checking if the sender is not blocked and the receiver is not deactivated
			try {

				PreparedStatement psmt = con.prepareStatement(query);

				psmt.setInt(1, senderId);
				psmt.setInt(2, receiverId);
				psmt.setString(3, message);

				int c = psmt.executeUpdate();

				if (c != 0) {
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

		/**
		 * 
		 * Input : user id
		 * Output : returns whether user account is activated successfully or not
		 * 
		 * activates the user and removes the entry in deactivatedUsers table
		 * 
		 */
		Connection con=DbUtility.getConnection();
		String query="delete from deactivatedUsers where userId = ?";
		int count=0;
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, userId);
			count=ps.executeUpdate();
			if(count==0)
			{
				System.out.println("User not found ");
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
		/**
		 * 
		 * Input : user id, loginOrRegister,loginOrLogout flag
		 * 
		 * 
		 * loginOrRegister flag values : login = false register = true 
		 * loginOrLogout flag values : login = false logout = true
		 * 
		 * Output : returns whether disabled successfully or not
		 * 
		 * Updates activity of user in activity table
		 * 1)While Registering : insert an entry in activity table
		 * 2)While logging in : update timestamp of user
		 * 3)While logging out : update timestamp and calculate activeHours of user 
		 * 
		 */
		
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
			String query="update activity set timestamp=? where userId = ?";					
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
					System.out.println("User not found exception");
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
