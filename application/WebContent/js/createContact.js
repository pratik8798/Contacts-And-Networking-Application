document.getElementById("AddContacts").addEventListener("click", () => {
    console.log("hereee.....")
    changeDisplay.changeDisplayType("AddContacts")
    document.getElementById("saveContactDetails").style.display = "inline-block";
    document.getElementById("saveContactUpdatedDetails").style.display = "none";
    userNameContact = localStorage.getItem("userName")
    //userNameContact = "suhanirathi"
    emailC = document.querySelector("#emailC");
    phoneNumber = document.querySelector("#phoneNumberC");
})
emailC.addEventListener("blur", () => {
    if (!Validate.emailCheck(emailC)) {
        console.log("got here")
        document.querySelector(".emailCError").innerHTML = "Invalid Email"
    }
    else {
        document.querySelector(".emailError").innerHTML = ""
        http = new XMLHttpRequest();
        getContactByEmail(emailC.value)


    }
})
phoneNumberC.addEventListener("blur", () => {
    if (!Validate.phoneCheck(phoneNumberC)) {
        document.querySelector(".phoneNumberCError").innerHTML = "Invalid Phone Number"
    }
    else {
        document.querySelector(".phoneNumberCError").innerHTML = ""
    }
})
function getContactByEmail(email) {
    console.log(email);
    http.onreadystatechange = autoPopulateFields
    http.open("POST", "autoPopulateContactDetails", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("email=" + email);

}
function autoPopulateFields() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
            obj = JSON.parse(res)
            console.log(obj)


            if ("message" in obj) {
                console.log(obj.message);
                return;
            } else {


                $("#autoPopulateContact").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
            }
        }
        // document.getElementById("display").innerHTML = text;
    }

}

document.getElementById("addTheAlreadyPresentContact").addEventListener("click", () => {
    console.log(obj);
    http1 = new XMLHttpRequest();
    createContactDirectly(userNameContact, obj)


})

function createContactDirectly(userNameContact, obj) {

    console.log(obj + "........")

    const formatted_data = new FormData();
    formatted_data.append("fullName", obj.fullName);
    formatted_data.append("email", obj.email);
    console.log(obj.phoneNumber)
    formatted_data.append("phoneNumber", obj.phoneNumber)
    formatted_data.append("gender", obj.gender)
    formatted_data.append("dateOfBirth", obj.dateOfBirth)
    formatted_data.append("address", obj.address)
    formatted_data.append("city", obj.city)
    formatted_data.append("state", obj.state)
    formatted_data.append("country", obj.country)
    formatted_data.append("company", obj.company)
    formatted_data.append("userName", userNameContact)

    formatted_data.append("profileImage", obj.profileImage)
    console.log(formatted_data)
    http1.onreadystatechange = resultOfCreateContact
    http1.open("POST", "createContact", true);

    http1.send(formatted_data);
}
function resultOfCreateContact() {

    if (http1.readyState == 4) {
        text = "";
        if (http1.status == 200) {
            res = http1.responseText;
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

document.getElementById("doNotAdd").addEventListener("click", () => {

    $("#loginFailureModal").modal('show');
    document.getElementsByTagName("body")[0].removeAttribute("style");
    document.getElementsByTagName("body")[0].removeAttribute("class");
    document.querySelector(".failureMessage").innerHTML = "If you wish to continue adding the contact, kindly change the email address!";


})

document.getElementById("saveContactDetails").addEventListener("click", () => {
	 userNameContact = localStorage.getItem("userName")
    //userNameContact = "suhanirathi"
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
        http2 = new XMLHttpRequest();
        console.log("in function1");
        addContactDetails(fullName, email, phoneNumber, gender, dateOfBirth, address, city, state, country, company, userNameContact, profileImage)
        document.querySelector(".formDetailsError").innerHTML = ""
    }


    console.log(document.getElementById("emailC").value);


})
function addContactDetails(fullName, email, phoneNumber, gender, dateOfBirth, address, city, state, country, company, userNameContact, profileImage) {


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
    http2.onreadystatechange = resultOfCreateContact
    http2.open("POST", "createContact", true);
    http2.send(formatted_data);

}


document.getElementById("viewAndEdit").addEventListener("click", () => {

    console.log(" I came Into editing")
    document.getElementById("genderC").value = obj.gender;
    document.getElementById("dateOfBirthC").value = obj.dateOfBirth;
    document.getElementById("addressC").value = obj.address;
    arrayStr = obj.location.split(",")
    console.log(arrayStr[0] + " " + arrayStr[1] + " " + arrayStr[2])
    document.getElementById("cityC").value = arrayStr[0];
    document.getElementById("stateC").value = arrayStr[1].slice(1)
    document.getElementById("countryC").value = arrayStr[2].slice(1);
    document.getElementById("companyC").value = obj.company;
    document.getElementById("fullNameC").value = obj.fullName;

    document.getElementById("emailC").value = obj.email;
    console.log(document.getElementById("emailC").value);
    document.getElementById("phoneNumberC").value = obj.phoneNumber;
    document.getElementById("emailC").disabled = true;
    document.querySelector(".phoneNumberCError").innerHTML = ""



})