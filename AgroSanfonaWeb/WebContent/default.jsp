<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Server name(hostname) in JSP</title>
<link rel="stylesheet" href="/resources/themes/master.css"
	type="text/css" />
</head>
<body>
	<div id="allContent">

		<div id="myContent">
			<h1>JSP - Get Server HOST name</h1>
		</div>
		<div id="myExample1">
			Server host name is: <b><%=request.getServerName()%></b>
		</div>
		<div id="myExample2">
			getContextPath is: <b><%=request.getContextPath()%></b>
		</div>
	</div>
	<div id="myExample3">
		getLocalAddr is: <b><%=request.getLocalAddr()%></b>
	</div>
	</div>
	<div id="myExample4">
		getLocalName is: <b><%=request.getLocalName()%></b>
	</div>
	</div>
	<div id="myExample5">
		getLocalPort is: <b><%=request.getLocalPort()%></b>
	</div>
	</div>
	<div id="myExample6">
		getRequestURL is: <b><%=request.getRequestURL()%></b>
	</div>
	<div id="myExample7">
		getPathInfo is: <b><%=request.getPathInfo()%></b>
	</div>
	<div id="myExample8">
		getContextPath is: <b><%=request.getContextPath()%></b>
	</div>
	<div id="myExample9">
		getServletPath is: <b><%=request.getServletPath()%></b>
	</div>
	<c:set var="req" value="${pageContext.request}" />
	<c:set var="baseURL"
		value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
	baseURL
	<a href="${pageContext.servletContext.contextPath}/">Cancel</a>
	</div>
</body>
</html>