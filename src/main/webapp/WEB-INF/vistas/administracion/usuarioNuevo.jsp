<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Alta de usuario</title>
    <link href="<c:url value="/css/bootstrap-theme.min.css" />" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<div class="container">
		<h2>Alta de usuario</h2>
		<form:form method="post" modelAttribute="usuario" action="${contextPath}/administracion/usuario/nuevo">
			<div class="form-group">
				<label for="id">Id</label>
				<form:input id="id" path="id" cssClass="form-control" disabled="true"/>
			</div>
			<div class="form-group">
				<label for="nombre">Nombre</label>
				<form:input id="nombre" path="nombre" cssClass="form-control"/>
			</div>
			<div class="form-group">
				<label for="email">Email</label>
				<form:input id="email" path="email" cssClass="form-control"/>
			</div>
			<div class="form-group">
				<label for="rol">Rol</label>
				<form:input id="rol" path="rol" cssClass="form-control"/>
			</div>
			<div class="form-group">
				<label for="clave">Clave</label>
				<form:input id="clave" path="clave" cssClass="form-control"/>
			</div>
			<a class="btn btn-primary" role="button" href="<c:url value="/administracion/usuarios"/>">Volver</a>
			<form:button class="btn btn-primary">Alta</form:button>			
		</form:form>
	</div>
</body>
</html>