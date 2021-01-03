function ValidateNum(field) {
	var pattern = /^-?[0-9]+(.[0-9]{1,2})?$/;
	var text = field.value;
	var name = field.name;
	if (text.match(pattern) == null) {
		window.alert(name + ' : format is wrong');
		field.focus();
	}
}

function validateCurrency() {
	var pattern = /^-?[0-9]+(.[0-9]{1,2})?$/;
	var text = document.getElementById('valor').value;
	if (text.match(pattern) == null) {
		alert('the format is wrong');
	}
}