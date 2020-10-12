
var indexS;
var requestParent = document.querySelector("#showSearchContent");
requestParent.addEventListener("click", sendRequestFunction, false);

function sendRequestFunction(e) {
    if (e.target !== e.currentTarget) {


        strr = e.target.id;

        console.log(strr);
        if (strr.includes("sendFriendRequest")) {

            indexS = strr.charAt(strr.length - 1)
            userNameS = localStorage.getItem("userName")
            //userNameS = "suhanirathi"
            userNameFriend = "searchUserName" + indexS;
            tempUserNameFriend = document.getElementById(userNameFriend).innerHTML;
            actualUserNameFriend = tempUserNameFriend.slice(1);
            console.log(actualUserNameFriend)
            messageTosentId = "requesMessage" + indexS;
            messageTosent = document.getElementById(messageTosentId).innerHTML;
            http = new XMLHttpRequest();
            sendTheRequestToUser(userNameS, actualUserNameFriend, messageTosent);

        }
    } else {
        e.stopPropagation();
    }

}

function sendTheRequestToUser(userNameS, actualUserNameFriend, messageTosent) {
    http.onreadystatechange = displayRequestStatus
    http.open("POST", "sendFriendRequest", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName1=" + userNameS + "&userName2=" + actualUserNameFriend + "&message=" + messageTosent);
}

function displayRequestStatus() {
    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;

            console.log(res)
            obj = JSON.parse(res);
            if (obj.message == "Successful") {
              document.getElementById("mySearchBtn").click();
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = "Friend Request Sent Successfully";

                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");

            } else {
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = "You have already send the request to this user.";

                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
            }


        }
        // document.getElementById("display").innerHTML = text;
    }
}

