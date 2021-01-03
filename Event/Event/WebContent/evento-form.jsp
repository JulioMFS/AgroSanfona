<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.util.Calendar"%><%@page import="java.text.*"%>
<%@page import="java.text.*"%>
<html>

<head>
<title>Evento Management Application</title>
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
</style>
</head>

<body>
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
					Evento Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/"
					class="nav-link">Eventos</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<!--  	<form action="insert" method="post"> -->
	<caption>
		<h2>
			<c:if test="${existingEvent != null}">
                                    Editar Evento
                                </c:if>
			<c:if test="${existingEvent == null}">
                                    Novo Evento
                                </c:if>
		</h2>
	</caption>
	<c:if test="${existingEvent != null}">
		<form action="update" method="post">
	</c:if>
	<c:if test="${existingEvent == null}">
		<form action="insert" method="post">
	</c:if>

	<table class="table table-bordered" style="with: 50%">
		<tr>
			<td></td>
			<td><input type="hidden" name="no" type="text"
				value='<%=request.getAttribute("no")%>' /></td>
		</tr>

		<tr>
			<td>Data</td>
			<td><input type="date" size="8" name="data"
				value='<%=request.getAttribute("date")%>' required="required"></td>
		</tr>
		<tr>
			<td>Parcela</td>
			<td><select name="parcela" style="width:200px">
					<c:forEach items="${pparcela}" var="row">
						<option value="${row.nome}"
							${row.nome == parcela ? 'selected="selected"' : ''}>${row.nome}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>Prestador</td>
			<td><select name="prestador" style="width:200px">
					<c:forEach items="${pprestador}" var="row">
						<option value="${row.prestador}"
							${row.prestador == prestador ? 'selected="selected"' : ''}>${row.prestador}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>Evento</td>
			<td><select name="evento" style="width:200px">
					<c:forEach items="${eevento}" var="row">
						<option value="${row.eventos}"
							${row.eventos == evento ? 'selected="selected"' : ''}>${row.eventos}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>Alfaia</td>
			<td><select name="alfaia" style="width:200px">
					<c:forEach items="${aalfaia}" var="row">
						<option value="${row.alfaia}"
							${row.alfaia == alfaia ? 'selected="selected"' : ''}>${row.alfaia}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>Produto</td>
			<td><input type="text"  style="width:300px" name="produto"
				value='<%=request.getAttribute("produto")%>'></td>
		</tr>
		<tr>
			<td>Fornecedor</td>
			<td><select name="fornecedor" style="width:200px">
					<c:forEach items="${ffornecedor}" var="row">
						<option value="${row.fornecedores}"
							${row.fornecedores == ffornecedor ? 'selected="selected"' : ''}>${row.fornecedores}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>Quant.</td>
			<td><input type="text" size="5" name="quantidade"
				value='<%=request.getAttribute("quantidade")%>'></td>
		</tr>
		<tr>
			<td>UN</td>
			<td><input type="text" size="5" name="un"
				value='<%=request.getAttribute("un")%>'></td>
		</tr>
		<tr>
			<td>Settings</td>
			<td><input type="text"  style="width:300px" name="settings"
				value='<%=request.getAttribute("settings")%>'></td>
		</tr>
	</table>

	<button type="submit" class="btn btn-success">Save</button>
	</form>
</body>

</html>