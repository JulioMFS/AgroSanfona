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
</head>

<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="https://www.javaguides.net" class="navbar-brand">
					guiaentrada Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">guiaentradas</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${guiaentrada != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${guiaentrada == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${guiaentrada != null}">
                                    Edit guiaentrada
                                </c:if>
						<c:if test="${guiaentrada == null}">
                                    Adicionar Guia de Entrada
                                </c:if>
					</h2>
				</caption>

				<c:if test="${guiaentrada != null}">
					<input type="hidden" name="id"
						value="<c:out value='${guiaentrada.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Guia N.</label>

					<c:if test="${guiaentrada == null}">
						<input type="text" value="<c:out value='20 97/2000' />"
							class="form-control" name="guiaNo" required="required">
					</c:if>
					<c:if test="${guiaentrada != null}">
						<input type="text" value="<c:out value='${guiaentrada.guiaNo}' />"
							class="form-control" name="guiaNo" required="required">
					</c:if>


				</fieldset>
				<fieldset class="form-group">
					<label>Data</label> <input type="date" min="2018-01-012"
						max="2030-12-31" value="<c:out value='${guiaentrada.data}' />"
						class="form-control" name="data" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Hora</label> <input type="time" min="07:00" max="22:00"
						value="<c:out value='${guiaentrada.hora}' />" class="form-control"
						name="hora">
				</fieldset>

				<fieldset class="form-group">
					<label>Matricula</label> <input type="text"
						value="<c:out value='${guiaentrada.matricula}' />"
						class="form-control" name="matricula">
				</fieldset>
				<fieldset class="form-group">
					<label>Peso Esp.</label> <input type="text"
						value="<c:out value='${guiaentrada.pesoEsp}' />"
						class="form-control" name="pesoEsp">
				</fieldset>
				<fieldset class="form-group">
					<label>Peso Verde</label> <input type="text"
						value="<c:out value='${guiaentrada.pesoVerde}' />"
						class="form-control" name="pesoVerde">
				</fieldset>

				<fieldset class="form-group">
					<label>Humidade</label> <input type="text"
						value="<c:out value='${guiaentrada.humidade}' />"
						class="form-control" name="humidade">
				</fieldset>
				<fieldset class="form-group">
					<label>Conv./TN</label> <input type="text"
						value="<c:out value='${guiaentrada.convTN}' />"
						class="form-control" name="convTN">
				</fieldset>
				<fieldset class="form-group">
					<label>Artigo</label> <input type="text"
						value="<c:out value='${guiaentrada.artigo}' />"
						class="form-control" name="artigo">
				</fieldset>
				<fieldset class="form-group">
					<label>Descricao</label> <input type="text"
						value="<c:out value='${guiaentrada.descricao}' />"
						class="form-control" name="descricao">
				</fieldset>
				<fieldset class="form-group">
					<label>Peso</label> <input type="text"
						value="<c:out value='${guiaentrada.peso}' />" class="form-control"
						name="peso">
				</fieldset>
				<fieldset class="form-group">
					<label>Nota Pagamento</label> <input type="text"
						value="<c:out value='${guiaentrada.notaPagamento}' />"
						class="form-control" name="notaPagamento">
				</fieldset>

				<fieldset class="form-group">
					<label>Parcela</label> <input type="text"
						value="<c:out value='${guiaentrada.parcela}' />"
						class="form-control" name="parcela">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>

</html>