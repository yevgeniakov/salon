/**
 * 
 */
const form = document.getElementById("feedback_form");



// This defines what happens when the user tries to submit the data
form.addEventListener("submit", (event) => {
	let val = 0;
	for (const radioButton of rating) {
		if (radioButton.checked) {
			val = radioButton.value;
			break;
		}
	}
	if (val == 0) {
		event.preventDefault();
		window.alert("Please, set the rating from 1 to 5");
	}

});