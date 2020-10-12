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
            console.log("came here")
            return false;
        } else {
            console.log("came here....")
            return true;
        }
    },
    fullNameCheck(fullName) {
        if (fullName.value == "") {
            return false;
        } else {
            return true;
        }
    },
    emailCheck(email) {
        if (email.value == "") {
            document.querySelector(".emailError").innerHTML = "Email can't be blank"
            return false;
        }
        else {
            var mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
            if (!email.value.match(mailformat)) {
                document.querySelector(".emailError").innerHTML = "Invalid Email address"
                return false;
            }
            else {
                return true;
            }

        }

    },
    phoneCheck(phoneNumber) {
        if (phoneNumber.value == "") {
            document.querySelector(".phoneNumberError").innerHTML = "Phone Number can't be blank"
            return false;

        } else {
            var phonenoformat = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
            if (!phoneNumber.value.match(phonenoformat)) {
                document.querySelector(".phoneNumberError").innerHTML = "Invalid Phone Number"
                return false;
            }
            else {
                return true;
            }
        }
    },
    genderCheck(gender) {
        if (gender.value == "") {
            return false;
        }
        else {
            return true
        }
    },
    dobCheck(dateOfBirth) {
        if (dateOfBirth.value == "") {
            return false;
        }
        else {
            return true;
        }
    },
    addressCheck(address) {
        if (address.value == "") {
            return false;
        }
        else {
            return true
        }
    },
    pincodeCheck(pincode) {
        if (pincode.value == "") {
            return false;
        }
        else {
            return true;
        }
    },
    cityCheck(city) {
        if (city.value == "") {
            return false;

        }
        else {
            return true;
        }
    },
    stateCheck(state) {
        if (state.value == "") {
            return false;
        }
        else {
            return true;
        }
    },
    countryCheck(country) {
        if (country.value == "") {
            return false;
        }
        else {
            return true;
        }
    },
    companyCheck(company) {
        if (company.value == "") {
            return false;
        }
        else {
            return true;
        }
    }



}