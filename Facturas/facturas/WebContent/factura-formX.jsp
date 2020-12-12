<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title>Factura Management Application</title>
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
					Factura Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Facturas</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${factura != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${factura == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${factura != null}">
                                    Editar Factura
                                </c:if>
						<c:if test="${factura == null}">
                                    Nova Factura
                                </c:if>
					</h2>
				</caption>

				<c:if test="${factura != null}">
					<input type="hidden" name="no"
						value="<c:out value='${factura.no}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Data</label> <input type="date"
						value="<c:out value='${factura.data}' />" class="form-control"
						name="data" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Factura#</label> <input type="text"
						value="<c:out value='${factura.invno}' />" class="form-control"
						name="invno">
				</fieldset>

				<fieldset class="form-group">
					<label>Item</label> <input type="text"
						value="<c:out value='${factura.item}' />" class="form-control"
						name="item">
				</fieldset>
				<fieldset class="form-group">
					<label>Designacao</label> <input type="text"
						value="<c:out value='${factura.designacao}' />"
						class="form-control" name="designacao" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Quantidade</label> <input type="text"
						value="<c:out value='${factura.quantidade}' />"
						class="form-control" name="quantidade">
				</fieldset>

				<fieldset class="form-group">
					<label>Unidades</label> <input type="text"
						value="<c:out value='${factura.un}' />" class="form-control"
						name="un">
				</fieldset>

				<fieldset class="form-group">
					<label>Preco</label> <input type="text"
						value="<c:out value='${factura.preco}' />" class="form-control"
						name="preco">
				</fieldset>

				<fieldset class="form-group">
					<label>Iva</label> <input type="text"
						value="<c:out value='${factura.iva}' />" class="form-control"
						name="iva">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>

</html>