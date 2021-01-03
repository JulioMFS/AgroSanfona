<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Admin Page</title>
</head>
<%
	//In case, if Admin session is not set, redirect to Login page
	if ((request.getSession(false).getAttribute("Admin") == null)) {
%>
<jsp:forward page="/JSP/Login.jsp"></jsp:forward>
<%
	}
%>
<%
	Cookie username = new Cookie("user-name-in-jsp", "SomeNAME");

	username.setMaxAge(60 * 60 * 10);
	username.setPath("/");

	// Add both the cookies in the response header.
	response.addCookie(username);
%>
<body>
	<center>
		<h2>Admin's Home</h2>
	</center>

	Welcome
	<mark> <%=request.getAttribute("userName")%>,</mark>
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