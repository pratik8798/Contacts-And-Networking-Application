console.log("...............in view")
var index;
var theParent = document.querySelector("#showFriends");
theParent.addEventListener("click", performFunction, false);

function performFunction(e) {
    if (e.target !== e.currentTarget) {


        str = e.target.id;

        console.log(str);
        if (str.includes("viewFriend")) {

            index = str.charAt(str.length - 1)
            userName1 = localStorage.getItem("userName")
            //userName1 = "suhanirathi"
            id = "usernameFriend" + index;
            tempUserName2 = document.getElementById(id).innerHTML;
            userName2 = tempUserName2.slice(1);
            console.log(userName2)
            http = new XMLHttpRequest();
            getOneFriendDetails(userName1, userName2);

        }
        else if (str.includes("blockFriend")) {
            console.log(str)
            index = str.charAt(str.length - 1)
            userName1 = localStorage.getItem("userName")
            //userName1 = "suhanirathi"
            id = "usernameFriend" + index;
            tempUserName2 = document.getElementById(id).innerHTML;
            userName2 = tempUserName2.slice(1);
            console.log(userName2)
            http = new XMLHttpRequest();
            blockedFriendDetails(userName1, userName2);
        }
        else if (str.includes("removeFriend")) {
            console.log(str)
            index = str.charAt(str.length - 1)
            userName1 = localStorage.getItem("userName")
            //userName1 = "suhanirathi"
            id = "usernameFriend" + index;
            tempUserName2 = document.getElementById(id).innerHTML;
            userName2 = tempUserName2.slice(1);
            console.log(userName2)
            http = new XMLHttpRequest();
            removeFriendDetails(userName1, userName2);
        }
    } else {
        e.stopPropagation();
    }

}

function getOneFriendDetails(userName1, userName2) {

    console.log(userName1);
    console.log(userName2)

    http.onreadystatechange = displayOneFriendData
    http.open("POST", "viewOneFriend", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName1=" + userName1 + "&userName2=" + userName2);

}
function displayOneFriendData() {
    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;


            obj = JSON.parse(res)
            $("#displayFriend").modal('show');
            document.getElementsByTagName("body")[0].removeAttribute("style");
            document.getElementsByTagName("body")[0].removeAttribute("class");
            document.getElementById("oneFriendUsername").innerHTML = "@" + obj.userName;
            document.getElementById("friendName").innerHTML = obj.fullName;
            document.getElementById("friendEmail").innerHTML = "Email: " + obj.email;
            document.getElementById("friendPhone").innerHTML = "Contact No.: " + obj.phoneNumber;
            document.getElementById("friendDOB").innerHTML = "DOB: " + obj.dateOfBirth;
            document.getElementById("friendLocation").innerHTML = "Location: " + obj.city + ", " + obj.state + ", " + obj.country;
            
            document.getElementById("friendCompany").innerHTML = obj.company;



            if (obj.profileImage != null) {
                profileImage = obj.profileImage
                document.getElementById("imageFriend").src = "data:image/jpg;base64," + profileImage;
            } else {
                document.getElementById("imageFriend").src = "images/avatar.png";
            }
        }

    }

}

// for blocking a friend

function blockedFriendDetails(userName1, userName2) {

    console.log(userName1);
    console.log(userName2)
    console.log(index)
    http.onreadystatechange = blockOneFriendData
    http.open("POST", "blockFriend", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName1=" + userName1 + "&userName2=" + userName2);

}
function blockOneFriendData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;


            obj = JSON.parse(res)
            if (obj.message == "Successful") {
                var parent = document.querySelector("#displayFriends");
                childId = "friendCard" + index;
                console.log(childId);
                var child = document.getElementById(childId);
                console.log(child);
                parent.removeChild(child);
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = obj.message + "ly Blocked the user";

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

//removing a friend
function removeFriendDetails(userName1, userName2) {

    console.log(userName1);
    console.log(userName2)
    console.log(index)
    http.onreadystatechange = removeFriendData
    http.open("POST", "removeFriend", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName1=" + userName1 + "&userName2=" + userName2);

}
function removeFriendData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;


            obj = JSON.parse(res)
            if (obj.message == "Successful") {
                var parent = document.querySelector("#displayFriends");
                childId = "friendCard" + index;
                console.log(childId);
                var child = document.getElementById(childId);
                console.log(child);
                parent.removeChild(child);
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = "The user is " + obj.message + "ly removed from your friendlist";

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