<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.Date,java.util.Calendar,java.text.SimpleDateFormat,java.text.ParseException"%><!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>View Evento</title>

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
		<a href="https://www.javaguides.net" class="navbar-brand"> Evento
			Management App </a>
	</div>

	<ul class="navbar-nav">
		<li><a href="<%=request.getContextPath()%>/" class="nav-link">Facturas</a></li>
	</ul>
</nav>
</head>

<body>
	<h1>
		<%
			String data1 = "", data2 = "";
			java.text.DateFormat df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			Calendar aCalendar = Calendar.getInstance();
			java.util.Date today = aCalendar.getTime();
			aCalendar.set(Calendar.MONTH, 0);
			aCalendar.set(Calendar.DAY_OF_MONTH, 1);
			java.util.Date firstDateOfJan = aCalendar.getTime();
			data1 = df.format(firstDateOfJan);
			data2 = df.format(today);
		%>
	</h1>
	<h1>Evento</h1>
	<form action="EventoList.jsp" method="post">
		<table style="with: 50%">
			<tr>
				<th>Data Inicial</th>
				<th>Data Final</th>
				<th>Parcela</th>
				<th>Evento</th>
				<th>Prestador</th>

			</tr>
			<tr>

				<td><input type="date" size="8" name="Data1" value="<%=data1%>"
					required="required" /></td>

				<td><input type="date" name="Data2" value="<%=data2%>"
					required="required" /></td>
				<td><input type="text" name="Parcela" /></td>
				<td><input type="text" name="Evento" /></td>
				<td><input type="text" name="Prestador" /></td>
			</tr>
		</table>
		<input type="submit" value="Submit" class="btn btn-success" />
	</form>
</body>

</html>