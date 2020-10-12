userNameL = document.querySelector("#userNameLogin");
passwordL = document.querySelector("#passwordLogin");

userNameL.addEventListener("blur", () => {

    if (!Validate.userNameCheck(userNameL)) {
        document.querySelector(".userNameErrorLogin").innerHTML = "Username can't be blank."
    }
    else {
        document.querySelector(".userNameErrorLogin").innerHTML = ""
    }
})

passwordL.addEventListener("blur", () => {

    if (!Validate.passwordCheck(passwordL)) {

        document.querySelector(".passwordErrorLogin").innerHTML = "Password can't be blank."
    } else {
        document.querySelector(".passwordErrorLogin").innerHTML = ""
    }
})

document.getElementById("submitLoginDetails").addEventListener("click", () => {

    if (!Validate.passwordCheck(passwordL) || !Validate.userNameCheck(userNameL)) {
        console.log("error encountered")
        document.querySelector(".passwordErrorLogin").innerHTML = "Username or Password can't be blank."
    } else {
        console.log("caught in success block")
        document.querySelector(".passwordErrorLogin").innerHTML = ""
        submitLoginForm();
    }
})

function submitLoginForm() {


    console.log(userNameL.value + " " + passwordL.value)

    http = new XMLHttpRequest();
    loginForAdmin(userNameL.value, passwordL.value);




}


function loginForAdmin(userName, password) {
    console.log("For admin")
    console.log(userName);
    console.log(password)
    http.onreadystatechange = displayLoginDataForAdmin
    http.open("POST", "viewAdmin", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName + "&password=" + password);



}

function displayLoginDataForAdmin() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;

            obj = JSON.parse(res)
			
            if (obj.message == "Successful") {

                var url = window.location.href;
                url = window.location.href + "admin.html"

                window.location.assign(url)
                localStorage.setItem("name", obj.name)
                
                localStorage.setItem("email", obj.email)
                localStorage.setItem("phoneNumber", obj.phoneNumber)
                
            }
            else {
                console.log(obj.message);
                login(userNameL.value, passwordL.value);		//login failed check if user

            }


        }


    }

}



function login(userName, password) {

    console.log(userName);
    console.log(password)
    http.onreadystatechange = displayLoginData
    http.open("POST", "login", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName + "&password=" + password);



}

function displayLoginData() {

    if (http.readyState == 4) {

        if (http.status == 200) {
            res = http.responseText;

            obj = JSON.parse(res);
            if (obj.message == "Successful") {

                var url = window.location.href;
                url = window.location.href + "user.html"

                window.location.assign(url)

                localStorage.setItem("fullName", obj.fullName)
                
                localStorage.setItem("email", obj.email)
                localStorage.setItem("phoneNumber", obj.phoneNumber)
                localStorage.setItem("userName", obj.userName)
                console.log(obj.birthday)
                localStorage.setItem("birthday", obj.birthday)
                if (obj.profileImage == null) {
                    localStorage.setItem("profileImage", "null")
                } else {
                    localStorage.setItem("profileImage", obj.profileImage)
                }
            }
            else {
                console.log(obj.message);
                $("#loginFailureModal").modal('show');
                document.querySelector("#redirectTologin").style.display = "none";
                document.querySelector(".failureMessage").innerHTML = obj.message;



            }


        }


    }

}
;