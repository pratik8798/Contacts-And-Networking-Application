 
 

document.getElementById("getAllFriend").addEventListener("click", () => {
 userName = "suhanirathi";
 
 
    submitUserDetails();
})

function submitUserDetails() {


    console.log(userName)
    http = new XMLHttpRequest();
    getAllFriends(userName);


}

function getAllFriends(userName) {

	
	console.log(userName)
    http.onreadystatechange = displayData
    http.open("POST", "viewAllFriend", true);
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName);



}

function displayData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
			
            console.log(res)


        }
        // document.getElementById("display").innerHTML = text;
    }

}