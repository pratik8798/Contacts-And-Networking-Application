window.addEventListener("load", () => {


    document.getElementById("fullName").innerHTML = localStorage.getItem("fullName")
    document.getElementById("userName").innerHTML = "(" + localStorage.getItem("userName") + ")";
    document.getElementById("email").innerHTML = localStorage.getItem("email");
    document.getElementById("phoneNumber").innerHTML = localStorage.getItem("phoneNumber")
    document.getElementById("sideName").innerHTML = localStorage.getItem("fullName")
    document.getElementById("welcomeMessage").innerHTML = "Hi, " + localStorage.getItem("fullName").split(" ")[0]

    if (localStorage.getItem("birthday") == "Happy birthday") {

        $("#birthdayWish").modal('show');
        document.getElementsByTagName("body")[0].removeAttribute("style");
        document.getElementsByTagName("body")[0].removeAttribute("class");
        document.querySelector(".birthdayMessage").innerHTML = "Dear " + localStorage.getItem("fullName") + ", <br><br> We wish you a very Happy Birthay &#127874;. Have a successful year ahead!"

    }
    if (localStorage.getItem("profileImage") != "null") {
        profileDisplayer.src = "data:image/jpg;base64," + localStorage.getItem("profileImage")
    }
    else {
        profileDisplayer.src = "images/avatar.png"
    }


});



