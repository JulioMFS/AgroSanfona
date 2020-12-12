<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>User Management Application</title>
<!-- 
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous"> -->
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th,td {
	text-align: left;
	padding: 2px;
}

div {
	text-align: right;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

.primary-nav {
	display: -webkit-flex;
	display: flex;
	list-style-type: none;
	padding: 0;
	justify-content: flex-end;
}

.left {
	margin-right: auto;
}
</style>
</head>
<%@page import="java.util.*"%>
<%
	String data1, data2, designacao, fornecedor;

	java.text.DateFormat df = new java.text.SimpleDateFormat(
			"yyyy-MM-dd");
	Calendar aCalendar = Calendar.getInstance();
	java.util.Date today = aCalendar.getTime();
	aCalendar.add(Calendar.MONTH, -3);
	aCalendar.set(Calendar.DAY_OF_MONTH, 1);
	java.util.Date threeMonthsAgo = aCalendar.getTime();
	data1 = df.format(threeMonthsAgo);
	data2 = df.format(today);

	if (request.getParameter("data1") == null)
		data1 = "";
	else
		data1 = request.getParameter("data1");
	if (request.getParameter("data2") == null)
		data2 = "";
	else
		data2 = request.getParameter("data2");
	if (request.getParameter("designacao") == null)
		designacao = "";
	else
		designacao = request.getParameter("designacao");
	if (request.getParameter("fornecedor") == null)
		fornecedor = "";
	else
		fornecedor = request.getParameter("fornecedor");
%>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand">
					Factura Management App </a>
			</div>

			<ul class="primary-nav">
				<li class="left"><a href="<%=request.getContextPath()%>/"
					class="nav-link">Facturas</a></li>

				<li class="right"><a
					href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/AgroSanfonaWeb/"
					class="nav-link">Agro Sanfona</a></li>
			</ul>
		</nav>
	</header>
	<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->
	<br>

	<div class="row">


		<div class="container">
			<h3 class="text-center">Lista de Facturas</h3>
			<hr>

			<form action="FacturaServlet" method="post">
				<table style="with: 50%">
					<tr>
						<td><div class="container text-left">

								<a href="<%=request.getContextPath()%>/new"
									class="btn btn-success">&nbsp;&nbsp;&nbsp;Adicionar Factura
									&nbsp;&nbsp;&nbsp;</a>
							</div></td>
						<td>Data Inicial</td>
						<td><input type="date" name="data1"
							value='<%=request.getSession().getAttribute("data1")%>'
							min="2010-01-01" max="2030-12-31" /></td>
						<td>Data Final</td>
						<td><input type="date" name="data2"
							value='<%=request.getSession().getAttribute("data2")%>'
							min="2010-01-01" max="2030-12-31" /></td>
						<td>Designacao</td>
						<td><input type="text" name="designacao"
							value='<%=request.getSession().getAttribute("designacao")%>'
							style="width: 120px;" /></td>
						<td>Fornecedor</td>
						<td><input type="text" name="fornecedor"
							value='<%=request.getSession().getAttribute("fornecedor")%>'
							style="width: 120px;" /></td>
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
						<th style="width: 80px;">Data</th>
						<th style="width: 90px;">Factura#</th>
						<th style="width: 40px;">#</th>
						<th style="width: 110px;">Designacao</th>
						<th style="width: 60px;">Quant</th>
						<th style="width: 30px;">UN</th>
						<th style="width: 80px;">Preco/Valor</th>
						<th style="width: 40px;">Desc%</th>
						<th style="width: 40px;">Desc</th>
						<th style="width: 80px;">Valor</th>
						<th style="width: 40 px;">Iva%</th>
						<th style="width: 80 px;">Iva</th>
						<th style="width: 100px;">Parcela</th>
					</tr>
				</thead>
				<tbody>
					<!--   for (Todo todo: todos) {  -->

					<c:forEach var="factura" items="${listFactura}">
						<tr
							<c:if test="${factura.item eq '00'}"> style="background-color: mistyrose;"</c:if>
							<c:if test="${factura.item ne '00'}"> style="background-color: white;"</c:if>>
							<!-- 							<td><type ="hidden" c:out value="${factura.no}" /></td>   -->
							<td><c:out value="${factura.data}" /></td>
							<td><c:out value="${factura.invno}" /></td>
							<td><fmt:formatNumber pattern="00" value='${factura.item}' /></td>
							<td><c:out value="${factura.designacao}" /></td>
							<td><fmt:formatNumber minFractionDigits="2"
									value='${factura.quantidade}' type="number" /></td>
							<td><c:out value="${factura.un}" /></td>
							<td>
								<div>
									<fmt:formatNumber value='${factura.preco}' type="currency"
										currencySymbol="" />
								</div>
							</td>
							<td style="text-align: right;"><fmt:formatNumber
									value='${factura.desconto}' type="number" />%</td>
							<td><div>
									<fmt:formatNumber value='${factura.descontov}' type="currency"
										currencySymbol="" />
								</div></td>
							<td><div>
									<fmt:formatNumber value='${factura.valorliq}' type="currency"
										currencySymbol="" />
								</div></td>
							<td style="text-align: right"><fmt:formatNumber
									value='${factura.iva}' type="number" />%</td>
							<td><div>
									<fmt:formatNumber value='${factura.ivav}' type="currency"
										currencySymbol="" />
								</div></td>
							<td><c:out value="${factura.parcela}" /></td>
							<td><a href="edit?no=<c:out value='${factura.no}' />">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="delete?no=<c:out value='${factura.no}' />&amp;data=<c:out value='${factura.data}' />&amp;invno=<c:out value='${factura.invno}' />&amp;item=<c:out value='${factura.item}' />">Delete</a></td>
						</tr>
					</c:forEach>
					<!-- } -->
				</tbody>

			</table>
			<%
				if (request.getSession().getAttribute("data1") != null)
					data1 = request.getSession().getAttribute("data1").toString();
				if (request.getSession().getAttribute("data2") != null)
					data2 = request.getSession().getAttribute("data2").toString();
				if (request.getSession().getAttribute("designacao") != null)
					designacao = request.getSession().getAttribute("designacao")
							.toString();
				if (request.getSession().getAttribute("fornecedor") != null)
					fornecedor = request.getSession().getAttribute("fornecedor")
							.toString();

				if (request.getParameter("data1") != null)
					data1 = request.getParameter("data1");
				if (request.getParameter("data2") != null)
					data2 = request.getParameter("data2");
				if (request.getParameter("designacao") != null)
					designacao = request.getParameter("designacao");
				if (request.getParameter("fornecedor") != null)
					fornecedor = request.getParameter("fornecedor");

				request.getSession().setAttribute("data1", data1);
				request.getSession().setAttribute("data2", data2);
				request.getSession().setAttribute("designacao", designacao);
				request.getSession().setAttribute("fornecedor", fornecedor);
			%>
		</div>
	</div>
</body>
</html>