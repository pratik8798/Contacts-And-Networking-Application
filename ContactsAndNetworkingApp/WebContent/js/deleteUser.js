/**
 * 
 */
 
 /**
 * 
 */
 
 	http=new XMLHttpRequest();
	
	function getDeletedUsersList()
	{
		table = document.getElementById("tableOfDeletedUsers")
		checkBoxes = table.getElementsByTagName("input");
        message = ""
 
        
        for (i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].checked) {
            	message+=checkBoxes[i].id
                deleteUser(checkBoxes[i].id)
                
            }
        }
        alert(message)
 
        
	}
	function deleteUser(userId) {

		
		http.onreadystatechange=displayDeleteUser;
		
		http.open("POST", "deleteUser", true);
		http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		http.send("userId="+userId);
		
		
	}
	function displayDeleteUser() {
		if(http.readyState==4)
		{
			if (http.status == 200) 
			{
				res=http.responseText;
				obj=JSON.parse(res);
				
				document.getElementById("display").innerHTML=obj.message;
			
			}
			
			
			
				
			
		}
	}
		