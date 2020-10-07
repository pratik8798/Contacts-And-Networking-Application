/**
 * 
 */
 
 http=new XMLHttpRequest();
	
	function getDisabledUsersList()
	{
		table = document.getElementById("tableOfDisabledUsers")
		checkBoxes = table.getElementsByTagName("input");
        message = ""
 
        
        for (i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].checked) {
                disableUser(checkBoxes[i].id)
                
            }
        }
 
        
	}
	function disableUser(userId) {

		
		http.onreadystatechange=displayDisableUser;
		
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
				
				//alert(obj.message)
				document.getElementById("display").innerHTML=obj.message;
			
			}
			
			
			
				
			
		}
	}
		