<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP List Eventos Records</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<script type="text/javascript">
	function submit() {
		if (!sessionStorage.getItem("submitted")) {
			console.log("submitting");
			sessionStorage.setItem("submitted", "true");
			document.getElementById("form1").submit();
		} else {
			console.log("already submitted, not repeating");
			sessionStorage.removeItem("submitted");
		}
	}
</script>
<%@include file="/common/header.html"%>
</head>
<body onload="submit()">
	<%@page import="java.util.*"%>
	<%
		String data1 = "", data2 = "", parcela, evento, prestador;
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		Calendar aCalendar = Calendar.getInstance();
		java.util.Date today = aCalendar.getTime();
		aCalendar.set(Calendar.MONTH, 0);
		aCalendar.set(Calendar.DAY_OF_MONTH, 1);
		java.util.Date firstDateOfJan = aCalendar.getTime();
		data1 = df.format(firstDateOfJan);
		data2 = df.format(today);

		if (request.getParameter("Data1") == null)
			data1 = "";
		else
			data1 = request.getParameter("Data1");
		if (request.getParameter("Data2") == null)
			data2 = "";
		else
			data2 = request.getParameter("Data2");
		if (request.getParameter("Parcela") == null)
			parcela = "";
		else
			parcela = request.getParameter("Parcela");
		if (request.getParameter("Evento") == null)
			evento = "";
		else
			evento = request.getParameter("Evento");
		if (request.getParameter("Prestador") == null)
			prestador = "";
		else
			prestador = request.getParameter("Prestador");
		String d1 = "'" + data1 + "'";
		String d2 = "'" + data2 + "'";
		String p1 = "'%" + parcela + "%'";
		String e = "'%" + evento + "%'";
		String p2 = "'%" + prestador + "%'";
	%>
	<form action="Search" method="post" name="form1" id="form1">
		<caption>
			<h3>Eventos</h3>
		</caption>
		<table align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<th align><b>data1 </b></th>
				<th><b>data2 </b></th>
				<th><b>Parcela </b></th>
				<th><b>Evento </b></th>
				<th><b>Prestador </b></th>
			</tr>
			<tr>
				<td><input type="date" name="Data1" id="Data1"
					value='<%=request.getSession().getAttribute("data1")%>' /></td>
				<td><input type="date" name="Data2" id="Data2"
					value='<%=request.getSession().getAttribute("data2")%>' /></td>
				<td><input type="text" name="Parcela" id="Parcela"
					value="<%=request.getSession().getAttribute("parcela")%>" /></td>
				<td><input type="text" name="Evento" id="Evento"
					value="<%=request.getSession().getAttribute("evento")%>" /></td>
				<td><input type="text" name="Prestador" id="Prestador"
					value="<%=request.getSession().getAttribute("prestador")%>" /></td>
				<td><input type="submit" value="Submit" class="btn btn-success" /></td>
				<td><div class="container text-left">
						<a href="<%=request.getContextPath()%>/new"
							class="btn btn-success">Adicionar Evento</a>
					</div></td>
			</tr>
		</table>

		<sql:setDataSource var="myDS" driver="com.mysql.jdbc.Driver"
			url="jdbc:mysql://localhost:3306/agro?useSSL=false" user="root"
			password="j301052" />

		<sql:query var="ListEventos" dataSource="${myDS}">
        SELECT * FROM agro.event where Date between <%=d1%> and <%=d2%> and Parcela like <%=p1%> and event like <%=e%> and Prestador like <%=p2%>;
    </sql:query>

		<div align="center">
			<table border="0" cellpadding="5">

				<tr>
					<th></th>
					<th>Data</th>
					<th>Parcela</th>
					<th>Evento</th>
					<th>Prestador</th>
					<th>Alfaia</th>
					<th>Produto</th>
					<th>Fornecedor</th>
					<th>Quant.</th>
					<th>UN</th>
					<th>Settings</th>

				</tr>
				<c:forEach var="event" items="${ListEventos.rows}">
					<tr>
						<td><type ="hidden"c:out value="${event.No}" /></td>
						<td><c:out value="${event.Date}" /></td>
						<td><c:out value="${event.Parcela}" /></td>
						<td><c:out value="${event.event}" /></td>
						<td><c:out value="${event.Prestador}" /></td>
						<td><c:out value="${event.Alfaia}" /></td>
						<td><c:out value="${event.Produto}" /></td>
						<td><c:out value="${event.Fornecedor}" /></td>
						<td><fmt:formatNumber value='${event.Quantidade}'
								type="number" /></td>
						<td><c:out value="${event.Unidade}" /></td>
						<td><c:out value="${event.Settings}" /></td>
						<td><a href="edit?no=<c:out value='${event.No}' />">Edit</a>
							&nbsp;&nbsp;&nbsp;&nbsp; <a
							href="delete?no=<c:out value='${event.No}' />">Delete</a></td>
					</tr>
				</c:forEach>
			</table>
			<%

				if (request.getParameter("Data1") == null)
					data1 = "";
				else
					data1 = request.getParameter("Data1");
				if (request.getParameter("Data2") == null)
					data2 = "";
				else
					data2 = request.getParameter("Data2");
				if (request.getParameter("Parcela") == null)
					parcela = "";
				else
					parcela = request.getParameter("Parcela");

				request.getSession().setAttribute("data1", data1);
				request.getSession().setAttribute("data2", data2);
				request.getSession().setAttribute("parcela", parcela);
				request.getSession().setAttribute("evento", evento);
				request.getSession().setAttribute("prestador", prestador);
			%>
		</div>
	</form>
</body>
</html>