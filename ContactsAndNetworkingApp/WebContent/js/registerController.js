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
userName = document.querySelector("#userName");
password = document.querySelector("#password");
confirmPassword = document.querySelector("#confirmPassword");
profileImage = document.querySelector("#profileImage");
// fullName.addEventListener("blur", () => {
//     console.log("here")
//     if (!Validate.fullNameCheck(fullName)) {
//         document.querySelector(".fullNameError").innerHTML = "Name can't be blank."
//     }
// })


// password.addEventListener("blur", () => {
//     console.log("here")
//     if (!Validate.passwordCheck(password)) {
//         document.querySelector(".passwordError").innerHTML = "Password can't be blank."
//     }
// })

document.getElementById("registerLoginDetails").addEventListener("click", () => {
    submitRegisterForm();
})

function submitRegisterForm() {


    // if (!Validate.passwordCheck(password) && !Validate.userNameCheck(userName)) {

    //     document.querySelector(".passwordError").innerHTML = "Username or Password can't be blank."
    // }
    console.log(fullName.value + " " + email.value + " " + phoneNumber.value + " " + gender.value + " " + dateOfBirth.value + " " + address.value + " " + pincode.value + " " + city.value + " " + state.value + " " + country.value + " " + company.value + " " + userName.value + " " + password.value + " " + confirmPassword.value + " " + profileImage.value)

    http = new XMLHttpRequest();
    register(fullName.value, email.value, phoneNumber.value, gender.value, dateOfBirth.value, address.value, pincode.value, city.value, state.value, country.value, company.value, userName.value, password.value, profileImage.value)

}

function register(fullName, email, phoneNumber, gender, dateOfBirth, address, pincode, city, state, country, company, userName, password, profileImage) {

    console.log(userName);
    console.log(password)
    console.log(profileImage);
    address = address + ", Pincode:" + pincode;
    console.log(address);
    http.onreadystatechange = displayData
    http.open("POST", "register", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("fullName=" + fullName + "&email=" + email + "&phoneNumber=" + phoneNumber + "&gender=" + gender + "&dateOfBirth=" + dateOfBirth + "&address=" + address + "&city=" + city + "&state=" + state + "&country=" + country + "&company=" + company + "&userName=" + userName + "&password=" + password+"&profileImage="+profileImage);
    console.log("fullName=" + fullName + "&email=" + email + "&phoneNumber=" + phoneNumber + "&gender=" + gender + "&dateOfBirth=" + dateOfBirth + "&address=" + address + "&city=" + city + "&state=" + state + "&country=" + country + "&company=" + company + "&userName=" + userName + "&password=" + password+"&profileImage="+profileImage);
    console.log("send the data");

}
function displayData() {

    if (http.readyState == 4) {
        text = "";
        if (http.status == 200) {
            res = http.responseText;

            console.log(res)


        }
        // document.getElementById("display").innerHTML = text;
    }

}