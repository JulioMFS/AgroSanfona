<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<!--<c:set var="count" value="0" scope="page" />-->
<head>
<title>Factura Management Application</title>
<script type="text/javascript">
	function validate() {
		var pattern = /^-?[0-9]+(.[0-9]{1,2})?$/;
		var text = document.getElementById('Valor').value;
		if (text.match(pattern) == null) {
			alert('the format is wrong');
		} else {
			alert('OK');
		}
	}
</script>
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
	white-space: nowrap;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
</style>
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
					class="nav-link">Contador de Electricidade</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div>
		<div>
			<div class="card-body">
				<c:if test="${existingcontadorelect != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${ExistingFactura == null}">
					<form action="insert" method="post">
				</c:if>
				<fmt:setLocale value="${user.locale}" />
				<caption>
					<h2>
						<c:if test="${existingcontadorelect != null}">
                                    Editar Factura de Electricidade
                                </c:if>
						<c:if test="${existingcontadorelect == null}">
                                    Nova Factura de Electricidade
                                </c:if>
					</h2>
				</caption>
				<table>
					<thead>
						<tr>
							<th></th>
							<th>Fatura</th>
							<th>Parcela</th>
							<th>Data1</th>
							<th>Data2</th>
							<th>Leitura</th>
							<th>Est</th>
							<th>Vazio</th>
							<th>Ponta</th>
							<th>Cheia</th>
							<th>F Vazio</th>
							<th>Valor</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="contadorelect" items="${existingcontadorelect}">

							<!-- 				<c:if test="${existingcontadorelect != null}">
								<input type="hidden" name="id"
									value="<c:out value='${contadorelect.id}' />" />
							</c:if> -->
							<tr>
								<td><input type="hidden"
									value="<c:out value='${contadorelect.id}' />" name="id"></td>

								<td><input type="text"
									value="<c:out value='${contadorelect.fatura}' />" name="fatura"
									size="16" required="required"></td>

								<td><input type="text"
									value="<c:out value='${contadorelect.parcela}' />"
									name="parcela" size="8" required="required"></td>

								<td><input type="date"
									value="<c:out value='${contadorelect.data1}' />" name="data1"
									style="width: 140px" required="required"></td>

								<td><input type="date"
									value="<c:out value='${contadorelect.data2}' />" name="data2"
									style="width: 140px" required="required"></td>

								<td><input type="date"
									value="<c:out value='${contadorelect.leituradata}' />"
									name="leituradata" style="width: 140px" required="required"></td>

								<td><input type="text"
									value="<c:out value='${contadorelect.est}' />" name="est"
									style="width: 30px" required="required"></td>

								<td><input type="text"
									value="<fmt:formatNumber  value='${contadorelect.vazio}' type="number"/>"
									style="width: 60px" name="vazio"></td>

								<td><input type="text"
									value="<fmt:formatNumber  value='${contadorelect.ponta}' type="number"/>"
									style="width: 60px" name="ponta"></td>

								<td><input type="text"
									value="<fmt:formatNumber  value='${contadorelect.cheia}' type="number"/>"
									style="width: 60px" name="cheia"></td>

								<td><input type="text"
									value="<fmt:formatNumber  value='${contadorelect.foravazio}' type="number"/>"
									style="width: 60px" name="foravazio"></td>

								<td><input type="text"
									value="<fmt:formatNumber value = '${contadorelect.valor}' type = "currency" currencySymbol="€"/>"
									style="text-align: right; width: 80px" name="valor" id="valor"></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
				<table>
					<col width="100">
					<col width="100">
					<tr>
						<td></td>
						<td>
							<button type="submit" onclick="javascript:validate();"
								class="btn btn-success">Save</button>
						</td>
						<td><a href="ContadorElectServlet" class="btn btn-success">Voltar</a></td>
					</tr>
				</table>

				</form>
			</div>
		</div>
	</div>
</body>
</html>