section_arr = ["showFriends","showPendingRequests","showBlockedContacts","showContacts","showProfile","showAddContacts","showSearchContent"];
const changeDisplay = {

    changeDisplayType(section_name) {
        
        console.log("show" + section_name)
        for (i = 0; i < section_arr.length; i++) {
            if (section_arr[i] == "show" + section_name) {

                document.getElementById(section_arr[i]).classList.add("changeDisplayTypeBlock");
                document.getElementById(section_arr[i]).classList.remove("changeDisplayTypeNone");
            } else {
                document.getElementById(section_arr[i]).classList.remove("changeDisplayTypeBlock");
                document.getElementById(section_arr[i]).classList.add("changeDisplayTypeNone");
            }
        }

    }
}