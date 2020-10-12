/**
 * 
 */
 
 http=new XMLHttpRequest();
	
	function getDisabledUsersList()
	{
		table = document.getElementById("tableOfDisabledUsers")
		checkBoxes = table.getElementsByTagName("input");
        
 		message=""
        
        for (i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].checked) {
                message+=checkBoxes[i].id
                disableUser(checkBoxes[i].id)
                
            }
        }
        alert(message)
 
        
	}
	function disableUser(userId) {

		
		http.onreadystatechange=displayDisableUser;
		//alert(userId);
		http.open("POST", "disableUser", true);
		http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		http.send("userId="+userId);
		
		
	}
	function displayDisableUser()
	 {
		if(http.readyState==4)
		{
			if (http.status == 200) 
			{
				res=http.responseText;
				obj=JSON.parse(res);
				
				
				document.getElementById("display").innerHTML=obj.message;
				document.getElementById("adminDisabledUsers").click()
			
			}
			
			
			
				
			
		}
	}
		