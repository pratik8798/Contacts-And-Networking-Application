package com.hsbc.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hsbc.domain.Admin;
import com.hsbc.domain.User;
import com.hsbc.utility.DbUtility;

public class AdminDaoImpl implements AdminDao{

	@Override
	public Admin getAdmin(String userName,String password) {
		/**
		 * 
		 * Input : takes username and password to check credentials
		 * Output : If username and password matches, returns admin object else null
		 * 
		 * Reads an xml file containing admin information to authenticate the admin
		 */
		boolean flag=false;
		Admin admin=new Admin();
		try {

			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
   
			File inputFile=new File("F:\\CODEFURY\\ContactsAndNetworkingApp\\resources\\Admin.xml");
			Document doc=builder.parse(inputFile);
			
			doc.getDocumentElement().normalize();			
			NodeList listOfAdmins=doc.getElementsByTagName("admin");
			
			for(int i=0;i<listOfAdmins.getLength();i++)
			{
				Node adminNode=listOfAdmins.item(i);

				if(adminNode.getNodeType()==adminNode.ELEMENT_NODE)
				{
					
					Element adminElement=(Element) adminNode;
					
					String adminUserName=adminElement.getElementsByTagName("userName").item(0).getTextContent();
					String adminPassword=adminElement.getElementsByTagName("password").item(0).getTextContent();
					if(adminUserName.equals(userName) && adminPassword.equals(password))
					{

						admin.setName(adminElement.getElementsByTagName("name").item(0).getTextContent());
						admin.setEmail(adminElement.getElementsByTagName("email").item(0).getTextContent());
						admin.setPhoneNumber(Long.parseLong(adminElement.getElementsByTagName("phoneNumber").item(0).getTextContent()));
						flag=true;
						return admin;
						
					}
					else if(adminUserName.equals(userName) && !adminPassword.equals(password))
					{
						System.out.println("Wrong Password");
					}
					
					
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!flag)
		{
			System.out.println("Login credentials failed");
		}
		
		
		
		return null;
	}

	@Override
	public List<User> getTotalUsers()
	{
		/**
		 * 
		 * Input : No input required
		 * Output : Returns List of all users
		 * 
		 * Gets basic user details like user name, location of all users.
		 * 
		 */
		Connection con=DbUtility.getConnection();
		String query="SELECT userId,userName,city,state,country from Users";
		ArrayList<User> users=new ArrayList<>();
		try {
			
			Statement stmt=con.createStatement();
			
			ResultSet rs=stmt.executeQuery(query);
			
			while (rs.next()) {
				User u=new User();
				u.setUserId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setCity(rs.getString(3));
				u.setState(rs.getString(4));
				u.setCountry(rs.getString(5));
				users.add(u);
				
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public boolean disableUser(int userId) {
		
		/**
		 * 
		 * Input : user id
		 * Output : returns whether disabled successfully or not
		 * 
		 * Disables the user and updates the entry in disabledUsers
		 * 
		 */
		
		Connection con=DbUtility.getConnection();
		String query="update DisabledUsers set isDisabled = true where userId = ?";
		int count=0;
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, userId);
			count=ps.executeUpdate();
			if(count==0)
			{
				System.out.println("User not found exception");
			}
			

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count!=0;
	}

	@Override
	public boolean deleteUser(int userId) {
		
		/**
		 * 
		 * Input : user id
		 * Output : returns whether deleted successfully or not
		 * 
		 * deletes the user entry from user table
		 * 
		 */
		
		Connection con=DbUtility.getConnection();
		String query="delete from Users where userId = ?";
		int count=0;
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, userId);
			count=ps.executeUpdate();
			if(count==0)
			{
				System.out.println("User not found exception");
			}
			
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count!=0;
	}

	@Override
	public HashMap<User,Boolean> listOfPossibleDisabledUsers() {	
		/**
		 * 
		 * Input : No input required
		 * Output : Returns List of users who can be disabled by admin
		 * 
		 * Gets basic user details like user name, location  and disabled status of users  who can be disabled
		 * 
		 */
		Connection con=DbUtility.getConnection();
		String query=" select disabledusers.userid,username,city,state,country,isdisabled from disabledusers left join users on users.userid=disabledusers.userid";
				
		HashMap<User, Boolean> hash=new HashMap<>();

		
		
		try {
			
			Statement stmt=con.createStatement();	
			
			ResultSet rs=stmt.executeQuery(query);
						
			while (rs.next()) {
				
				User u=new User();
				u.setUserId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setCity(rs.getString(3));
				u.setState(rs.getString(4));
				u.setCountry(rs.getString(5));
				hash.put(u, rs.getBoolean(6));

				
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hash;
	}

	@Override
	public HashMap<User, Double> listOfPossibleDeletedUsers() {
		/**
		 * 
		 * Input : No input required
		 * Output : Returns List of users who can be deleted by admin
		 * 
		 * Gets basic user details like user name, location of users and activeHours who can be deleted
		 * 
		 */
		Connection con=DbUtility.getConnection();
		final int MINIMUM_HOURS=2;
		
		String query=" select activity.userid,username,city,state,country,activeHours from activity left join users on users.userid=activity.userid where activehours< ?";		
		HashMap<User, Double> hash=new HashMap<>();
		
		
		
		try {
			
			PreparedStatement ps1=con.prepareStatement(query);
			ps1.setInt(1, MINIMUM_HOURS);
			
			ResultSet rs=ps1.executeQuery();
			
			while (rs.next()) {
				
				User u=new User();
				u.setUserId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setCity(rs.getString(3));
				u.setState(rs.getString(4));
				u.setCountry(rs.getString(5));
				hash.put(u, rs.getDouble(6));
				
				
				
			}
			
			rs.close();
			ps1.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hash;		
	}
	
	@Override
	public LinkedHashMap<User,Double> listOfMostActiveUsers() {
		
		/**
		 * 
		 * Input : No input required
		 * Output : Returns List of most active users based on activityHours.
		 * 
		 * Gets basic user details like user name, location of user and activeHours
		 * 
		 */
		Connection con=DbUtility.getConnection();
		
		
		String query=" select activity.userid,username,city,state,country,activeHours from activity left join users on users.userid=activity.userid order by activeHours desc fetch first 5 rows only";
		
		
		LinkedHashMap<User, Double> hash=new LinkedHashMap<>();
		
		
		
		try {
			
			PreparedStatement ps1=con.prepareStatement(query);
			
			
			ResultSet rs=ps1.executeQuery();
			
			while (rs.next()) {
				
				User u=new User();
				u.setUserId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setCity(rs.getString(3));
				u.setState(rs.getString(4));
				u.setCountry(rs.getString(5));
				hash.put(u, rs.getDouble(6));
				
				
				
			}
			
			rs.close();
			ps1.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hash;		
	}

	
}

