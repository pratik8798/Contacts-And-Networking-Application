console.log("...............in view")
var indexC;
var contactParent = document.querySelector("#showContacts");
contactParent.addEventListener("click", performContactFunction, false);

function performContactFunction(e) {
    if (e.target !== e.currentTarget) {


        strc = e.target.id;

        console.log(strc);
        if (strc.includes("viewContact")) {

            indexC = strc.charAt(strc.length - 1)
            var userNameC = localStorage.getItem("userName")
            
            id = "email" + indexC;
            email2 = document.getElementById(id).innerHTML;

            console.log(email2)
            http = new XMLHttpRequest();
            getOneContactDetails(userNameC, "email", email2);

        }

        else if (strc.includes("deleteContacts")) {
            http = new XMLHttpRequest();
            var userName = localStorage.getItem("userName")
            //var userName = "suhanirathi"
            table = document.getElementById("tableOfContact")
            checkBoxes = table.getElementsByTagName("input");
            message = ""
            console.log("hereee")

            for (i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].checked) {
                    message = checkBoxes[i].id
                    console.log(message)
                    indexC = message.charAt(message.length - 1)
                    email = message.slice(0, message.length - 1)
                    console.log(message)
                    console.log(indexC)
                    console.log(email)
                    console.log(checkBoxes[i].id);
                    deleteContact(userName, email)

                }
            }

        }
        else if (strc.includes("editContact")) {

            var userNameC = localStorage.getItem("userName")
            indexC = strc.charAt(strc.length - 1)
            //var userNameC = "suhanirathi"
            id = "email" + indexC;
            email2 = document.getElementById(id).innerHTML;
            message = ""
            console.log("hereee")
            http = new XMLHttpRequest();
            getOneContactToUpdate(userNameC, "email", email2);


        }
    } else {
        e.stopPropagation();
    }

}

function getOneContactDetails(userName, field, value) {
    console.log(userName + " " + field + " " + value)
    http.onreadystatechange = displayOneContactData
    http.open("POST", "viewContact", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName + "&field=" + field + "&value=" + value);

}
function displayOneContactData() {
    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;


            obj = JSON.parse(res)

            $("#displayFriend").modal('show');
            document.getElementsByTagName("body")[0].removeAttribute("style");
            document.getElementsByTagName("body")[0].removeAttribute("class");

            document.getElementById("friendName").innerHTML = obj.contactList[0].fullName
            document.getElementById("friendEmail").innerHTML = "Email: " + obj.contactList[0].email
            document.getElementById("friendPhone").innerHTML = "Contact No.: " + obj.contactList[0].phoneNumber;
            document.getElementById("friendDOB").innerHTML = "DOB: " + obj.contactList[0].dateOfBirth;
            document.getElementById("friendLocation").innerHTML = "Location: " + obj.contactList[0].location;

            document.getElementById("friendCompany").innerHTML = obj.contactList[0].company;
            document.getElementById("extraButton").style.display = "none";



            if (obj.contactList[0].profileImage != null) {
                profileImage = obj.contactList[0].profileImage
                document.getElementById("imageFriend").src = "data:image/jpg;base64," + profileImage;
            } else {
                document.getElementById("imageFriend").src = "images/avatar.png";
            }
        }

    }

}

function deleteContact(userName, email) {

    console.log(userName + " " + email)
    http.onreadystatechange = displayDeleteContact;

    http.open("POST", "deleteContact", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName + "&email=" + email);


}
function displayDeleteContact() {
    if (http.readyState == 4) {
        if (http.status == 200) {
            res = http.responseText;
            obj = JSON.parse(res);
            if (obj.message == "Successful") {
                var Tparent = document.querySelector("#contactTable");
                childId = "contactrow" + indexC;
                console.log(childId);
                var child = document.getElementById(childId);
                console.log(child);
                Tparent.removeChild(child);
                document.getElementById("Contacts").click()
       //         $("#loginFailureModal").modal('show');
         //       document.querySelector(".failureMessage").innerHTML = "The contact is " + obj.message + "ly removed from your contactlist";

                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
            } else {
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = "Something went wrong. Please try again later";

                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
            }

            // document.getElementById("display").innerHTML = obj.message;

        }
    }
}

function getOneContactToUpdate(userName, field, value) {

    console.log(userName + " " + field + " " + value)
    http.onreadystatechange = upateContactData
    http.open("POST", "viewContact", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName + "&field=" + field + "&value=" + value);

}

function upateContactData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
            obj = JSON.parse(res)
            console.log(obj);
            if ("message" in obj) {
                console.log(obj.message);
                $("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = obj.message;

            } else {
                console.log(obj["contactList"])
                changeDisplay.changeDisplayType("AddContacts");
                document.getElementById("saveContactDetails").style.display = "none";
                document.getElementById("saveContactUpdatedDetails").style.display = "inline-block";
                document.getElementById("genderC").value = obj["contactList"][0].gender;
                document.getElementById("dateOfBirthC").value = obj["contactList"][0].dateOfBirth;
                document.getElementById("addressC").value = obj["contactList"][0].address;
                console.log(obj["contactList"][0].location)
                arrayStr = obj["contactList"][0].location.split(",")
                console.log(arrayStr[0] + " " + arrayStr[1] + " " + arrayStr[2])
                document.getElementById("cityC").value = arrayStr[0];
                document.getElementById("stateC").value = arrayStr[1].slice(1)
                document.getElementById("countryC").value = arrayStr[2].slice(1);
                document.getElementById("companyC").value = obj["contactList"][0].company;
                document.getElementById("fullNameC").value = obj["contactList"][0].fullName;

                document.getElementById("emailC").value = obj["contactList"][0].email;
                console.log(document.getElementById("emailC").value);
                document.getElementById("phoneNumberC").value = obj["contactList"][0].phoneNumber;
                document.getElementById("emailC").disabled = true;
                document.querySelector(".phoneNumberCError").innerHTML = ""

            }


        }
    }
}



document.getElementById("saveContactUpdatedDetails").addEventListener("click", () => {
     userNameContact1 = localStorage.getItem("userName")
    //userNameContact1 = "suhanirathi"
    var fullName = document.getElementById("fullNameC").value
    var email = document.getElementById("emailC").value
    var phoneNumber = document.getElementById("phoneNumberC").value
    var gender = document.getElementById("genderC").value
    var dateOfBirth = document.getElementById("dateOfBirthC").value
    var address = document.getElementById("addressC").value
    var city = document.getElementById("cityC").value
    var state = document.getElementById("stateC").value
    var country = document.getElementById("countryC").value
    var company = document.getElementById("companyC").value
    profileImage = document.querySelector("#profileImageC").files[0]

    if (gender == "" || dateOfBirth == "" || address == "" || city == "" || state == "" || country == "" || company == "" || fullName == "" || email == "" || phoneNumber == "") {
        document.querySelector(".formDetailsCError").innerHTML = "Kindly fill all the details."

    }
    else {
        http = new XMLHttpRequest();
        console.log("in function1");
        updateContactDetails(fullName, email, phoneNumber, gender, dateOfBirth, address, city, state, country, company, userNameContact1, profileImage)
        document.querySelector(".formDetailsError").innerHTML = ""
    }


    console.log(document.getElementById("emailC").value);


})
function updateContactDetails(fullName, email, phoneNumber, gender, dateOfBirth, address, city, state, country, company, userNameContact, profileImage) {


    const formatted_data = new FormData();
    formatted_data.append("fullName", fullName);
    formatted_data.append("email", email);
    console.log(phoneNumber)
    formatted_data.append("phoneNumber", phoneNumber)
    formatted_data.append("gender", gender)
    formatted_data.append("dateOfBirth", dateOfBirth)
    formatted_data.append("address", address)
    formatted_data.append("city", city)
    formatted_data.append("state", state)
    formatted_data.append("country", country)
    formatted_data.append("company", company)
    formatted_data.append("userName", userNameContact)

    formatted_data.append("profileImage", profileImage)
    console.log(formatted_data)
    http.onreadystatechange = resultOfUpdateContact
    http.open("POST", "updateContact", true);
    http.send(formatted_data);

}

function resultOfUpdateContact() {


    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
            obj = JSON.parse(res)
            console.log(obj)
            if (obj.message == "Successful") {

                document.getElementById("Contacts").click();
            }
            else {
                console.log("here")

                $("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = obj.message;
                document.getElementById("Contacts").click();
            }
        }
        // document.getElementById("display").innerHTML = text;
    }


}
