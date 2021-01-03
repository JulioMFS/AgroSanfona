<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>Banco Extracto Management Application</title>
<%@include file="/common/header.html"%>
</head>
<body>
<h1>Banco Extracto Management Application</h1>
	<form action="list" method="post" name="form1" id="form1">

		<header> </header>
		<br>

		<div class="row">
			<!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

			<div class="container">
				<hr>
				<table style="with: 50%">
					<tr>
						<th>Data Inicial</th>
						<th>Data Final</th>
						<th>Descricao</th>
						<th>Classificacao</th>
						<th>Descr</th>
						<th>Parcela</th>
						<th>Seara</th>
					</tr>
					<tr>
						<td><input type="date" name="data1"
							value='<%=request.getSession().getAttribute("data1")%>'
							min="2010-01-01" max="2030-12-31" /></td>

						<td><input type="date" name="data2"
							value='<%=request.getSession().getAttribute("data2")%>'
							min="2010-01-01" max="2030-12-31" /></td>

						<td><input type="text" name="descricao"
							value='<%=request.getSession().getAttribute("descricao")%>'
							style="width: 120px;" /></td>
						<td><input type="text" name="classificacao"
							value='<%=request.getSession().getAttribute("classificacao")%>'
							style="width: 120px;" /></td>


						<td><input type="text" name="descr"
							value='<%=request.getSession().getAttribute("descr")%>'
							style="width: 120px;" /></td>

						<td><input type="text" name="parcela"
							value='<%=request.getSession().getAttribute("parcela")%>'
							style="width: 120px;" /></td>

						<td><input type="text" name="seara"
							value='<%=request.getSession().getAttribute("seara")%>'
							style="width: 120px;" /></td>
						<td><div class="container text-left">
								<a href="<%=request.getContextPath()%>/new"
									class="btn btn-success">&nbsp;&nbsp;&nbsp;Adicionar
									Extracto &nbsp;&nbsp;&nbsp;</a>
							</div></td>
						<td><input type="submit" value="Pesquisar"
							class="btn btn-success">
						<td>
					</tr>
				</table>
				<hr>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Data Mov.</th>
							<th>Data Valor</th>
							<th>Descricao</th>
							<th>Montante</th>
							<th>Saldo</th>
							<th>Classificacao</th>
							<th>Descr</th>
							<th>Parcela</th>
							<th>Seara</th>
						</tr>
					</thead>
					<tbody>
						<!--   for (Todo todo: todos) {  -->
						<c:forEach var="bancoextracto" items="${bancoextracto}">

							<tr>
								<td><c:out value="${bancoextracto.dataMov}" /></td>
								<td><c:out value="${bancoextracto.dataValor}" /></td>
								<td><c:out value="${bancoextracto.descricao}" /></td>
								<td><div>
										<fmt:formatNumber value='${bancoextracto.montante}'
											type="currency" currencySymbol="" />
									</div></td>
								<td><div>
										<fmt:formatNumber value="${bancoextracto.saldo}"
											type="currency" currencySymbol="" />
									</div></td>
								<td><c:out value="${bancoextracto.classificacao}" /></td>
								<td><c:out value="${bancoextracto.descr}" /></td>
								<td><c:out value="${bancoextracto.parcela}" /></td>
								<td><c:out value="${bancoextracto.seara}" /></td>

								<td><a href="edit?id=<c:out value='${bancoextracto.id}' />">Edit</a>
									&nbsp;&nbsp;&nbsp;&nbsp; <a
									href="delete?id=<c:out value='${bancoextracto.id}' />">Delete</a></td>
								<td><type ="hidden" c:out value="${bancoextracto.id}" /></td>

							</tr>
						</c:forEach>
						<!-- } -->
					</tbody>

				</table>
				<%
					String data1 = "", data2 = "", parcela = "", descricao = "", classificacao = "", descr = "", seara = "";

					if (request.getParameter("data1") == null)
						data1 = "";
					else
						data1 = request.getParameter("data1");
					if (request.getParameter("data2") == null)
						data2 = "";
					else
						data2 = request.getParameter("data2");
					if (request.getParameter("parcela") == null)
						parcela = "";
					else
						parcela = request.getParameter("parcela");
					if (request.getParameter("descricao") == null)
						descricao = "";
					else
						descricao = request.getParameter("descricao");
					if (request.getParameter("classificacao") == null)
						classificacao = "";
					else
						classificacao = request.getParameter("classificacao");
					if (request.getParameter("descr") == null)
						descr = "";
					else
						descr = request.getParameter("descr");
					if (request.getParameter("seara") == null)
						seara = "";
					else
						seara = request.getParameter("seara");

					request.getSession().setAttribute("data1", data1);
					request.getSession().setAttribute("data2", data2);
					request.getSession().setAttribute("descricao", descricao);
					request.getSession().setAttribute("classificacao", classificacao);
					request.getSession().setAttribute("descr", descr);
					request.getSession().setAttribute("parcela", parcela);
					request.getSession().setAttribute("seara", seara);
				%>
			</div>
		</div>
	</form>
</body>
</html>