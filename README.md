# Contacts-And-Networking-Application

## Table of contents
* [About the Project](#about-the-project)
* [Tools And Technologies](#tools-and-technologies)
* [Setup](#setup)
* [Usage](#usage)


## About the Project
 Contacts and Networking application is a Java based web application where users can create an account, add contacts, view other users, add other users as friends. Also users can block or unblock other users. Also the application provides separate interface for admin where admin can disable users, delete users and monitor user activity.
	
## Tools And Technologies
Project is created with:
* Java JDK version : 8
* Apache Derby Database version : 10.14
* Apache Tomcat version : 9.0.38
* Spring Tool Suite version : 4.7.2

Technologies : 
* Java
* HTML
* CSS
* JavaScript
* Ajax
* Servlets
	
## Setup
To run this project, Apache Tomcat server and Apache Derby database must be present on the system.

Steps :
1) Open Spring Tool Suite and open this project in the worksapce.

2) Right Click on the project -> Run As -> Run on Server

3) Select the Runtime with correct Apache Tomcat version.

4) The Home page will appear on localhost:8080.

(Please note if there is some clash with port numbers, change the receiver port in server.xml file)

Also following jar files are needed for derby to work in embedded environment and JSON parsing.

Go to WebContent -> WEB-INF -> lib folder and paste following jar files 
1) derby.jar
2) derbyclient.jar
3) derbynet.jar
4) derbyrun.jar
5) derbyshared.jar
6) derbytools.jar
7) json-simple-1.1.jar

## Usage
1) Go to localhost:port_no/ContactsAndNetworkingApp/

2) You will be directed to the home page where you can register or login.

3) If you have already registered as an user, enter the valid credentials and you will be directed to user dashboard where you can search users, send and accept friend request, add contacts or even block a user.

4) If you have admin privileges, login with admin credentials. You will be directed to admin dashboard where you can disable or even delete one or more users and can monitor user activity.

Admin Credentials:
Username: Admin1
Password: Admin1

# Note: Update the paths in DbUitility for database and FileUtility for admin's xml file.
