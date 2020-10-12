var i;
var flag;
document.getElementById("searchValue").addEventListener("keypress", () => {

  if (event.keyCode === 13) {
    // Cancel the default action, if needed
    event.preventDefault();
    // Trigger the button element with a click
    document.getElementById("mySearchBtn").click();
  }
})
document.getElementById("mySearchBtn").addEventListener("click", () => {

  userNameOfFinder =  localStorage.getItem("userName")

  //userNameOfFinder = "suhanirathi"
  var searchValue = document.getElementById("searchValue").value;
  var searchFilter = document.getElementById("searchFilter").value;
  if (searchFilter == "") {
    document.getElementById("searchFilterError").innerHTML = "Kindly select the filter."
  } else {
    http = new XMLHttpRequest();
    searchUser(searchFilter, searchValue, userNameOfFinder)
  }


})

function searchUser(searchFilter, searchValue, userNameOfFinder) {

  http.onreadystatechange = displaySearchedData
  http.open("POST", "viewUser", true);
  http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  http.send("userName=" + userNameOfFinder + "&field=" + searchFilter + "&value=" + searchValue);

}

function displaySearchedData() {
  if (http.readyState == 4) {
    text = "";
    if (http.status == 200) {
      res = http.responseText;

      console.log(res.length)
      if (res.length == 2) {
        console.log("here when 2");
        $("#loginFailureModal").modal('show');
        document.querySelector(".failureMessage").innerHTML = "Invalid Search Parameters";
        document.getElementsByTagName("body")[0].removeAttribute("style");
        document.getElementsByTagName("body")[0].removeAttribute("class");
      }

      else {
        obj = JSON.parse(res)
        changeDisplay.changeDisplayType("SearchContent")
        console.log("to check if available" + userNameOfFinder)
        for (i = 0; i < obj["usersList"].length; i++) {

          if (obj["usersList"][i].message == "Successful") {

            text += " <div class='col-lg-4 col-sm-6 card-displayer'id=searchCard" + i + "><div class='card hover-shadow-lg'><div class='card-body text-center'> <div class='avatar-parent-child' id=searchParent" + i + ">"


            if (obj["usersList"][i].profileImage != null) {
              profileImage = obj['usersList'][i].profileImage
              text += "<img alt = 'Image placeholder' src = data:image/jpg;base64," + profileImage + "id = 'searchProfileImage' class='avatar rounded-circle avatar-lg'>"
            } else {
              text += "<img alt = 'Image placeholder' src = 'images/avatar.png'" + "id = 'searchProfileImage' class='avatar rounded-circle avatar-lg'>"
            }
            text += "<span class='avatar-child avatar-badge bg-success'></span></div ><h5 class='h6 mt-4 mb-0 card_head_name' id=searchName" + i + ">" +
              obj['usersList'][i].fullName + "</h5 ><span  class='d-block text-sm text-muted mb-3' id= searchUserName" + i + ">@" + obj['usersList'][i].userName + "</span>"


            console.log(obj["usersList"][i].userName);
            http1 = new XMLHttpRequest();
            isFriend(userNameOfFinder, obj["usersList"][i].userName)

            // console.log("type: " + typeButton)
            if (flag == 0) {
              text += "<button class='btn btn-info' disabled id= pendingRequest" + i + " >Request Already Sent</button></div ></div ></div > "
            } else if (flag == 2) {
              text += "<input type='text'placeholder='Enter message..' id=requesMessage" + i + "> <button class='btn btn-primary' id= sendFriendRequest" + i + " >Send Friend Request</button></div></div></div>"
            } else if(flag==3){
            	text += "<button class='btn btn-info' disabled id= pendingInList" + i + " >Check Pending Friend Request List</button ></div ></div ></div> "
            }
            else if(flag==4){
              text += "<button class='btn btn-info' disabled id= blockedByMe" + i + " >You have blocked the user</button ></div ></div ></div> "
            }
            else {
              text += "<button class='btn btn-info' disabled id= viewFriendInSearch" + i + " >Friends</button ></div ></div ></div> "
            }






          } else {
            continue;
          }
        }
        document.getElementById("displaySearch").innerHTML = text;
      }
    }
  }



}


function isFriend(userNameOfFinder, userNameofSearched) {


  console.log("inside friend function finder" + userNameOfFinder)
  console.log("inside friend function searched" + userNameofSearched)
  // http = new XMLHttpRequest();
  http1.onreadystatechange = displayStatusOfFriend
  http1.open("POST", "searchUser", false);
  http1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  http1.send("userName1=" + userNameOfFinder + "&userName2=" + userNameofSearched);

}

function displayStatusOfFriend() {


  if (http1.readyState == 4) {


    if (http1.status == 200) {

      res1 = http1.responseText;

      obj1 = JSON.parse(res1);
      console.log(obj1.message)
      if (obj1.message == "Pending Request") {
        flag = 0;
      }
      else if (obj1.message == "Already Friend") {
        flag = 1;
      }
      else if(obj1.message =="Request already in pending list"){
      	flag =3;
      }
      else if(obj1.message=="Blocked User"){
      	flag=4;
      }
      else {
        flag = 2;
      }




    }

  }
}
