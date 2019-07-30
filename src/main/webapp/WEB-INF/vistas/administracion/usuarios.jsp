<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administración de usuarios</title>
    <link href="<c:url value="/css/bootstrap-theme.min.css" />" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
</head>
<body style="margin: 30px">
	<div class="container">
		<div class="row">
			<div class="col-md-8">
				<h2 style="margin: 6px 0 0;">Administración de usuarios</h2>
			</div>
			<div class="col-md-4">
				<a class="btn btn-primary" role="button" href="<c:url value="/administracion/usuario/nuevo"/>">+ Agregar nuevo</a>
			</div>
		</div>		
	    <table class="table">
	    	<thead>
	    		<tr>
	    			<th scope="col">#</th>
	    			<th scope="col">Usuario</th>
	    			<th scope="col">Email</th>
	    			<th scope="col">Rol</th>
	    			<th scope="col">Acciones</th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach var="usuario" items="${usuarios}">
					<tr>
		    			<th scope="row">${usuario.id}</th>
		    			<td>${usuario.nombre}</td>
		    			<td>${usuario.email}</td>
		    			<td>${usuario.rol}</td>
		    			<td>
		    				<a class="btn btn-primary" role="button" href="<c:url value="/administracion/usuario/${usuario.id}?accion=EDITAR"/>">Editar</a>
		    				<a class="btn btn-primary" role="button" href="<c:url value="/administracion/usuario/${usuario.id}?accion=BORRAR"/>">Borrar</a>
	    				</td>
	    			</tr>    		
	    		</c:forEach>
	    	</tbody>
	    </table>
	</div>
</body>
</html>