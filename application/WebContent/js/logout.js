document.getElementById("logout").addEventListener("click", () => {
    var userName = localStorage.getItem("userName")
    //var userName = "suhanirathi"
    http = new XMLHttpRequest();
    console.log(userName)
    logMeOut(userName);
})

function logMeOut(userName) {
console.log(userName);
    http.onreadystatechange = logoutResult;
    http.open("POST", "logout", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("userName=" + userName);
}

function logoutResult() {
    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
            console.log(res)
            obj = JSON.parse(res)
            console.log(obj)
            if (obj.message == "Successful") {
            var tempUrl = window.location.href;
            var actUrl = tempUrl.replace("user.html","");
				 window.location.assign(actUrl)                
					localStorage.clear();
            }

        }


    }
}

