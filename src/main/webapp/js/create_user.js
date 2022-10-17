/**
 * 
 */
const form = document.getElementById("registration_form");
const email = document.getElementById("email");
const firstname = document.getElementById("name");
const surname = document.getElementById("surname");
const password = document.getElementById("password");
const repeat_password = document.getElementById("repeat_password");
const error_email = document.getElementById("error_email");
const error_firstname = document.getElementById("error_firstname");
const error_surname = document.getElementById("error_surname");
const error_password = document.getElementById("error_password");
const error_repeat_password = document.getElementById("error_repeat_password");
const select_role = document.querySelectorAll('input[name="role"]');
const master_options = document.getElementById("master_options");

// As per the HTML Specification
const emailRegExp =
  /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
const nameRegExp = 
	/^[\D ,.'-]+$/
const passwordRegExp = 
	/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

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
    email.className = "valid";
    error_email.textContent = "";
    error_email.className = "error";
  } else {
    email.className = "invalid";
  }
});
firstname.addEventListener("input", () => {
   isValid = nameRegExp.test(firstname.value);
  if (isValid) {
    firstname.className = "valid";
    error_firstname.textContent = "";
    error_firstname.className = "error";
  } else {
    firstname.className = "invalid";
  }
});
surname.addEventListener("input", () => {
   isValid = nameRegExp.test(surname.value);
  if (isValid) {
    surname.className = "valid";
    error_surname.textContent = "";
    error_surname.className = "error";
  } else {
    surname.className = "invalid";
    error_surname.textContent = "";
  }
});
password.addEventListener("input", () => {
  isValid = passwordRegExp.test(password.value);
  if (isValid) {
    password.className = "valid";
    error_password.textContent = "";
    error_password.className = "error";
  } else {
    password.className = "invalid";
  }
});
repeat_password.addEventListener("input", () => {
   isValid = (password.value == repeat_password.value);
  if (isValid) {
    repeat_password.className = "valid";
    error_repeat_password.textContent = "";
    error_repeat_password.className = "error";
  } else {
    repeat_password.className = "invalid";
  }
});

// This defines what happens when the user tries to submit the data
form.addEventListener("submit", (event) => {
  

   isValid = emailRegExp.test(email.value);
  if (!isValid) {
	event.preventDefault();
    email.className = "invalid";
    error_email.textContent = "It's not a valid e-mail";
    error_email.className = "error active";
  } else {
    email.className = "valid";
    error_email.textContent = "";
    error_email.className = "error";
  }
  
   isValid = nameRegExp.test(firstname.value);
  if (!isValid) {
	event.preventDefault();
    firstname.className = "invalid";
    error_firstname.textContent = "Name must contain letters!";
    error_firstname.className = "error active";
  } else {
	firstname.className = "valid";
    error_firstname.textContent = "";
    error_firstname.className = "error";
  }
  
    isValid = nameRegExp.test(surname.value);
 if (!isValid) {
	event.preventDefault();
    surname.className = "invalid";
    error_surname.textContent = "Surname must contain letters!";
    error_surname.className = "error active";
  } else {
	surname.className = "valid";
    error_surname.textContent = "";
    error_surname.className = "error";
  }
  
    isValid = passwordRegExp.test(password.value);
 if (!isValid) {
	event.preventDefault();
    password.className = "invalid";
    error_password.textContent = "Password must be at least 8 characters, contain capital letters and digits!";
    error_password.className = "error active";
  } else {
	password.className = "valid";
    error_password.textContent = "";
    error_password.className = "error";
  }
  
   isValid = (password.value == repeat_password.value);
 if (!isValid) {
	event.preventDefault();
    repeat_password.className = "invalid";
    error_repeat_password.textContent = "Passwords do not match!";
    error_repeat_password.className = "error active";
  } else {
	repeat_password.className = "valid";
    error_repeat_password.textContent = "";
    error_repeat_password.className = "error";
  }
  
});
form.addEventListener("reset", () => {
	email.className = "valid";
    error_email.textContent = "";
    error_email.className = "error";
    firstname.className = "valid";
    error_firstname.textContent = "";
    error_firstname.className = "error";
    surname.className = "valid";
    error_surname.textContent = "";
    error_surname.className = "error";
	password.className = "valid";
    error_password.textContent = "";
    error_password.className = "error";
    repeat_password.className = "valid";
    error_repeat_password.textContent = "";
    error_repeat_password.className = "error";
    master_options.style.setProperty('display', 'none');
  });
  