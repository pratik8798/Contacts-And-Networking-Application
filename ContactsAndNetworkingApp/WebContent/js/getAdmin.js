/**
 * 
 */
 	http=new XMLHttpRequest();
	
	function getAdmin() {
		
		userName=document.getElementById("userName").value;
		password=document.getElementById("password").value;

		http.onreadystatechange=displayAdmin;
		
		http.open("POST", "viewAdmin", true);
		http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		http.send("userName="+userName+"&password="+password);
		
		
	}
	
	function displayAdmin()
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