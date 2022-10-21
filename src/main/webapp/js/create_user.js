/**
 * 
 */
const form = document.getElementById("registration_form");
const email = document.getElementById("email");
const firstname = document.getElementById("name");
const surname = document.getElementById("surname");
const password = document.getElementById("password");
const repeat_password = document.getElementById("repeat_password");
const tel = document.getElementById("tel");
const error_email = document.getElementById("error_email");
const error_firstname = document.getElementById("error_firstname");
const error_surname = document.getElementById("error_surname");
const error_password = document.getElementById("error_password");
const error_repeat_password = document.getElementById("error_repeat_password");
const error_tel = document.getElementById("error_tel");
// As per the HTML Specification
const emailRegExp =
  /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
const nameRegExp = 
	/^[\D ,.'-]+$/
const passwordRegExp = 
	/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
const telRegExp = 
	/^[0]\d{9}$/;

// Now we can rebuild our validation constraint
// Because we do not rely on CSS pseudo-class, we have to
// explicitly set the valid/invalid class on our email field
function change_master_options() {
	for (const radioButton of select_role) {
		if (radioButton.checked) {
			if (radioButton.value == 'HAIRDRESSER') {
				master_options.style.setProperty('display', 'block');
			} else {
				master_options.style.setProperty('display', 'none');
			}
			break;
		}


	}
	}

// This defines what happens when the user types in the field
email.addEventListener("input", () => {
   isValid = emailRegExp.test(email.value);
  if (isValid) {
    email.classList.remove("invalid");
    error_email.textContent = "";
    error_email.className = "error";
  } else {
	
	email.classList.add("invalid");
  
  }
});
firstname.addEventListener("input", () => {
   isValid = nameRegExp.test(firstname.value);
  if (isValid) {
    firstname.classList.remove("invalid");
    error_firstname.textContent = "";
    error_firstname.className = "error";
  } else {
    firstname.classList.add("invalid");
  }
});
surname.addEventListener("input", () => {
   isValid = nameRegExp.test(surname.value);
  if (isValid) {
    surname.classList.remove("invalid");
    error_surname.textContent = "";
    error_surname.className = "error";
  } else {
    surname.classList.add("invalid");
    error_surname.textContent = "";
  }
});
password.addEventListener("input", () => {
  isValid = passwordRegExp.test(password.value);
  if (isValid) {
    password.classList.remove("invalid");
    error_password.textContent = "";
    error_password.className = "error";
  } else {
    password.classList.add("invalid");
  }
});
repeat_password.addEventListener("input", () => {
   isValid = (password.value == repeat_password.value);
  if (isValid) {
    repeat_password.classList.remove("invalid");
    error_repeat_password.textContent = "";
    error_repeat_password.className = "error";
  } else {
    repeat_password.classList.add("invalid");
  }
});
tel.addEventListener("input", () => {
   isValid = telRegExp.test(tel.value);
  if (isValid) {
    tel.classList.remove("invalid");
    error_tel.textContent = "";
    error_tel.className = "error";
  } else {
    tel.classList.add("invalid");
  }
});

// This defines what happens when the user tries to submit the data
form.addEventListener("submit", (event) => {
  

   isValid = emailRegExp.test(email.value);
  if (!isValid) {
	event.preventDefault();
    email.classList.add("invalid");
    error_email.textContent = "It's not a valid e-mail";
    error_email.className = "error active";
  } else {
    email.classList.remove("invalid");
    error_email.textContent = "";
    error_email.className = "error";
  }
  
   isValid = nameRegExp.test(firstname.value);
  if (!isValid) {
	event.preventDefault();
    firstname.classList.add("invalid");
    error_firstname.textContent = "Name must contain letters!";
    error_firstname.className = "error active";
  } else {
	firstname.classList.remove("invalid");
    error_firstname.textContent = "";
    error_firstname.className = "error";
  }
  
    isValid = nameRegExp.test(surname.value);
 if (!isValid) {
	event.preventDefault();
    surname.classList.add("invalid");
    error_surname.textContent = "Surname must contain letters!";
    error_surname.className = "error active";
  } else {
	surname.classList.remove("invalid");
    error_surname.textContent = "";
    error_surname.className = "error";
  }
  
    isValid = passwordRegExp.test(password.value);
 if (!isValid) {
	event.preventDefault();
    password.classList.add("invalid");
    error_password.textContent = "Password must be at least 8 characters, contain capital letters and digits!";
    error_password.className = "error active";
  } else {
	password.classList.remove("invalid");
    error_password.textContent = "";
    error_password.className = "error";
  }
  
   isValid = (password.value == repeat_password.value);
 if (!isValid) {
	event.preventDefault();
    repeat_password.classList.add("invalid");
    error_repeat_password.textContent = "Passwords do not match!";
    error_repeat_password.className = "error active";
  } else {
	repeat_password.classList.remove("invalid");
    error_repeat_password.textContent = "";
    error_repeat_password.className = "error";
  }
  
  isValid = telRegExp.test(tel.value);
 if (!isValid) {
	event.preventDefault();
    tel.classList.add("invalid");
    error_tel.textContent = "Tel number must be in format^ 0XXXXXXXXX";
    error_tel.className = "error active";
  } else {
	tel.classList.remove("invalid");
    error_tel.textContent = "";
    error_tel.className = "error";
  }
  
});
form.addEventListener("reset", () => {
	email.classList.remove("invalid");
    error_email.textContent = "";
    error_email.className = "error";
    firstname.classList.remove("invalid");
    error_firstname.textContent = "";
    error_firstname.className = "error";
    surname.classList.remove("invalid");
    error_surname.textContent = "";
    error_surname.className = "error";
	password.classList.remove("invalid");
    error_password.textContent = "";
    error_password.className = "error";
    repeat_password.classList.remove("invalid");
    error_repeat_password.textContent = "";
    error_repeat_password.className = "error";
    tel.classList.remove("invalid");
    error_tel.textContent = "";
    error_tel.className = "error";
    
    master_options.style.setProperty('display', 'none');
  });
  
  