document.getElementById("deactivateMe").addEventListener("click", () => {
    var userName = localStorage.getItem("userName")
    //var userName = "suhanirathi"
    http = new XMLHttpRequest();
    console.log(userName)
    deactivateMe(userName);
})

function deactivateMe(userName) {
    http.onreadystatechange = displayDeactivationResult;
    http.open("POST", "deactivateUser", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName);
}

function displayDeactivationResult() {
    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
            console.log(res)
            obj = JSON.parse(res)
            console.log(obj)
            if (obj.message == "Successful") {
                console.log(obj.message);
                $("#deactivationConfirmation").modal('show');
                document.getElementsByTagName("body")[0].removeAttribute("style");
                document.getElementsByTagName("body")[0].removeAttribute("class");
                document.querySelector(".deactivationMessage").innerHTML = "You are disabled Successfully";


            }

        }


    }
}

document.getElementById("confirmDeactivated").addEventListener("click", () => {

     var tempUrl = window.location.href;
            var actUrl = tempUrl.replace("user.html","");
				 window.location.assign(actUrl)  
    localStorage.clear();
})
  

