const Validate = {

    userNameCheck(userName) {
        console.log("in validation")
        if (userName.value == "") {
            return false;
        } else {
            return true;
        }
    },
    passwordCheck(password) {
        console.log("in validation")
        if (password.value == "") {
            return false;
        } else {
            return true;
        }
    }

}