<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
		
	<c:if test = "${message==null}">
   
	
		<img src="data:image/jpg;base64,${image}" width="240" height="300"/>
	     
  	</c:if>
  	<c:if test="${message!=null}">
  		<h2>${message}</h2> 
  	</c:if>
	 
		 
		
		 
		 
	
</body>
</html>