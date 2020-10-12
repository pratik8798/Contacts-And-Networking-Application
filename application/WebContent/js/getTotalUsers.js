document.getElementById("adminAllUsers").addEventListener("click", () => {

	http = new XMLHttpRequest();

	getTotalUsers();
})
function getTotalUsers() {


	http.onreadystatechange = displayTotalUsers;

	http.open("POST", "viewAllUsers", true);

	http.send();

}
function displayTotalUsers() {
	if (http.readyState == 4) {
		if (http.status == 200) {
			res = http.responseText;
			obj = JSON.parse(res);


			text = "<div id='contentDisplayerForAdmin'><center><h5>List of users</h5></center>" +
				"<br>" +
				"<table border=2 align='center'>" +
				"<tr><th>User Id</th><th>User Name</th><th>Location</th><th>Account Active?</th></tr>";

			for (id = 0; id < obj.usersList.length; id++) {
				u = obj.usersList[id];
				text += "<tr>";
				text += "<td>" + u.userId + "</td>";
				text += "<td>" + u.userName + "</td>";
				text += "<td>" + u.location + "</td>";
				if (u.active) {
					text += "<td>" + "Yes" + "</td>";
				}
				else {
					text += "<td>" + "No" + "</td>";
				}

				text += "</tr>"

			}
			text += "</table></div>";

			document.getElementById("displayUsers").innerHTML = text;

		}
	}


}
