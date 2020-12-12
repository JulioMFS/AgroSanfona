<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.util.Calendar"%><%@page import="java.text.*"%>
<%@page import="java.text.*"%>
<html>

<head onload="GFG_Run();">
<title>Factura Management Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th,td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

.gtext {
	color: blue;
	text-shadow: 2px 2px 5px blue;
	font-size: 140%;
}

.gtextred {
	color: red;
}

.gtextgreen {
	color: green;
}
</style>
</head>

<body onload="GFG_Run()">
	<script type="text/javascript">
		function ValidateNum(field) {
			var pattern = /^-?[0-9]+(.[0-9]{1,5})?$/;
			var value = field.value;
			var name = field.name;
			if (value.match(pattern) == null) {
				window.alert(" !!!! " + name + ' : format is wrong');
				field.value = '';
				field.focus();
			}
			return false;
		}

		function onLoadBody() {
			alert("onLoadBody");
			document.getElementById('invNo').readOnly = true;
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
			var desc = document.getElementById("myTable").rows[row].cells[5]
					.querySelector('input').value;
			if (desc == null || isNaN(desc) || desc == "")
				desc = 0;
			var iva = document.getElementById("myTable").rows[row].cells[8]
					.querySelector('input').value;
			if (iva == null || isNaN(iva) || iva == "") {
				iva = 0;
				document.getElementById("myTable").rows[row].cells[8]
						.querySelector('input').value = iva;
			}
			var x = document.getElementById("myTable").rows[row].cells;

			var valor = parseFloat(quant) * parseFloat(preco);
			var descV = valor * (parseFloat(desc) / 100) * -1;
			var valorLiq = valor + descV;
			var ivaV = valorLiq * (parseFloat(iva) / 100);

			document.getElementById("myTable").rows[row].cells[4]
					.querySelector('input').value = valor.toFixed(2);

			document.getElementById("myTable").rows[row].cells[6]
					.querySelector('input').value = descV.toFixed(2);

			document.getElementById("myTable").rows[row].cells[7]
					.querySelector('input').value = valorLiq.toFixed(2);

			document.getElementById("myTable").rows[row].cells[9]
					.querySelector('input').value = ivaV.toFixed(2);

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
				t2 = document.getElementById("myTable").rows[i].cells[9]
						.querySelector('input').value;
				if (isNaN(t2) || t2 == "")
					t2 = 0;
				t3 = document.getElementById("myTable").rows[i].cells[6]
						.querySelector('input').value;
				if (isNaN(t3) || t3 == "")
					t3 = 0;
				valorSiva = valorSiva + (parseFloat(t0) * parseFloat(t1));
				valorIva = valorIva + parseFloat(t2);
				valordesc = valordesc + parseFloat(t3);
			}
			if (valordesc > 0)
				valordesc * -1;
			total = valorSiva + valorIva + valordesc;
			document.getElementById("valorSiva").value = valorSiva.toFixed(2);
			document.getElementById("descontov").value = valordesc.toFixed(2);
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
				//			return false;
			}
			//			return false;
		}
		function addRow() {
			var root = document.getElementById('myTable').getElementsByTagName(
					'tbody')[1];
			var rows = root.getElementsByTagName('tr');
			var clone = cloneEl(rows[rows.length - 1]);
			cleanUpInputs(clone);
			root.appendChild(clone);
		}
		function addColumn() {
			var rows = document.getElementById('mytab').getElementsByTagName(
					'tr'), i = 0, r, c, clone;
			while (r = rows[i++]) {
				c = r.getElementsByTagName('td');
				clone = cloneEl(c[c.length - 1]);
				cleanUpInputs(clone);
				c[0].parentNode.appendChild(clone);
			}
		}
		function cloneEl(el) {
			var clo = el.cloneNode(true);
			return clo;
		}

		function cleanUpInputs(obj) {
			for (var i = 0; n = obj.childNodes[i]; ++i) {
				if (n.childNodes && n.tagName != 'INPUT') {
					cleanUpInputs(n);
				} else if (n.tagName == 'INPUT' && n.type == 'text') {
					n.value = '';
				}
			}
		}

		function checkInv() {
			//			  document.getElementById('Input').readOnly 
			//             = true; 
			alert("in checkInv");
			var text = document.getElementById("disp").innerHTML;
			alert(text);
			var invNo = inv.value;
			var no = document.getElementById("no1").value;
			var action = document.forms[0].getAttribute("action");
			alert("no: " + no + ", inv: " + invNo + ", action: " + action);
			document.forms[0].action = "verify";
			alert("action: " + document.forms[0].action);
			document.forms[0].submit();
			return false;
		}

		function populateCustomerId() {
			var selectBox = document.getElementById('selectBox');

			/* selected value of dropdown */
			var selectedCustomerId = selectBox.options[selectBox.selectedIndex].value;

			/* selected value set to input field */
			document.getElementById('customerId').value = selectedCustomerId;
		}

		function disableFunction() {
			var disp = document.getElementById("disp");
			alert("disp: " + disp);
			if (disp == "Nova Factura") {
				document.getElementById("invNo").disabled = false;
			}
			/*			var inv = document.getElementById("invNo").value;
			 var no = document.getElementById('no1').value;
			 var num = document.getElementById('no1');
			 var form = document.getElementById("form1").value;
			 /*			if (no == null || isNaN(no) || no == 0) {
			 invoice.disabled = false;
			 } else */
			/*			alert("O numero da factura nao pode ser mudado, no: " + no
			 + ", form.action.value: " + form.action);

			 if (form.action.value == "insert") {
			 invoice.disabled = false;
			 } else {
			 invoice.disabled = true;
			 alert("O numero da factura nao pode ser mudado, no: " + no
			 + ", form.action: " + form.action);
			 }*/
			return false;
		}

		function GFG_Run() {

			var div = document.getElementById("div1");
			var inv = document.getElementById("invNo");
			var text = div.textContent;

			if (text == "Editar Factura") {
				inv.setAttribute("readonly", true);
			} else {
				inv.removeAttribute("readonly");
			}

			return true;
			//		document.getElementById("GFG_down").innerHTML = "Read-Only attribute enabled";
		}

		function xaddRow(selValue) {
			alert("In addRow, selected value: " + selValue);

			var empTab = document.getElementById('myTable');

			var rowCnt = empTab.rows.length; // get the number of rows.
			alert("row count: " + rowCnt);
			var tr = empTab.insertRow(rowCnt); // table row.
			tr = empTab.insertRow(rowCnt);

			for (var c = 0; c < 7; c++) {
				var td = document.createElement('td'); // TABLE DEFINITION.
				td = tr.insertCell(c);

				if (c == 0) { // if its the first column of the table.
					// add a button control.
					var button = document.createElement('input');

					// set the attributes.
					button.setAttribute('type', 'button');
					button.setAttribute('value', 'Remove');

					// add button's "onclick" event.
					button.setAttribute('onclick', 'removeRow(this)');

					td.appendChild(button);
				} else {
					// the 2nd, 3rd and 4th column, will have textbox.
					var ele = document.createElement('input');
					ele.setAttribute('type', 'text');
					ele.setAttribute('value', selValue);

					td.appendChild(ele);
				}
			}
		}
	</script>
	<%
		String date2 = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Calendar today = Calendar.getInstance();
		Calendar aCalendar = Calendar.getInstance();
		java.util.Date today = aCalendar.getTime();
		date2 = dateFormat.format(today);
		request.setAttribute("data2", date2);
	%>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand">
					Factura Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Facturas</a></li>
			</ul>
		</nav>
	</header>
	<br>

	<c:choose>
		<c:when test="${existingFactura != null}">
			<c:set var="formAction" value="update" />
		</c:when>
		<c:otherwise>
			<c:set var="formAction" value="insert" />
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${message != null}">
			<c:set var="formAction" value="insert" />
		</c:when>
	</c:choose>
	<c:if test="${existingFactura != null}">
		<c:set var="display" value="Editar Factura" />
	</c:if>
	<c:if test="${existingFactura == null}">
		<c:set var="display" value="Nova Factura" />
	</c:if>
	<c:if test="${message != null}">
		<c:set var="display" value="Nova Factura" />
	</c:if>
	<form name="form1" id="form1" action="${formAction}" method="post">

		<caption>
			<div id="disp">
				<h2>
					<div id="div1" title="My Test">${display}</div>
				</h2>
			</div>
		</caption>
		<table id="myTable">
			<thead>
				<tr>
					<th>Data</th>
					<th>Valor</th>
					<th>Desconto</th>
					<th>Valor Iva</th>
					<th>Total</th>
					<th>Fornecedor</th>
					<th>Factura#</th>
				<tr>
					</the
			ad>
			<tbody>
				<c:set var="factura" scope="session" value="${existingFactura}" />
				<c:if test="${existingFactura != null}">
					<c:set var="data" value="${factura[0].data}" />
				</c:if>
				<c:if test="${existingFactura == null}">
					<c:set var="data" value="${data2}" />
				</c:if>

				<tr style="background-color: mistyrose;">
					<input type="hidden" name="no" id="no1"
						value="<c:out value='${factura[0].no}' />" />
					<td><input type="date" size="8" name="date" tabindex="-1"
						value="<c:out value='${data}' />" required="required"></td>
					<td><input readonly type="text" size="8" name="preco"
						tabindex="-1"
						value="<fmt:formatNumber minFractionDigits='2' value='${factura[0].preco}' type='number'/>"
						id="valorSiva" style="text-align: right; width: 80px"></td>

					<td><input readonly type="text" size="5" name="descontoV"
						id="descontov" tabindex="-1"
						value="<fmt:formatNumber minFractionDigits='2' value='${factura[0].descontov}' type='number'/>"
						style="text-align: right; width: 80px"></td>

					<td><input readonly type="text" size="5" name="ivaV"
						id="valoriva" tabindex="-1"
						value="<fmt:formatNumber minFractionDigits="2" value='${factura[0].ivav}' type='number'/>"
						style="text-align: right; width: 80px"></td>
					<td><input readonly type="text" size="5" name="valorLiq"
						id="total" tabindex="-1"
						value="<fmt:formatNumber minFractionDigits='2' value='${factura[0].valorliq}' type='number'/>"
						style="text-align: right; width: 80px"></td>

					<td colspan="2"><input type="text" 
						value="<c:out value='${factura[0].fornecedor}' />"
						name="fornecedor" required="required"
						style="text-align: right; width: 175px"></td>

					<td><input type="text" id="invNo"
						value="<c:out value='${factura[0].invno}' />" name="invNo"
						required="required" style="text-align: right; width: 150px">
					</td>

					<td><input type="button" value="Add Row"
						class="btn btn-success" onclick="addRow()"></td>
					<td><input type="hidden" name="item"
						value="<c:out value='${factura[0].item}' />" /></td>
					<!-- 					<c:choose>
						<c:when test="${color eq 'red'}">
							<c:set var="varclass" value=".gtextred" />
						</c:when>
						<c:otherwise>
							<c:set var="varclass" value=".gtextgreen" />
						</c:otherwise>    
					</c:choose>   -->
					<td class="${color}">${message}
					<td class="" ${varClass}" colspan="2"&nbsp;></td>
					<td><input name="designacao" value=" " type="hidden"></td>
					<td><input name="quantidade" value="0" type="hidden"></td>
					<td><input name="un" value="" type="hidden"></td>
					<td><input name="desconto" value="0.0" type="hidden"></td>
					<td><input name="iva" value="0" type="hidden"></td>
					<td><input type="hidden" value="" name="parcela"></td>
				</tr>

			</tbody>
			<thead>
				<tr>
					<th>Designacao</th>
					<th>Quant.</th>
					<th>UN</th>
					<th>Preco</th>
					<th>Valor</th>
					<th>Desc%</th>
					<th>Desc</th>
					<th>Valor Liq.</th>
					<th>Iva%</th>
					<th>Iva</th>
					<th>Parcela</th>
				<tr>
			</thead>
			<tbody>

				<c:forEach var="factura" items="${existingFactura}" varStatus="stat">
					<c:if test="${!stat.first}">
						<input type="hidden" name="no"
							value="<c:out value='${factura.no}' />" />
						<tr>
							<td><input type="text" size="20"
								value="<c:out value='${factura.designacao}'/>" name="designacao"></td>

							<td><input type="currency" name="quantidade"
								value="<c:out value='${factura.quantidade}'/>"
								onchange="ValidateNum(this);tableloop(this);"
								style="text-align: right; width: 60px"></td>

							<td><input type="text" size="3" name="un"
								value="<c:out value='${factura.un}' />" name="un"></td>

							<td><input type="text" size="5"
								value="<fmt:formatNumber minFractionDigits='2' value='${factura.preco}' type='number'/>"
								name="preco" onchange="ValidateNum(this);tableloop(this);"
								id="valor" style="text-align: right;"></td>

							<td><input type="currency" size="5" tabindex="-1"
								value="<fmt:formatNumber minFractionDigits='2' value='${factura.quantidade*factura.preco}' type="number" />"
								onchange="ValidateNum(this);tableloop(this);"
								style="text-align: right;"></td>

							<td><input type="currency" size="3"
								value="<c:out value='${factura.desconto}'/>" name="desconto"
								onchange="ValidateNum(this);tableloop(this);"
								style="text-align: right;"></td>

							<td><input readonly type="currency" size="4" tabindex="-1"
								value="<c:out value='${factura.descontov}' />" name="descontoV"
								onchange="ValidateNum(this);tableloop(this);"
								style="text-align: right;"></td>

							<td><input readonly type="currency" size="5" tabindex="-1"
								value="<fmt:formatNumber minFractionDigits='2' value='${factura.valorliq}' type="number" />"
								name="valorLiq" style="text-align: right;"></td>

							<td><input type="currency" size="3"
								value="<c:out value='${factura.iva}' />" name="iva"
								onchange="ValidateNum(this);tableloop(this);"
								style="text-align: right;"></td>

							<td><input type="currency" size="4" tabindex="-1"
								value="<c:out value='${factura.ivav}' />"
								style="text-align: right;" name="ivaV"></td>

							<td><input type="text" size="16"
								value="<c:out value='${factura.parcela}' />" name="parcela"></td>

							<td><input type="hidden"
								value="<c:out value='${factura.fornecedor}' />"
								name="fornecedor"></td>

							<td><input name="date" type="hidden"></td>
							<td><input name="invNo" type="hidden"></td>
							<td><input name="item"
								value="<c:out value='${factura.item}' />" type="hidden"></td>
						</tr>
					</c:if>
				</c:forEach>
				<%
					for (int i = 1; i <= 3; i++) {
				%>
				<input type="hidden" name="no" value="0" />
				<tr>
					<td><input type="text" size="20" value="" name="designacao"></td>

					<td><input type="currency" name="quantidade"
						onchange="ValidateNum(this);tableloop(this);"
						style="text-align: right; width: 60px"></td>

					<td><input type="text" size="3" name="un" name="un"></td>

					<td><input type="text" size="5"
						value="<fmt:formatNumber minFractionDigits='2' value='' type='number'/>"
						name="preco" onchange="ValidateNum(this);tableloop(this);"
						id="valor" style="text-align: right;"></td>

					<td><input type="currency" size="5" value="<c:out value=''/>"
						tabindex="-1" onchange="ValidateNum(this);tableloop(this);"
						style="text-align: right;"></td>

					<td><input type="currency" size="3" value="<c:out value=''/>"
						name="desconto" onchange="ValidateNum(this);tableloop(this);"
						style="text-align: right;"></td>

					<td><input readonly type="currency" size="4" tabindex="-1"
						value="<c:out value='' />" name="descontoV"
						onchange="ValidateNum(this);tableloop(this);"
						style="text-align: right;"></td>

					<td><input readonly type="currency" size="5" tabindex="-1"
						value="<fmt:formatNumber minFractionDigits='2' value='' type="number" />"
						name="valorLiq" style="text-align: right;"></td>

					<td><input type="currency" size="3" value="<c:out value='' />"
						name="iva" onchange="ValidateNum(this);tableloop(this);"
						style="text-align: right;"></td>

					<td><input type="currency" size="4" value="<c:out value='' />"
						tabindex="-1" name="ivaV" style="text-align: right;"></td>

					<td><input type="text" size="16" value="" name="parcela"></td>

					<td><input type="hidden" value="<c:out value='' />"
						name="fornecedor"></td>

					<td><input name="date" value="<c:out value='${data}'/>"
						type="hidden"></td>
					<td><input name="invNo" value="<c:out value=''/>"
						type="hidden"></td>
					<td><input name="item" value="<c:out value='${i}' />"
						type="hidden"></td>
				</tr>
				<%
					}
				%>

			</tbody>
		</table>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="submit" class="btn btn-success">Save</button>
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
				href="<%=request.getContextPath()%>/" class="btn btn-success">Lista
					de Facturas</a></td>
		</tr>
	</form>
</body>

</html>