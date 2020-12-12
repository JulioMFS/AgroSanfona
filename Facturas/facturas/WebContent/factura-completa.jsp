<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<!--<c:set var="count" value="0" scope="page" />-->
<head>
<title>Factura Management Application</title>
<!-- <link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous"> -->
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th,td {
	text-align: center;
	padding: 1px;
}

div {
	text-align: center;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
</style>
<script type="text/javascript">
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
		var noRows = table.rows.length;

		var quant = document.getElementById("myTable").rows[row].cells[5]
				.querySelector('input').value;
		if (quant == null || isNaN(quant) || quant == "")
			quant = 0;

		var preco = document.getElementById("myTable").rows[row].cells[7]
				.querySelector('input').value;
		if (preco == null || isNaN(preco) || preco == "")
			preco = 0;

		var desc = document.getElementById("myTable").rows[row].cells[8]
				.querySelector('input').value;
		if (desc == null || isNaN(desc) || desc == "")
			desc = 0;

		var iva = document.getElementById("myTable").rows[row].cells[11]
				.querySelector('input').value;
		if (iva == null || isNaN(iva) || iva == "")
			iva = 0;

		var x = document.getElementById("myTable").rows[row].cells;

		var valor = parseFloat(quant) * parseFloat(preco);
		var descV = valor * (parseFloat(desc) / 100);
		var valorLiq = valor - descV;
		var ivaV = valorLiq * (parseFloat(iva) / 100);

		/* Desconto Valor Liquido */
		document.getElementById("myTable").rows[row].cells[10]
				.querySelector('input').value = valorLiq.toFixed(2);

		/* Iva Valor */
		document.getElementById("myTable").rows[row].cells[12]
				.querySelector('input').value = ivaV.toFixed(2);

		/* Desconto Valor */
		document.getElementById("myTable").rows[row].cells[9]
				.querySelector('input').value = descV.toFixed(2);

		var gross = 0;
		var descv = 0;
		var ivav = 0;
		var valor = 0;
		var quant = 0;
		var preco = 0;
		var desc = 0;
		var iva = 0;

		var tgross = 0;
		var tdescv = 0;
		var tivav = 0;
		var tvalor = 0;

		for (var i = 2; i < table.rows.length; i++) {

			quant = document.getElementById("myTable").rows[i].cells[5]
					.querySelector('input').value;
			if (isNaN(quant) || quant == "")
				quant = 0;
			preco = document.getElementById("myTable").rows[i].cells[7]
					.querySelector('input').value;
			if (isNaN(preco) || preco == "")
				preco = 0;
			descv = document.getElementById("myTable").rows[i].cells[9]
					.querySelector('input').value;
			if (isNaN(descv) || descv == "")
				descv = 0;
			valor = document.getElementById("myTable").rows[i].cells[10]
					.querySelector('input').value;
			if (isNaN(valor) || valor == "")
				valor = 0;
			ivav = document.getElementById("myTable").rows[i].cells[12]
					.querySelector('input').value;
			if (isNaN(ivav) || ivav == "")
				ivav = 0;
			gross = (parseFloat(quant) * parseFloat(preco));
			tgross = tgross + gross;
			tdescv = tdescv + parseFloat(descv);
			tivav = tivav + parseFloat(ivav);
			tvalor = tvalor + parseFloat(valor);
		}

		tvalor = tvalor + tivav;

		document.getElementById("myTable").rows[1].cells[7]
				.querySelector('input').value = tgross.toFixed(2);

		document.getElementById("myTable").rows[1].cells[9]
				.querySelector('input').value = tdescv.toFixed(2);

		document.getElementById("myTable").rows[1].cells[10]
				.querySelector('input').value = tvalor.toFixed(2);

		document.getElementById("myTable").rows[1].cells[12]
				.querySelector('input').value = tivav.toFixed(2);

		return false;
	}
</script>
</head>

<body>

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
	<div>
		<div>
			<div class="card-body">
				<c:if test="${existingFactura != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${ExistingFactura == null}">
					<form action="insert" method="post">
				</c:if>
				<fmt:setLocale value="${user.locale}" />
				<caption>
					<h2>
						<c:if test="${existingFactura != null}">
                                    Editar Factura
                                </c:if>
						<c:if test="${existingFactura == null}">
                                    Nova Factura
                                </c:if>
					</h2>
				</caption>
				<table id=myTable>

					<thead>
						<tr>
							<th>Data</th>
							<th>Factura#</th>
							<th>#</th>
							<th>Designacao</th>
							<th>Quant</th>
							<th>UN</th>
							<th>Preco</th>
							<th>Desc%</th>
							<th>Desc</th>
							<th>Valor</th>
							<th>Iva%</th>
							<th>Iva</th>
							<th>Parcela</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="factura" items="${existingFactura}">

							<c:if test="${existingFactura != null}">
								<input type="hidden" name="no"
									value="<c:out value='${factura.no}' />" />
							</c:if>
							<tr
								<c:if test="${factura.item eq '00'}"> style="background-color: mistyrose;"</c:if>
								<c:if test="${factura.item ne '00'}"> style="background-color: white;"</c:if>>

								<td><div>
										<input type="date" value="<c:out value='${factura.data}' />"
											class="form-control" name="data" style="width: 130px"
											required="required">
									</div></td>

								<td><div>
										<input type="text" value="<c:out value='${factura.invno}' />"
											class="form-control" name="invno" style="width: 90px">
									</div></td>

								<td><div>
										<input type="text"
											value="<fmt:formatNumber pattern="00" value='${factura.item}' />"
											class="form-control" name="item" style="width: 30px">
									</div></td>

								<td><div>
										<input type="text"
											value="<c:out value='${factura.designacao}' />"
											class="form-control" name="designacao" style="width: 140px"
											required="required">
									</div></td>
								<td><div>
										<input type="text"
											value="<fmt:formatNumber minFractionDigits = "2" value='${factura.quantidade}' type="number"/>"
											class="form-control" style="width: 60px; text-align: right;"
											name="quantidade"
											onchange="ValidateNum(this);tableloop(this);">
									</div></td>
								<td><div>
										<input type="text" value="<c:out value='${factura.un}' />"
											class="form-control" style="width: 40px" name="un">
									</div></td>
								<td><div>
										<input type="text"
											value="<fmt:formatNumber value = '${factura.preco}' type = 'currency' currencySymbol=''/>"
											class="form-control" style="text-align: right; width: 70px;"
											name="preco" onchange="ValidateNum(this);tableloop(this);">
									</div></td>
								<td><div>
										<input type="text"
											value="<fmt:formatNumber value='${factura.desconto}' type="number" currencySymbol=""/>"
											class="form-control" style="text-align: right; width: 40px;"
											name="desconto" onchange="ValidateNum(this);tableloop(this)">
									</div></td>
								<td><div>
										<input type="text"
											value="<fmt:formatNumber value='${factura.descontov}' type="currency" currencySymbol=""/>"
											class="form-control" style="text-align: right; width: 80px;"
											name="descontov" id="descontov">
									</div></td>
								<td><div>
										<input type="text"
											value="<fmt:formatNumber value='${factura.valorliq}' type="currency" currencySymbol=""/>"
											class="form-control" style="text-align: right; width: 80px"
											name="valorliq">
									</div></td>
								<td><div>
										<input type="text"
											value="<fmt:formatNumber value='${factura.iva}' type="number"/>"
											class="form-control" style="width: 40px" name="iva"
											onchange="ValidateNum(this);tableloop(this);">
									</div></td>
								<td><div>
										<input type="text"
											value="<fmt:formatNumber value='${factura.ivav}' type="currency" currencySymbol=""/>"
											class="form-control" style="text-align: right; width: 80px"
											name="ivav">
									</div></td>
								<td><div style="width: 100px">
										<input type="text"
											value="<c:out value='${factura.parcela}' />"
											class="form-control" name="parcela">
									</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<table>
					<col width="100">
					<col width="100">
					<tr>
						<td>
							<button type="submit" onclick="javascript:validate();"
								class=" btn btn-success">Save</button>
						</td>
						<td><a href="FacturaServlet" class="btn btn-success">Voltar</a></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>

				</form>
			</div>
		</div>
	</div>

</body>
</html>