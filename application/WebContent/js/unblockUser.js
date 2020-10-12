
var index2;
var blockParent = document.querySelector("#showBlockedContacts");
blockParent.addEventListener("click", performUnblockingFunction, false);

function performUnblockingFunction(e) {
    if (e.target !== e.currentTarget) {


        str1 = e.target.id;

        console.log(str1);
        if (str1.includes("unblockFriend")) {

            index2 = str1.charAt(str1.length - 1)
            userNameB1 = localStorage.getItem("userName")
            //userNameB1 = "suhanirathi"
            id = "usernameBlockFriend" + index2;
            tempUserNameB2 = document.getElementById(id).innerHTML;
            userNameB2 = tempUserNameB2.slice(1);
            console.log(userNameB2)
            http = new XMLHttpRequest();
            unblockUserDetails(userNameB1, userNameB2);

        }
   
     
    } else {
        e.stopPropagation();
    }

}

//ignore a friend
function unblockUserDetails(userNameB1, userNameB2) {

    console.log(userNameB1);
    console.log(userNameB2)
    console.log(index2)
    http.onreadystatechange = unblockUser
    http.open("POST", "unBlockUser", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName1=" + userNameB1 + "&userName2=" + userNameB2);

}
function unblockUser() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;


            obj = JSON.parse(res)
           	console.log(obj);
            if (obj.message == "Successful") {
              var parent = document.querySelector("#displayBlockedUsers");
                childId = "blockedUserCard" + index2;
                console.log(childId);
                var child = document.getElementById(childId);
                console.log(child);
                parent.removeChild(child);
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = "The user is " + obj.message + "ly unblocked";

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