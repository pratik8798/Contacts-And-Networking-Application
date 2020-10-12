
document.getElementById("Friends").addEventListener("click", () => {
    console.log("hereee")
    changeDisplay.changeDisplayType("Friends")
    var userName = localStorage.getItem("userName")
    //var userName = "suhanirathi"
    http = new XMLHttpRequest();
    console.log(userName)
    getAllFriends(userName);
})

function getAllFriends(userName) {
    console.log(userName)
    http.onreadystatechange = displayFriendData
    http.open("POST", "viewAllFriend", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName);
}
function displayFriendData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
            obj = JSON.parse(res)
            console.log(obj)
            if ("message" in obj) {
                console.log(obj.message);
                $("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = obj.message;

            } else {

                for (i = 0; i < obj["friendList"].length; i++) {

                    text += " <div class='col-lg-4 col-sm-6 card-displayer'id=friendCard" + i + "><div class='card hover-shadow-lg'><div class='card-body text-center'>         <div class='avatar-parent-child' id=parent" + i + ">"

                    if (obj["friendList"][i].profileImage != null) {
                        profileImage = obj['friendList'][i].profileImage
                        text += "<img alt = 'Image placeholder' src = data:image/jpg;base64," + profileImage + "id = 'profileImage' class='avatar rounded-circle avatar-lg'>"
                    } else {
                        text += "<img alt = 'Image placeholder' src = 'images/avatar.png'" + "id = 'profileImage' class='avatar rounded-circle avatar-lg'>"
                    }
                    text += "<span class='avatar-child avatar-badge bg-success'></span></div ><h5 class='h6 mt-4 mb-0 card_head_name' id=nameFriend" + i + ">" +
                        obj['friendList'][i].fullName + "</h5 ><span  class='d-block text-sm text-muted mb-3' id= usernameFriend" + i + ">@" + obj['friendList'][i].userName + "</span><div class='actions d-flex justify-content-between px-4'><span class='action-item' ><i class='fas fa-eye' id= viewFriend" + i + "></i></span><span class='action-item'><i class='fas fa-user-slash' id= blockFriend" + i + "></i></span><span class='action-item' ><i class='fas fa-trash-alt' id= removeFriend" + i + "></i></span></div></div ></div ></div > "



                }
                document.getElementById("displayFriends").innerHTML = text;
            }


        }
        // document.getElementById("display").innerHTML = text;
    }

}







