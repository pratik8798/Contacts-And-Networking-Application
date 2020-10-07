/**
 * 
 */
 
 /**
 * 
 */
 
	http=new XMLHttpRequest();
	
	function getListOfPossibleDisabledUsers() 
	{
	
	
		http.onreadystatechange=displayAllDisabledUsers;
		
		http.open("POST", "viewAllDisabledUsers", true);
		
		http.send();
		
	}
	function displayAllDisabledUsers() 
	{
		if(http.readyState==4)
		{
			if (http.status == 200) 
			{
			
				res=http.responseText;
				obj=JSON.parse(res);
				
				text="<form action='disableUser' method='post'>";
				text+="<center><h2>List of Disabled Users</h2></center>"+
				"<br>"+
				"<table border=2 align='center' id='tableOfDisabledUsers'>"+
				"<tr><th></th><th>Disabled?</th><th>User Id</th><th>User Name</th><th>Location</th></tr>";
				
				for (id = 0; id < obj.disabledUsersList.length; id++) {
				    u = obj.disabledUsersList[id];
				    text+="<tr>";
				    if(u.isDisabled)
				    {
				    	text+="<td><input type='checkbox' disabled id='"+u.userId+"' name='"+u.userId+"' value='"+u.userId+"' ></td>";
				    	
				    	text+="<td>"+"Yes"+"</td>";
				    	
				    }
				    else
				    {
				    	text+="<td><input type='checkbox' id='"+u.userId+"' name='"+u.userId+"' value='"+u.userId+"'></td>";
				    	text+="<td>"+"No"+"</td>";
				    
				    	
				    	
				    }
				    text+="<td>"+u.userId+"</td>";
				    text+="<td>"+u.userName+"</td>";
				    text+="<td>"+u.location+"</td>";
				    
				    
				    text+="</tr>"
					
				}
				text+="</table>";
				text+="<center><input type='button' onclick='getDisabledUsersList()' value='Disable'><center>";
				text+="</form>";
				document.getElementById("display").innerHTML=text; 
				//
			}
					
			
				
		}
		
	
	}
		