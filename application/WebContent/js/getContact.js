
document.getElementById("Contacts").addEventListener("click", () => {

    changeDisplay.changeDisplayType("Contacts")
    var userName = localStorage.getItem("userName")
    //var userName = "suhanirathi"
    http = new XMLHttpRequest();

    getAllContacts(userName);
})

function getAllContacts(userName) {

    http.onreadystatechange = displayContactData
    http.open("POST", "viewAllContact", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName);
}
function displayContactData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;

            obj = JSON.parse(res)
            console.log(obj)
            if ("message" in obj) {

                $("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = obj.message;

            } else {

                

                
                text =  "<table id='tableOfContact'><tbody id=contactTable>" +
                    "<tr><th>Name</th><th>Email</th><th>Contact Number</th><th>Location</th><th>View/Edit</th><th>Delete</th></tr>";

                for (i = 0; i < obj["contactList"].length; i++) {
                    u = obj.contactList[i];
                    text += "<tr id=contactrow" + i + ">";


                    if (u.profileImage != null) {
                        profileImage = u.profileImage
                        text += "<td><img alt = 'Image placeholder' src = data:image/jpg;base64," + profileImage + " id = 'profileImage' class='avatar rounded-circle avatar-lg'><span id=name" + i + ">" + u.fullName + "</span></td>"
                    } else {
                        text += "<td><img alt = 'Image placeholder' src = 'images/avatar.png'" + "id = 'profileImage' class='avatar rounded-circle avatar-lg'><span id=name" + i + ">" + u.fullName + "</span></td>"
                    }


                    text += "<td id=email" + i + ">" + u.email + "</td>";
                    text += "<td id=phone" + i + ">" + u.phoneNumber + "</td>";
                    if (u.location == "undefined, undefined, undefined") {
                        clocation = "-";
                    }
                    else {
                        clocation = u.location
                    }
                    text += "<td id=location" + i + ">" + clocation + "</td>";
                    text += "<td id=functions" + i + "><i class='fas fa-eye' id= viewContact" + i + "></i><i class='fas fa-user-edit' id= editContact" + i + "></i>"


                    text += "<td><input type='checkbox' id='" + u.email + i + "' name='" + u.email + "' value='" + u.email + "' ></td>";


                    text += "</tr>"

                }
                text += "</tbody>";
                text += "</table>";
                text += "<input type='button'id='deleteContacts' value='Delete Contact'>";
                text += "</form>";



            }
            document.getElementById("displayContacts").innerHTML = text;
        }


    }

}








