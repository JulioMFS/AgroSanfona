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
<%
	String userName = "Unknown User?", role = "Unknown Role?";
	Cookie cookie = null;
	Cookie[] cookies = null;

	// Get an array of Cookies associated with the this domain
	cookies = request.getCookies();

	if (cookies != null) {
		//	out.println("<h2> Found Cookies Name and Value</h2>");
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			//		out.print("Name : " + cookie.getName() + ", ");
			//		out.print("Value: " + cookie.getValue() + " <br/>");
			if (cookie.getName().equalsIgnoreCase("userName"))
				userName = cookie.getValue();
			if (cookie.getName().equalsIgnoreCase("role"))
				role = cookie.getValue();
		}
	} else {
		out.println("<h2>No cookies founds</h2>");
	}
	//	out.print("---> " + userName + " is a " + role);
%>
<%@include file="/common/header.html"%>

</head>
<body>
	<h1>Agro Sanfona</h1>
	<br>
	<p id="demo"></p>
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<form action="AgroServlet" method="post" name="form1" id="form1">
			<tr>
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
			</tr>
			<tr>
				<td>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/guiaEntrada"
							class="btn btn-success"> Guia de Entrada</a>
					</div>
				</td>



				<td>
					<%
						if (!role.equalsIgnoreCase("User")) {
					%>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/BancoExtracto"
							class="btn btn-success"> Banco Extracto</a>
					</div> <%
 	}
 %>
				</td>
				<td>
					<%
						if (!role.equalsIgnoreCase("User")) {
					%>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/IncomeExpenseStatement"
							class="btn btn-success"> Entradas e Saidas</a>
					</div> <%
 	}
 %>
				</td>

			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td>
					<div class="container text-left">
						<a
							href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/contadorEndesa/"
							class="btn btn-success"> Endesa</a>
					</div>
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<!-- 		<td><p>URL</p> 	<button onclick="myFunction()">Try it</button>  -->
				<script>
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
								+ "\nhostname: " + hostname + "\npathname: "
								+ path + "\nport: " + port + "\nprotocol: "
								+ protocol)
								+ "\ndomain: " + domain;
					}
				</script>
				</td>

				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</form>
	</table>

</body>
</html>
