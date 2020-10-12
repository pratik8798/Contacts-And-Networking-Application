document.getElementById("adminDeletedUsers").addEventListener("click", () => {

	http = new XMLHttpRequest();

	getListOfPossibleDeletedUsers();
})

function getListOfPossibleDeletedUsers() {


	http.onreadystatechange = displayAllDeletedUsers;

	http.open("POST", "viewAllDeletedUsers", true);

	http.send();

}
function displayAllDeletedUsers() {
	if (http.readyState == 4) {
		if (http.status == 200) {

			res = http.responseText;
			obj = JSON.parse(res);

			text = "<div id='contentDisplayerForAdmin'><form action='deleteUser' method='post'>";
			text += "<center><h5>List of Deleted Users</h5></center>" +
				"<br>" +
				"<table border=2 align='center' id='tableOfDeletedUsers'>" +
				"<tr><th></th><th>User Id</th><th>User Name</th><th>Location</th><th>Active hours</th></tr>";

			for (id = 0; id < obj.deletedUsersList.length; id++) {
				u = obj.deletedUsersList[id];
				text += "<tr>";
				text += "<td><input type='checkbox' id='" + u.userId + "' name='" + u.userId + "' value='" + u.userId + "'></td>";

				text += "<td>" + u.userId + "</td>";
				text += "<td>" + u.userName + "</td>";
				text += "<td>" + u.location + "</td>";
				text += "<td>" + u.activeHours.toFixed(2) + "</td>";


				text += "</tr>"

			}
			text += "</table>";
			text += "<center><input type='button' onclick='getDeletedUsersList()' value='Delete'><center>";
			text += "</form></div>";

			document.getElementById("displayUsers").innerHTML = text;

		}



	}


}
