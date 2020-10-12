
document.getElementById("adminMostActiveUsers").addEventListener("click", () => {

	http = new XMLHttpRequest();

	getListOfMostActiveUsers();
})
	
	
	function getListOfMostActiveUsers() 
	{
		
	
		http.onreadystatechange=displayMostActiveUsers;
		
		http.open("POST", "viewMostActiveUsers", true);
		
		http.send();
		
	}
	function displayMostActiveUsers() 
	{
		if(http.readyState==4)
		{
			if (http.status == 200) 
			{
			
				res=http.responseText;
				obj=JSON.parse(res);
				
				
				text="<div id='contentDisplayerForAdmin'><center><h5>List of 5 Most Active Users</h5></center>"+
				"<br>"+
				"<table border=2 align='center' id='tableOfMostActiveUsers'>"+
				"<tr><th>User Id</th><th>User Name</th><th>Location</th><th>Active hours</th></tr>";
				
				for (id = 0; id < obj.mostActiveUsersList.length; id++) {
				    u = obj.mostActiveUsersList[id];
				    text+="<tr>";
									    
				    text+="<td>"+u.userId+"</td>";
				    text+="<td>"+u.userName+"</td>";
				    text+="<td>"+u.location+"</td>";
					text+="<td>"+u.activeHours.toFixed(2)+"</td>";
				    
				    
				    text+="</tr>"
					
				}
				text+="</table></div>";
				
				
				document.getElementById("displayUsers").innerHTML=text; 
			
			}
					
			
				
		}
		
	
	}
		