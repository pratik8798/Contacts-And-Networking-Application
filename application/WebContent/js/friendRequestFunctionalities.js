
var index1;
var requestParent = document.querySelector("#showPendingRequests");
requestParent.addEventListener("click", performRequestedFunction, false);

function performRequestedFunction(e) {
    if (e.target !== e.currentTarget) {


        str1 = e.target.id;

        console.log(str1);
        if (str1.includes("acceptFriend")) {

            index1 = str1.charAt(str1.length - 1)
            userNameR1 = localStorage.getItem("userName")
            //userNameR1 = "suhanirathi"
            id = "usernameRequestFriend" + index1;
            tempUserNameR2 = document.getElementById(id).innerHTML;
            userNameR2 = tempUserNameR2.slice(1);
            console.log(userNameR2)
            http = new XMLHttpRequest();
            acceptFriendRequest(userNameR1, userNameR2);

        }
        else if (str1.includes("ignoreFriend")) {
            console.log(str1)
            index1 = str1.charAt(str1.length - 1)
            userNameR1 = localStorage.getItem("userName")
            //userNameR1 = "suhanirathi"
            id = "usernameRequestFriend" + index1;
            tempUserNameR2 = document.getElementById(id).innerHTML;
            userNameR2 = tempUserNameR2.slice(1);
            console.log(userNameR2)
            http = new XMLHttpRequest();
            ignoreFriendRequest(userNameR1, userNameR2);
        }
     
    } else {
        e.stopPropagation();
    }

}

function acceptFriendRequest(userNameR1, userNameR2) {

    console.log(userNameR1);
    console.log(userNameR2)

    http.onreadystatechange = displayAcceptStatus
    http.open("POST", "acceptFriendRequest", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName1=" + userNameR1 + "&userName2=" + userNameR2);

}
function displayAcceptStatus() {
    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;


            obj = JSON.parse(res)
            if(obj.message=="Successful"){
            
            	 var parent = document.querySelector("#displayRequests");
                childId = "requestCard" + index1;
                console.log(childId);
                var child = document.getElementById(childId);
                console.log(child);
                parent.removeChild(child);
  				$("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = "Friend Succesfully Added to the Friendlist";
				}else{
				$("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = "Something went wrong. Please try again later";
					
				}


           
        }

    }

}


//ignore a friend
function ignoreFriendRequest(userNameR1, userNameR2) {

    console.log(userNameR1);
    console.log(userNameR2)
    console.log(index1)
    http.onreadystatechange = ignoreFriend
    http.open("POST", "ignoreFriendRequest", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName1=" + userNameR1 + "&userName2=" + userNameR2);

}
function ignoreFriend() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;


            obj = JSON.parse(res)
            if (obj.message == "Successful") {
              var parent = document.querySelector("#displayRequests");
                childId = "requestCard" + index1;
                console.log(childId);
                var child = document.getElementById(childId);
                console.log(child);
                parent.removeChild(child);
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = "The request is " + obj.message + "ly removed from your pending list";

                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
            } else {
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = "Something went wrong. Please try again later";

                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
            }
        }


    }
}