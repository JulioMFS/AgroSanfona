<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title>Guia de Entrada Management Application</title>
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
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
</style>

<script type="text/javascript">
	function ValidateNum(field) {
		var pattern = /^-?[0-9]+(.[0-9]{1,2})?$/;
		var value = field.value;
		var name = field.name;
		var n = value.indexOf(",");
		if (n >= 0) {
			window.alert(" !!!! commas not allowed\n" + name + ": " + value);
			field.focus();
		}
		if (value.match(pattern) == null) {
			window.alert(" !!!! " + name + ' : format is wrong');
			field.focus();
		}
		return false;
	}
	function jsFunction(value) {
		alert(value);
		var element = document.getElementById("addButton");
		if (typeof (element) != 'undefined' && element != null) {
			var id01 = document.getElementById("id01").value;
			var classificacao = value;
			var text1 = '<a href="facturas?id=' + id01;
			var text2 = '&classificacao=' + classificacao;
			var text3 = '" class="btn btn-success">Adicionar Factura</a>';
			var text = text1 + text2 + text3;

			element.innerHTML = text;
			alert(text);
		}
	}
</script>
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand"> Banco
					Extracto Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">bancoextracto</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${bancoextracto != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${bancoextracto == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${bancoextracto != null}">
                                    Edit Banco Extracto
                                </c:if>
						<c:if test="${bancoextracto == null}">
                                    Adicionar Banco Extracto
                                </c:if>
					</h2>
				</caption>

				<c:if test="${bancoextracto != null}">
					<input type="hidden" name="id" id="id01"
						value="<c:out value='${bancoextracto.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Data_mov</label> <input type="date" min="2018-01-012"
						max="2030-12-31"
						value="<c:out value='${bancoextracto.dataMov}' />"
						class="form-control" name="datamov" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Data_valor</label> <input type="date" min="2018-01-012"
						max="2030-12-31"
						value="<c:out value='${bancoextracto.dataValor}' />"
						class="form-control" name="datavalor" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Descricao</label> <input type="text"
						value="<c:out value='${bancoextracto.descricao}' />"
						class="form-control" name="descricao">
				</fieldset>

				<fieldset class="form-group">
					<label>Montante</label> <input type="text"
						value="<c:out value='${bancoextracto.montante}' />"
						class="form-control" name="montante" onchange="ValidateNum(this);">
				</fieldset>
				<fieldset class="form-group">
					<label>Saldo</label> <input type="text"
						value="<c:out value='${bancoextracto.saldo}' />"
						class="form-control" name="saldo" onchange="ValidateNum(this);">
				</fieldset>
				<fieldset class="form-group">
					<label for="classificacao">Classificacao:&nbsp</label> <select
						name="classificacao" id="classificacao"
						onchange="jsFunction(this.value);">
						<c:forEach items="${classificacao}" var="row">
							<option value="${row.classificacao}"
								${row.classificacao == bancoextracto.classificacao ? 'selected="selected"' : ''}>${row.classificacao}</option>
						</c:forEach>
					</select>
				</fieldset>

				<fieldset class="form-group">
					<label for="descr">
						&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspDescr:&nbsp;</label>
					<select name="descr">
						<c:forEach items="${descr}" var="row">
							<option value="${row.descr}"
								${row.descr == bancoextracto.descr ? 'selected="selected"' : ''}>${row.descr}</option>
						</c:forEach>
					</select>
				</fieldset>


				<fieldset class="form-group">
					<label for="parcela"></label>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspParcela:&nbsp;</label>
					<select name="parcela">
						<c:forEach items="${parcela}" var="row">
							<option value="${row.nome}"
								${row.nome == bancoextracto.parcela ? 'selected="selected"' : ''}>${row.nome}</option>
						</c:forEach>
					</select>
				</fieldset>
				<fieldset class="form-group">
					<label for="seara"></label>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSeara:&nbsp;</label>
					<select name="seara">
						<c:forEach items="${seara}" var="row">
							<option value="${row.seara}"
								${row.seara == bancoextracto.seara ? 'selected="selected"' : ''}>${row.seara}</option>
						</c:forEach>
					</select>
				</fieldset>
				<fieldset class="form-group">
					<label>Banco</label> <input type="text"
						value="<c:out value='${bancoextracto.banco}' />"
						class="form-control" name="banco">
				</fieldset>
				<table>
					<tr>
						<td>
							<button type="submit" class="btn btn-success">Save</button>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><c:if test="${contra != null}">

								<div class="container text-left" id="addButton">
									
								</div>
							</c:if></td>
					</tr>
				</table>
				</form>
			</div>
		</div>
	</div>
</body>

</html>