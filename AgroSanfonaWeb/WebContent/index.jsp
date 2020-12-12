<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP List Eventos Records</title>
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
<nav class="navbar navbar-expand-md navbar-dark"
	style="background-color: tomato">
	<div>
		<a href="https://www.javaguides.net" class="navbar-brand"> Agro
			Sanfona App </a>
	</div>

	<ul class="navbar-nav">
		<li><a href="<%=request.getContextPath()%>/" class="nav-link">AgroSanfona</a></li>
	</ul>
</nav>

</head>
<body>

	<h1>Agro Sanfona</h1>
	<br>
	<p id="demo"></p>
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<form action="AgroServlet" method="post" name="form1" id="form1">
			<tr>
				<td></td>
				<td>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/facturas/"
							class="btn btn-success"> Facturas</a>
					</div>
				</td>
				<td>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/contadorelectricidade/"
							class="btn btn-success"> Electricidade</a>
					</div>
				</td>
				<td>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/Event/"
							class="btn btn-success"> Evento</a>
					</div>
				</td>
				<td>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/guiaEntrada"
							class="btn btn-success"> Guia de Entrada</a>
					</div>
				</td>
				<td>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/BancoExtracto"
							class="btn btn-success"> Banco Extracto</a>
					</div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/IncomeExpenseStatement"
							class="btn btn-success"> Entradas e Saidas</a>
					</div>
				</td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><p>URL</p>

					<button onclick="myFunction()">Try it</button> <script>
						function myFunction() {
							var href = location.href;
							//		document.getElementById("demo").innerHTML = href;

							var host = location.host;
							var hostname = location.hostname;
							//alert(hostname);
							var path = location.pathname;
							var port = location.port;
							var protocol = location.protocol;
							var domain = window.location.hostname;
							alert("href: " + href + "\nhost: " + host
									+ "\nhostname: " + hostname
									+ "\npathname: " + path + "\nport: " + port
									+ "\nprotocol: " + protocol);
						}
					</script></td>

				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</form>
	</table>

</body>
</html>
