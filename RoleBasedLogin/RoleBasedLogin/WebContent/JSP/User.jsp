<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>User Page</title>
</head>
<%
	//In case, if User session is not set, redirect to Login page.
	if ((request.getSession(false).getAttribute("User") == null)) {
%>
<jsp:forward page="/JSP/Login.jsp"></jsp:forward>
<%
	}
%>
<body>
	<center>
		<h2>User's Home</h2>
	</center>
	Welcome
	<mark> <%=request.getAttribute("userName")%></mark>
	<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	this is your role:
	<mark> <%=session.getAttribute("role")%></mark>
	<div style="text-align: right">
		<a
			href="<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/AgroSanfonaWeb/">Carry
			on to AgroSanfona</a>
	</div>
	<div style="text-align: right">
		<a href="<%=request.getContextPath()%>/LogoutServlet">Logout</a>
	</div>

</body>
</html>