function ValidateNum(field) {
	var pattern = /^-?[0-9]+(.[0-9]{1,2})?$/;
	var value = field.value;
	var name = field.name;
	if (value.match(pattern) == null) {
		window.alert(" !!!! " + name + ' : format is wrong');
		field.value = '';
		field.focus();
	}
	return false;
}

function tableloop(field) {
	var row = field.closest('tr').rowIndex;
	var col = field.closest('td').cellIndex;
	var value = field.value;
	var name = field.name;
	var table = document.getElementById('myTable');
	var noRows = table.rows.length - 5;

	var quant = document.getElementById("myTable").rows[row].cells[1]
			.querySelector('input').value;
	if (quant == null || isNaN(quant) || quant == "")
		quant = 0;
	var preco = document.getElementById("myTable").rows[row].cells[3]
			.querySelector('input').value;
	if (preco == null || isNaN(preco) || preco == "")
		preco = 0;
	var desc = document.getElementById("myTable").rows[row].cells[4]
			.querySelector('input').value;
	if (desc == null || isNaN(desc) || desc == "")
		desc = 0;
	var iva = document.getElementById("myTable").rows[row].cells[6]
			.querySelector('input').value;
	if (iva == null || isNaN(iva) || iva == "")
		iva = 0;

	var x = document.getElementById("myTable").rows[row].cells;

	var valor = quant * preco;
	var descV = valor * (desc / 100) * -1;
	var valorLiq = valor + descV;
	var ivaV = valorLiq * (iva / 100);
	document.getElementById("myTable").rows[row].cells[5]
			.querySelector('input').value = valorLiq.toFixed(2);
	document.getElementById("myTable").rows[row].cells[7]
			.querySelector('input').value = ivaV.toFixed(2);
	document.getElementById("myTable").rows[row].cells[8]
			.querySelector('input').value = descV.toFixed(2);

	var valorSiva = 0;
	var valordesc = 0;
	var valorIva = 0;
	var total = 0;
	var t0 = 0;
	var t1 = 0;
	var t2 = 0;
	var t3 = 0;
	for (var i = 5; i < table.rows.length; i++) {
		t0 = document.getElementById("myTable").rows[i].cells[1]
				.querySelector('input').value;
		if (isNaN(t0) || t0 == "")
			t0 = 0;
		t1 = document.getElementById("myTable").rows[i].cells[3]
				.querySelector('input').value;
		if (isNaN(t1) || t1 == "")
			t1 = 0;
		t2 = document.getElementById("myTable").rows[i].cells[7]
				.querySelector('input').value;
		if (isNaN(t2) || t2 == "")
			t2 = 0;
		t3 = document.getElementById("myTable").rows[i].cells[8]
				.querySelector('input').value;
		if (isNaN(t3) || t3 == "")
			t3 = 0;
		valorSiva = valorSiva + (parseFloat(t0) * parseFloat(t1));
		valorIva = valorIva + parseFloat(t2);
		valordesc = valordesc + parseFloat(t3);
	}
	total = valorSiva + valorIva + valordesc;
	document.getElementById("valorSiva").value = valorSiva.toFixed(2);
	document.getElementById("desconto").value = valordesc.toFixed(2);
	document.getElementById("valoriva").value = valorIva.toFixed(2);
	document.getElementById("total").value = total.toFixed(2);

	return false;
}

function validateCurrency(field) {
	var pattern = /^-?[0-9]+(.[0-9]{1,2})?$/;
	var text = field.value;
	var name = field.name;
	if (text.match(pattern) == null) {
		window.alert(name + ' : format is wrong');
		field.focus();
		// return false;
	}
	// return false;
}
