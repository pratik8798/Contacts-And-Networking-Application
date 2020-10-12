
document.getElementById("BlockedContacts").addEventListener("click", () => {
    console.log("hereee")
    changeDisplay.changeDisplayType("BlockedContacts")
    var userName = localStorage.getItem("userName")
    //var userName ="suhanirathi"
    http = new XMLHttpRequest();
    console.log(userName)
    getAllBlockedUsers(userName);
})

function getAllBlockedUsers(userName) {
    console.log(userName)
 	http.onreadystatechange = displayBlockUsers
    http.open("POST", "viewBlockedUser", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName);
}
function displayBlockUsers() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
			obj = JSON.parse(res)
            console.log(obj)
            if("message" in obj){
            	console.log(obj.message);
                $("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = obj.message;
                
            }else{
            
            
    
    for (i = 0; i < obj["friendList"].length; i++) {
        
            text += " <div class='col-lg-4 col-sm-6 card-displayer'id=blockedUserCard"+i+"><div class='card hover-shadow-lg'><div class='card-body text-center'>         <div class='avatar-parent-child' id=requestparent" + i + ">"
            
            if (obj["friendList"][i].profileImage != null) {
                profileImage = obj['friendList'][i].profileImage
                text += "<img alt = 'Image placeholder' src = data:image/jpg;base64," + profileImage + "id = 'profileImage' class='avatar rounded-circle avatar-lg'>"
            } else {
                text += "<img alt = 'Image placeholder' src = 'images/avatar.png'" + "id = 'profileImage' class='avatar rounded-circle avatar-lg'>"
            }
            text += "<span class='avatar-child avatar-badge bg-success'></span></div ><h5 class='h6 mt-4 mb-0 card_head_name' id=nameBlockFriend" + i + ">" +obj['friendList'][i].fullName + "</h5 ><span  class='d-block text-sm text-muted mb-3' id= usernameBlockFriend" + i + ">@" + obj['friendList'][i].userName + "</span><div class='actions d-flex justify-content-between px-4' id='blockButtons'><button type='button' class='btn btn-warning' id=unblockFriend" + i + ">Unblock</button></div></div ></div ></div >"

       

    }
            document.getElementById("displayBlockedUsers").innerHTML = text;
            }
	

        }
        
    }

}










