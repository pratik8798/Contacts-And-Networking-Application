package com.hsbc.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

public class AdminDaoImpl implements AdminDao{

	@Override
	public Admin getAdmin(String userName,String password) {
		
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
						System.out.println("Wrong Password exception");
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
			System.out.println("Login credentials failed");
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
	public void disableUser(int userId) {
		Connection con=DbUtility.getConnection();
		String query="update DisabledUsers set isDisabled = true where userId = ?";
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, userId);
			ps.executeUpdate();
			
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(int userId) {
		Connection con=DbUtility.getConnection();
		String query="delete from Users where userId = ?";
		try {
			
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, userId);
			ps.executeUpdate();
			
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public List<User> listOfPossibleDisabledUsers() {			
		Connection con=DbUtility.getConnection();
		String query="select userId from DisabledUsers where isDisabled = false";
		
		ArrayList<User> users=new ArrayList<>();
		
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
					
				}
				
			}
			
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public List<User> listOfPossibleDeletedUsers() {
		// TODO Auto-generated method stub
		
		return null;
	}

}
