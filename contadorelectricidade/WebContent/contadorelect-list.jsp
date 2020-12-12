<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Calendar"%>
<html>
<head>
<title>User Management Application</title>

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
				<li><a href="<%=request.getContextPath()%>/" class="nav-link">Facturas
						de Electricidade</a></li>
			</ul>
			<ul class="navbar-nav ml-auto">
				<li><a
					href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/AgroSanfonaWeb/"
					class="nav-link">Agro Sanfona</a></li>
			</ul>
		</nav>
	</header>
	<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->
	<br>

	<div class="row">


		<div class="container">
			<h3 class="text-center">Contadores de Electricidade</h3>
			<hr>

			<form action="ContadorElectServlet" method="post">
				<table style="with: 50%">
					<tr>
						<td><div class="container text-left">

								<a href="<%=request.getContextPath()%>/new"
									class="btn btn-success">Adicionar Leitura</a>
							</div></td>
						<td>Data Inicial</td>
						<td><input type="date" name="Data1" id="Data1"
							value='<%=request.getSession().getAttribute("D1Value")%>'
							min="2010-01-01" max="2030-12-31" /></td>
						<td>Data Final</td>
						<td><input type="date" name="Data2" id="Data2"
							value='<%=request.getSession().getAttribute("D2Value")%>'
							min="2010-01-01" max="2030-12-31" /></td>
						<td>Parcela</td>
						<td><input type="text" name="Parcela" id="Parcela"
							value='<%=request.getSession().getAttribute("PValue")%>' /></td>
						<td><input type="submit" value="Pesquisar"
							class="btn btn-success">
						<td>
					</tr>
				</table>

			</form>

			<br> <br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th></th>
						<th>Factura#</th>
						<th>Parcela</th>
						<th>Data Inicio</th>
						<th>Data final</th>
						<th>Leitura</th>
						<th>Est</th>
						<th>Vazio</th>
						<th>Ponta</th>
						<th>Cheia</th>
						<th>ForaVazio</th>
						<th>Valor</th>
					</tr>
				</thead>
				<tbody>
					<!--   for (Todo todo: todos) {  -->


					<c:forEach var="contadorelectricidade" items="${listcontadorelect}">
						<tr>
							<td><type ="hidden" c:out
									value="${contadorelectricidade.id}" /></td>
							<td><c:out value="${contadorelectricidade.fatura}" /></td>
							<td><c:out value="${contadorelectricidade.parcela}" /></td>
							<td><c:out value="${contadorelectricidade.data1}" /></td>
							<td><c:out value="${contadorelectricidade.data2}" /></td>
							<td><c:out value="${contadorelectricidade.leituradata}" /></td>
							<td><c:out value="${contadorelectricidade.est}" /></td>
							<td><fmt:formatNumber value='${contadorelectricidade.vazio}'
									type="number" /></td>
							<td><fmt:formatNumber value='${contadorelectricidade.ponta}'
									type="number" /></td>
							<td><fmt:formatNumber value='${contadorelectricidade.cheia}'
									type="number" /></td>
							<td><fmt:formatNumber
									value='${contadorelectricidade.foravazio}' type="number" /></td>
							<td><fmt:formatNumber value='${contadorelectricidade.valor}'
									type="currency" currencySymbol="€" /></td>
							<td><a
								href="edit?id=<c:out value='${contadorelectricidade.id}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="delete?id=<c:out value='${contadorelectricidade.id}' />">Delete</a></td>
						</tr>
					</c:forEach>
					<!-- } -->
				</tbody>

			</table>
		</div>
	</div>
</body>
</html>