 
 

document.getElementById("getFriend").addEventListener("click", () => {
 userName1 = "suhanirathi";
 userName2 = document.querySelector("#userName2").value
 console.log(userName2)
    submitFriendDetails();
})

function submitFriendDetails() {


    console.log(userName1 + " " + userName2)
    http = new XMLHttpRequest();
    login(userName1, userName2);


}

function login(userName1, userName2) {

	console.log(userName1);
	console.log(userName2)
    http.onreadystatechange = displayData
    http.open("POST", "viewOneFriend", true);
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName1=" + userName1 + "&userName2=" + userName2);



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