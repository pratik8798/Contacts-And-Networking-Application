
fullName = document.querySelector("#fullName");
email = document.querySelector("#email");
phoneNumber = document.querySelector("#phoneNumber");
gender = document.querySelector("#gender");
dateOfBirth = document.querySelector("#dateOfBirth");
address = document.querySelector("#address");
pincode = document.querySelector("#pincode");
city = document.querySelector("#city");
state = document.querySelector("#state");
country = document.querySelector("#country");
company = document.querySelector("#company");
userNameR = document.querySelector("#userName");
passwordR = document.querySelector("#password");
confirmPassword = document.querySelector("#confirmPassword");
profileImage = document.querySelector("#profileImage");

fullName.addEventListener("blur", () => {
    console.log("here")
    if (!Validate.fullNameCheck(fullName)) {
        document.querySelector(".fullNameError").innerHTML = "Name can't be blank."
    }
    else {
        document.querySelector(".fullNameError").innerHTML = ""
    }
})
email.addEventListener("blur", () => {
    if (!Validate.emailCheck(email)) {
        // document.querySelector(".fullNameError").innerHTML = "Name can't be blank."
    }
    else {
        document.querySelector(".emailError").innerHTML = ""
    }
})
phoneNumber.addEventListener("blur", () => {
    if (!Validate.phoneCheck(phoneNumber)) {
        // document.querySelector(".fullNameError").innerHTML = "Name can't be blank."
    }
    else {
        document.querySelector(".phoneNumberError").innerHTML = ""
    }
})
gender.addEventListener("blur", () => {
    if (!Validate.genderCheck(gender)) {
        document.querySelector(".genderError").innerHTML = "Gender can't be blank."
    }
    else {
        document.querySelector(".genderError").innerHTML = ""
    }
})

dateOfBirth.addEventListener("blur", () => {
    if (!Validate.dobCheck(dateOfBirth)) {
        document.querySelector(".dobError").innerHTML = "Date of Birth can't be blank."
    }
    else {
        var dob = dateOfBirth.value;
  
        flag = getAge(dob)
       
        if (flag>=18) {
            document.querySelector(".dobError").innerHTML = ""
        } else {
            document.querySelector(".dobError").innerHTML = "You are not eligible as age must be greater than 18"
        }

    }
})
const getAge = birthDate => Math.floor((new Date() - new Date(birthDate).getTime()) / 3.15576e+10)

address.addEventListener("blur", () => {
    if (!Validate.addressCheck(address)) {
        document.querySelector(".addressError").innerHTML = "Address can't be blank."
    }
    else {
        document.querySelector(".addressError").innerHTML = ""
    }
})
pincode.addEventListener("blur", () => {
    if (!Validate.pincodeCheck(pincode)) {
        document.querySelector(".pincodeError").innerHTML = "Pincode can't be blank"
    } else {
        document.querySelector(".pincodeError").innerHTML = ""
    }
})

city.addEventListener("blur", () => {
    if (!Validate.cityCheck(city)) {
        document.querySelector(".cityError").innerHTML = "City can't be blank"
    } else {
        document.querySelector(".cityError").innerHTML = ""
    }
})

state.addEventListener("blur", () => {
    if (!Validate.stateCheck(state)) {
        document.querySelector(".stateError").innerHTML = "State can't be blank"
    } else {
        document.querySelector(".stateError").innerHTML = ""
    }
})

country.addEventListener("blur", () => {
    if (!Validate.countryCheck(country)) {
        document.querySelector(".countryError").innerHTML = "Country can't be blank"
    } else {
        document.querySelector(".countryError").innerHTML = ""
    }
})

company.addEventListener("blur", () => {
    if (!Validate.companyCheck(company)) {
        document.querySelector(".companyError").innerHTML = "Company can't be blank"
    } else {
        document.querySelector(".companyError").innerHTML = ""
    }
})
userNameR.addEventListener("blur", () => {

    if (!Validate.userNameCheck(userNameR)) {
        document.querySelector(".userNameErrorR").innerHTML = "Username can't be blank."
    }
    else {
        document.querySelector(".userNameErrorR").innerHTML = ""
    }
})

passwordR.addEventListener("blur", () => {
    console.log("here")
    if (!Validate.passwordCheck(passwordR)) {
        document.querySelector(".passwordErrorR").innerHTML = "Password can't be blank."
    }
    else {
        document.querySelector(".passwordErrorR").innerHTML = ""
    }
})
confirmPassword.addEventListener("blur", () => {
    console.log("here")
    if (!Validate.passwordCheck(confirmPassword)) {
        document.querySelector(".confirmPasswordError").innerHTML = "Confirm Password can't be blank."
    }
    else {
        document.querySelector(".confirmPasswordError").innerHTML = ""
    }
})


document.getElementById("registerDetails").addEventListener("click", () => {

    if (!Validate.fullNameCheck(fullName) || !Validate.emailCheck(email) || !Validate.phoneCheck(phoneNumber) || !Validate.genderCheck(gender) || !Validate.dobCheck(dateOfBirth) || !Validate.addressCheck(address) || !Validate.pincodeCheck(pincode) || !Validate.cityCheck(city) || !Validate.stateCheck(state) || !Validate.countryCheck(country) || !Validate.companyCheck(company) || !Validate.passwordCheck(password) || !Validate.userNameCheck(userName) || !Validate.passwordCheck(confirmPassword)) {

        document.querySelector(".formDetailsError").innerHTML = "Kindly fill all the details."
    } else {
        if (passwordR.value === confirmPassword.value) {
            document.querySelector(".confirmPasswordError").innerHTML = ""
            submitRegisterForm();
        } else {
            document.querySelector(".confirmPasswordError").innerHTML = "Password does not matches"
        }
    }

})

function submitRegisterForm() {



    // console.log(fullName.value + " " + email.value + " " + phoneNumber.value + " " + gender.value + " " + dateOfBirth.value + " " + address.value + " " + pincode.value + " " + city.value + " " + state.value + " " + country.value + " " + company.value + " " + userNameR.value + " " + passwordR.value + " " + confirmPassword.value + " " + profileImage.value)



    http = new XMLHttpRequest();
    register(fullName.value, email.value, phoneNumber.value, gender.value, dateOfBirth.value, address.value, pincode.value, city.value, state.value, country.value, company.value, userNameR.value, passwordR.value, profileImage.files[0])

}

function register(fullName, email, phoneNumber, gender, dateOfBirth, address, pincode, city, state, country, company, userName, password, profileImage) {

    console.log(userName);
    console.log(password)
    console.log(profileImage);
    address = address + ", Pincode:" + pincode;
    console.log(address);
    const formatted_data = new FormData();
    formatted_data.append("fullName", fullName);
    formatted_data.append("email", email);
    formatted_data.append("phoneNumber", phoneNumber)
    formatted_data.append("gender", gender)
    formatted_data.append("dateOfBirth", dateOfBirth)
    formatted_data.append("address", address)
    formatted_data.append("city", city)
    formatted_data.append("state", state)
    formatted_data.append("country", country)
    formatted_data.append("company", company)
    formatted_data.append("userName", userName)
    formatted_data.append("password", password)
    formatted_data.append("profileImage", profileImage)

    http.onreadystatechange = displayData
    http.open("POST", "register", true);
    // http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send(formatted_data);


}
function displayData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;
            obj = JSON.parse(res)



            if (obj.message == "Successful") {
                console.log("successful")
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = "Congratulations! You are successfuly registered. Kindly login to continue";
                document.querySelector("#redirectTologin").style.display = "inline-block";


            }
            else {
                console.log(obj.message);
                $("#loginFailureModal").modal('show');
                document.querySelector(".failureMessage").innerHTML = obj.message;
                document.querySelector("#redirectTologin").style.display = "none";


            }
        }
    }
}

document.querySelector("#redirectTologin").addEventListener("click", () => {

    $('#loginForm, .formwrap').addClass('active');
    if (!$('#registrationForm').hasClass('active')) {
        $("body").toggleClass("left", 100);


    } else {
        $('.cta-register').removeClass("colorChange");
        $('#registrationForm, .formwrap').removeClass('active');

    }
    $('.icon-close').addClass('open');
    // $("body").toggleClass("left", 100);
    $('.cta-login').addClass("colorChange");

});