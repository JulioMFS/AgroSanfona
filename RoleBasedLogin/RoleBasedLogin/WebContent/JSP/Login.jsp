<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body,html {
	height: 100%;
	margin: 0;
}

.bg {
	/* The image used */
	background-image: url("images/CloudsAndMeadow.jpg");
	/* Full height */
	height: 100%;
	/* Center and scale the image nicely */
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
}
</style>
</head>
<body>
	<div class="bg">
		<form name="form" action="<%=request.getContextPath()%>/LoginServlet"
			method="post">
			<br> <br> <br>
			<table align="center">
				<tr>
					<td>Username</td>
					<td><input title="hint: your Nickname" type="text"
						name="username" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input
						title="hint: the name of your last family pet followed by your date of birth in this format: 'ddmmyy'"
						type="password" name="password" /></td>
				</tr>
				<tr>
					<td><span style="color: red"><%=(request.getAttribute("errMessage") == null) ? ""
					: request.getAttribute("errMessage")%></span></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Login"></input>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
						<input type="reset" value="Reset"></input></td>
				</tr>
			</table>
			<br> <br> <br> <br> <br> <br> <br>
			<br>
			<p style="text-align: center">
				Please note that this is a <b><em>work in progress</em></b>
				application hosted on a raspberry pi <br>and new functionality
				will be added from time to time.
			</p>
			<br> <br>
			<p style="text-align: center">
				There are currently three user types with different levels of
				authority.<br> Currently there are a few user names and
				passwords reserved for some special users.<br> These user and
				passwords have been set by the administrator (moi/the boss of all
				this).
			</p>
		</form>
</body>
</html>