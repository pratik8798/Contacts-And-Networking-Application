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
		
		boolean flag=false;
		Admin admin=new Admin();
		try {

			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();

			DocumentBuilder builder=factory.newDocumentBuilder();
	
			//String basePath = new File("").getAbsolutePath();
		    
			//System.out.println(basePath);
		    
			File inputFile=new File("C:\\Users\\mohit\\Documents\\GitHub\\Contacts-And-Networking-Application\\Contacts-And-Networking-Application\\ContactsAndNetworkingApp\\resources\\Admin.xml");
			
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
						//System.out.println("Wrong Password exception");
					}
					
					
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!flag)
		{
			//System.out.println("Login credentials failed");
		}
		
		
		
		return null;
	}

	@Override
	public List<User> getTotalUsers() {
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
			
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public boolean disableUser(int userId) {
		Connection con=DbUtility.getConnection();
		String query="update DisabledUsers set isDisabled = true where userId = ?";
		int count=0;
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, userId);
			count=ps.executeUpdate();
			if(count==0)
			{
				//System.out.println("User not found exception");
			}
			
			
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count!=0;
	}

	@Override
	public boolean deleteUser(int userId) {
		Connection con=DbUtility.getConnection();
		String query="delete from Users where userId = ?";
		int count=0;
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, userId);
			count=ps.executeUpdate();
			if(count==0)
			{
				//System.out.println("User not found exception");
			}
			
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count!=0;
	}

	@Override
	public HashMap<User,Boolean> listOfPossibleDisabledUsers() {			
		Connection con=DbUtility.getConnection();
		String query="select * from DisabledUsers";
		
		ArrayList<User> users=new ArrayList<>();
		
		HashMap<User, Boolean> hash=new HashMap<>();
		
		String getUserQuery="SELECT userId,userName,city,state,country from Users where userId=?";
		
		try {
			
			Statement stmt=con.createStatement();
			
			ResultSet rs=stmt.executeQuery(query);
			
			PreparedStatement ps=con.prepareStatement(getUserQuery);
			
			while (rs.next()) {
				
				int userId=rs.getInt(1);
				ps.setInt(1, userId);
				
				ResultSet user=ps.executeQuery();
				
				while (user.next()) {
					User u=new User();
					u.setUserId(user.getInt(1));
					u.setUsername(user.getString(2));
					u.setCity(user.getString(3));
					u.setState(user.getString(4));
					u.setCountry(user.getString(5));
					users.add(u);
					hash.put(u, rs.getBoolean(2));
				}
				
			}
			
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hash;
	}

	@Override
	public HashMap<User,Double> listOfPossibleDeletedUsers() {
		Connection con=DbUtility.getConnection();
		final double MINIMUM_HOURS=2;
		
		String query="select userId,activeHours from activity where activeHours < ?";
		
		ArrayList<User> users=new ArrayList<>();
		
		HashMap<User, Double> hash=new HashMap<>();
		
		String getUserQuery="SELECT userId,userName,city,state,country from Users where userId=?";
		
		try {
			
			PreparedStatement ps1=con.prepareStatement(query);
			ps1.setDouble(1, MINIMUM_HOURS);
			
			ResultSet rs=ps1.executeQuery();
			
			PreparedStatement ps2=con.prepareStatement(getUserQuery);
			
			while (rs.next()) {
				
				int userId=rs.getInt(1);
				ps2.setInt(1, userId);
				
				ResultSet user=ps2.executeQuery();
				
				while (user.next()) {
					User u=new User();
					u.setUserId(user.getInt(1));
					u.setUsername(user.getString(2));
					u.setCity(user.getString(3));
					u.setState(user.getString(4));
					u.setCountry(user.getString(5));
					users.add(u);
					hash.put(u, rs.getDouble(2));
				}
				
			}
			
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hash;		
	}

}
