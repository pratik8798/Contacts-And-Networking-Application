window.addEventListener("load", () => {

    console.log(localStorage.getItem("name"))
    document.getElementById("adminName").innerHTML = localStorage.getItem("name")

    document.getElementById("adminemail").innerHTML = localStorage.getItem("email");
    document.getElementById("adminphoneNumber").innerHTML = localStorage.getItem("phoneNumber")
    document.getElementById("adminsideName").innerHTML = localStorage.getItem("name")
    document.getElementById("adminwelcomeMessage").innerHTML = "Hi, " + localStorage.getItem("name")



});


