
document.getElementById("Profile").addEventListener("click", () => {
    console.log("hereee")
    changeDisplay.changeDisplayType("Profile")
    var userName1 = localStorage.getItem("userName")
    var field = "userName";
    //var userName1 = "suhanirathi"
    var value = userName1;
    var userName = ""
    document.getElementById("saveDetails").style.display = "none";
    http = new XMLHttpRequest();
    console.log(userName)
    getProfile(field, value, userName);
})

function getProfile(field, value, userName) {
    console.log(field + " " + value + " " + userName)
    http.onreadystatechange = displayProfile
    http.open("POST", "viewUser", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("field=" + field + "&value=" + value + "&userName=" + userName);
}
function displayProfile() {
    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
            obj = JSON.parse(res)
            console.log(obj)
            console.log(obj.fullName);

            var fields = document.getElementById("profileForm").getElementsByTagName("input");
            for (var i = 0; i < fields.length; i++) {
                fields[i].disabled = true;
            }

            document.getElementById("gender").value = obj.gender;
            document.getElementById("dateOfBirth").value = obj.dateOfBirth;
            document.getElementById("address").value = obj.address;
            document.getElementById("city").value = obj.city;
            document.getElementById("state").value = obj.state;
            document.getElementById("country").value = obj.country;
            document.getElementById("company").value = obj.company;
            document.getElementById("fullNameF").value = obj.fullName;
            document.getElementById("userNameF").value = obj.userName;
            document.getElementById("emailF").value = obj.email;
            console.log(document.getElementById("emailF").value);
            document.getElementById("phoneNumberF").value = obj.phoneNumber;


        }
        // document.getElementById("display").innerHTML = text;
    }
}

document.getElementById("EditDetails").addEventListener("click", () => {

    var fields = document.getElementById("profileForm").getElementsByTagName("input");
    for (var i = 0; i < fields.length; i++) {

        fields[i].disabled = false;
        if (fields[i].id == "userNameF") {
            fields[i].disabled = true;
        }
    }
    document.getElementById("saveDetails").style.display = "inline-block";
})

document.getElementById("saveDetails").addEventListener("click", () => {

    genderR = document.getElementById("gender").value
    dateOfBirthR = document.getElementById("dateOfBirth").value
    addressR = document.getElementById("address").value
    cityR = document.getElementById("city").value
    stateR = document.getElementById("state").value
    countryR = document.getElementById("country").value
    companyR = document.getElementById("company").value
    fullNameR = document.getElementById("fullNameF").value
    userNameR= document.getElementById("userNameF").value
    emailR = document.getElementById("emailF").value
    phoneNumberR = document.getElementById("phoneNumberF").value
    profileImageR = document.querySelector("#profileImageR").files[0]
    console.log(profileImageR)
    console.log(companyR)

    if (genderR == "" || dateOfBirthR == "" || addressR == "" || cityR == "" || stateR == "" || countryR == "" || companyR == "" || fullNameR == "" || userNameR == "" || emailR == "" || phoneNumberR == "") {
        document.querySelector(".formDetailsError").innerHTML = "Kindly fill all the details."

    }
    else {
        http = new XMLHttpRequest();
        console.log("in function1");
        updateUserDetails(fullNameR, emailR, phoneNumberR, genderR, dateOfBirthR, addressR, cityR, stateR, countryR, companyR, userNameR,profileImageR)
        document.querySelector(".formDetailsError").innerHTML = ""
    }


})
function updateUserDetails(fullName, email, phoneNumber, gender, dateOfBirth, address, city, state, country, company, userName,profileImage) {
   	
   	const formatted_data1 = new FormData();
    formatted_data1.append("fullName", fullName);
    formatted_data1.append("email", email);
    console.log(phoneNumber)
    formatted_data1.append("phoneNumber", phoneNumber)
    formatted_data1.append("gender", gender)
    formatted_data1.append("dateOfBirth", dateOfBirth)
    formatted_data1.append("address", address)
    formatted_data1.append("city", city)
    formatted_data1.append("state", state)
    formatted_data1.append("country",country)
    formatted_data1.append("company", company)
    formatted_data1.append("userName", userName)
    formatted_data1.append("profileImage", profileImage)
    console.log(formatted_data1)
   	   http.onreadystatechange = saveData

    http.open("POST", "updateUser", true);
 
    http.send(formatted_data1);

}

function saveData() {

    if (http.readyState == 4) {
        text = "";
        console.log("in function1");
        if (http.status == 200) {
            res = http.responseText;
            obj = JSON.parse(res)
            console.log(obj);

            if (obj.message == "Successful") {
                console.log(obj.message);
                $("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = "Your profile is updated successfully";
                var fields = document.getElementById("profileForm").getElementsByTagName("input");
                for (var i = 0; i < fields.length; i++) {
                    fields[i].disabled = true;
                }
                document.getElementById("saveDetails").style.display = "none";
            }
            else {
                $("#loginFailureModal").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".failureMessage").innerHTML = "Something went wrong. Please try again later";
                var fields = document.getElementById("profileForm").getElementsByTagName("input");
                for (var i = 0; i < fields.length; i++) {
                    fields[i].disabled = true;
                }
                document.getElementById("saveDetails").style.display = "none";
            }
        }
    }
}





