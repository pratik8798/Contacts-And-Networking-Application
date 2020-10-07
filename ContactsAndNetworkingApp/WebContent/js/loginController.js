userName = document.querySelector("#userNameLogin");
password = document.querySelector("#passwordLogin");

userName.addEventListener("blur", () => {
    console.log("here")
    if (!Validate.userNameCheck(userName)) {
        document.querySelector(".userNameError").innerHTML = "Username can't be blank."
    }
})

password.addEventListener("blur", () => {
    console.log("here")
    if (!Validate.passwordCheck(password)) {
        document.querySelector(".passwordError").innerHTML = "Password can't be blank."
    }
})

document.getElementById("submitLoginDetails").addEventListener("click", () => {
    submitLoginForm();
})

function submitLoginForm() {

    if (!Validate.passwordCheck(password) && !Validate.userNameCheck(userName)) {

        document.querySelector(".passwordError").innerHTML = "Username or Password can't be blank."
    }
    console.log(userName.value + " " + password.value)
    http = new XMLHttpRequest();
    login(userName.value, password.value);


}

function login(userName, password) {

	console.log(userName);
	console.log(password)
    http.onreadystatechange = displayData
    http.open("POST", "login", true);
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName + "&password=" + password);



}

function displayData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
			
            console.log(res)


        }
        // document.getElementById("display").innerHTML = text;
    }

}