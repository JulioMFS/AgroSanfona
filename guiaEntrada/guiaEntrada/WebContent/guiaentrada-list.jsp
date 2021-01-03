<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>Guia de Entrada Management Application</title>
<!--  <link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous"> -->

<%@include file="/common/header.html"%>
</head>
<h1 style="text-align: center;">Guia de Entrada</h1>
<body>
	<form action="list" method="post" name="form1" id="form1">

		<header> </header>

		<div class="row">

			<div class="container">
				<table style="with: 50%">
					<tr>
						<td><div class="container text-left">

								<a href="<%=request.getContextPath()%>/new"
									class="btn btn-success">&nbsp;&nbsp;&nbsp;Adicionar Guia
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
						<td>Matricula</td>
						<td><input type="text" name="matricula"
							value='<%=request.getSession().getAttribute("matricula")%>'
							style="width: 120px;" /></td>
						<td>Parcela</td>
						<td><input type="text" name="parcela"
							value='<%=request.getSession().getAttribute("parcela")%>'
							style="width: 120px;" /></td>
						<td><input type="submit" value="Pesquisar"
							class="btn btn-success">
						<td>
					</tr>
				</table>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th style="text-align: center;">Guia No</th>
							<th style="text-align: center;">Data</th>
							<th style="text-align: center;">Hora</th>
							<th style="text-align: center;">Artg</th>
							<th style="text-align: center;">Descricao</th>
							<th style="text-align: center;">Matrcula</th>
							<th style="text-align: center;">Esp.</th>
							<th style="text-align: center;">Verde</th>
							<th style="text-align: center;">Hum.</th>
							<th style="text-align: center;">Conv</th>
							<th style="text-align: center;">Peso</th>
							<th style="text-align: center;">Nota Pag.</th>
							<th style="text-align: center;">Parcela</th>

						</tr>
					</thead>
					<tbody>
						<!--   for (Todo todo: todos) {  -->
						<c:forEach var="guiaentrada" items="${guiaentrada}">

							<tr
								<c:if test="${guiaentrada.id == 0}"> style="font-style: italic;font-weight: bold" </c:if>>
								<td style="width: 110px;"><c:out
										value="${guiaentrada.guiaNo}" /></td>
								<td style="width: 80px;"><c:out value="${guiaentrada.data}" /></td>
								<td><c:out value="${guiaentrada.hora}" /></td>
								<td><c:out value="${guiaentrada.artigo}" /></td>
								<td style="width: 220px;"><c:out
										value="${guiaentrada.descricao}" /></td>
								<td style="text-align: center"><c:out
										value="${guiaentrada.matricula}" /></td>
								<td style="text-align: right"><fmt:formatNumber
										minFractionDigits="2" maxFractionDigits="2"
										value='${guiaentrada.pesoEsp}' type="number" /></td>
								<td style="text-align: right"><fmt:formatNumber
										value='${guiaentrada.pesoVerde}' type="number" /></td>
								<td style="text-align: right"><fmt:formatNumber
										minFractionDigits="2" maxFractionDigits="2"
										value='${guiaentrada.humidade}' type="number" /></td>
								<td style="text-align: right"><fmt:formatNumber
										minFractionDigits="3" maxFractionDigits="3"
										value='${guiaentrada.convTN}' type="number" /></td>
								<td style="text-align: right"><fmt:formatNumber
										value='${guiaentrada.peso}' type="number" /></td>
								<c:if test="${guiaentrada.id != 0}">
									<td style="text-align: right"><c:out
											value="${guiaentrada.notaPagamento}" /></td>
									<td><c:out value="${guiaentrada.parcela}" /></td>
								</c:if>

								<c:if test="${guiaentrada.id == 0}">
									<td colspan="2" style="text-align: center"><c:out
											value="${guiaentrada.parcela}" /></td>
								</c:if>

								<c:if test="${guiaentrada.id != 0}">
									<td><a href="edit?id=<c:out value='${guiaentrada.id}' />">Edit</a>
										&nbsp;&nbsp;&nbsp;&nbsp; <a
										href="delete?id=<c:out value='${guiaentrada.id}' />">Delete</a></td>
								</c:if>
								<td><type ="hidden" c:out value="${guiaentrada.id}" /></td>

							</tr>
						</c:forEach>
						<!-- } -->
					</tbody>

				</table>

			</div>
		</div>
	</form>
</body>
</html>