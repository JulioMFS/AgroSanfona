<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>Farm Income and Expense Statement</title>
<!--  <link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous"> -->

<%@include file="/common/header.html"%>
</head>
<body>
	<%@page import="java.util.*"%>
	<%
		String datainicial = "", datafinal = "";
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		Calendar aCalendar = Calendar.getInstance();
		java.util.Date today = aCalendar.getTime();
		aCalendar.set(Calendar.MONTH, 0);
		aCalendar.set(Calendar.DAY_OF_MONTH, 1);
		java.util.Date firstDateOfJan = aCalendar.getTime();
		datainicial = df.format(firstDateOfJan);
		datafinal = df.format(today);

		if (request.getParameter("datainicial") != null)
			datainicial = request.getParameter("datainicial");
		if (request.getParameter("datafinal") != null)
			datafinal = request.getParameter("datafinal");
		request.getSession().setAttribute("datainicial", datainicial);
		request.getSession().setAttribute("datafinal", datafinal);
	%>

	<form action="list" method="post" name="form1" id="form1">
		<header> </header>

		<div class="row">
			<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

			<div class="container">
				<table style="with: 50%">
					<tr>
						<td></td>
						<td><label>Data Inicial <input id="datainicial"
								name="datainicial" type="date"
								value='<%=request.getSession().getAttribute("datainicial")%>'
								min="2010-01-01" max="2030-12-31" />
						</label></td>
						<td><label>Data Final <input id="datafinal"
								name="datafinal" type="date"
								value='<%=request.getSession().getAttribute("datafinal")%>'
								min="2010-01-01" max="2030-12-31" />
						</label></td>
						<%
							String whichRadio = request.getParameter("selectiontype");
							String classificacaochk = "", searachk = "", parcelachk = "", inoutchk = "";
							if (whichRadio == null)
								whichRadio = "classificacao";
							if (whichRadio.equals("classificacao"))
								classificacaochk = " checked";
							if (whichRadio.equals("seara"))
								searachk = " checked";
							if (whichRadio.equals("parcela"))
								parcelachk = " checked";
							if (whichRadio.equals("inout"))
								inoutchk = " checked";
						%>
						<td><label for="Classificacao">Classificacao</label> <input
							type="radio" id="Classificacao" name="selectiontype"
							value="classificacao" <%=classificacaochk%>> <label
							for="seara">Seara</label> <input type="radio" id="seara"
							name="selectiontype" value="seara" <%=searachk%>> <label
							for="parcela">Parcela</label> <input type="radio" id="parcela"
							name="selectiontype" value="parcela" <%=parcelachk%>> <label
							for="In/Out">In/Out</label> <input type="radio" id="inout"
							name="selectiontype" value="inout" <%=inoutchk%>></td>
						<td><input type="submit" value="Pesquisar"
							class="btn btn-success"></td>
						<td></td>
					</tr>
				</table>
				<table id="incomeexpensetbl" class="table table-bordered">
					<thead>
						<tr>
							<th
								style="text-align: center; font-size: 22px; text-decoration: underline;">I
								n c o m e</th>
							<th
								style="text-align: center; font-size: 22px; text-decoration: underline;">E
								x p e n s e s</th>
						</tr>
					</thead>
					<tbody>
						<td>
							<table id="incometbl" class="table table-bordered">
								<tr>
									<th>Classificacao</th>
									<th style="text-align: center;">€</th>
								</tr>
								<c:forEach var="income" items="${incomeExpenses}">
									<c:if test="${income.incMontante != 0 }">
										<tr>
											<td><c:out value="${income.classificacao}" /></td>
											<td><div>
													<fmt:formatNumber value='${income.incMontante}'
														type="currency" currencySymbol="" />
												</div></td>
										</tr>
									</c:if>
								</c:forEach>
								<tr>
									<td></td>
									<td style="text-align: right"><fmt:formatNumber
											value='${inc}' type="currency" currencySymbol="" /></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td></td>
								</tr>
								<tr class="bold-italic">
									<td style="text-align: right">Entradas</td>
									<td style="text-align: right"><fmt:formatNumber
											value='${inc}' type="currency" currencySymbol="" /></td>
								</tr>
								<tr class="bold-italic">
									<td style="text-align: right">Saidas</td>
									<td style="text-align: right"><fmt:formatNumber
											value='${exp}' type="currency" currencySymbol="" /></td>
								</tr>
								<tr class="bold-italic">
									<td></td>
									<c:choose>
										<c:when test="${incexp < 0}">
											<td class="bold-italic-red" style="text-align: right"><fmt:formatNumber
													value='${incexp}' type="currency" currencySymbol="" /></td>
										</c:when>
										<c:otherwise>
											<td class="bold-italic-green" style="text-align: right"><fmt:formatNumber
													value='${incexp}' type="currency" currencySymbol="" /></td>
										</c:otherwise>
									</c:choose>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td></td>
								</tr>
								<c:forEach var="inout" items="${inOut}">
									<tr class="bold-italic">
										<td style="text-align: right"><c:out
												value="${inout.desc}" /></td>
										<c:choose>
											<c:when test="${inout.bal < 0}">
												<td class="bold-italic-red" style="text-align: right"><fmt:formatNumber
														value='${inout.bal}' type="currency" currencySymbol="" /></td>
											</c:when>
											<c:otherwise>
												<td class="bold-italic-green" style="text-align: right"><fmt:formatNumber
														value='${inout.bal}' type="currency" currencySymbol="" /></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td>
							<table id="expensetbl" class="table table-bordered">
								<tr>
									<th>Classificacao</th>
									<th style="text-align: center;">€</th>
								</tr>
								<c:forEach var="expenses" items="${incomeExpenses}">
									<c:if test="${expenses.expMontante != 0 }">
										<tr>
											<td><c:out value="${expenses.classificacao}" /></td>
											<td><div>
													<fmt:formatNumber value='${expenses.expMontante}'
														type="currency" currencySymbol="" />
												</div></td>
											<td></td>
										</tr>
									</c:if>
								</c:forEach>
								<tr>
									<td></td>
									<td style="text-align: right"><fmt:formatNumber
											value='${exp}' type="currency" currencySymbol="" /></td>
								</tr>
							</table>
						</td>
					</tbody>
				</table>
				<%
					if (request.getParameter("datainicial") != null)
						datainicial = request.getParameter("datainicial");
					if (request.getParameter("datafinal") != null)
						datainicial = request.getParameter("datafinal");
					request.getSession().setAttribute("datainicial", datainicial);
					request.getSession().setAttribute("datafinal", datafinal);
				%>
			</div>
		</div>
	</form>
</body>
</html>