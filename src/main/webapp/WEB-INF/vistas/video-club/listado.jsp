<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Video Club Red Link Iinicia</title>
    <link href="<c:url value="/css/bootstrap-theme.min.css" />" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
</head>
<body style="margin: 30px">
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<div class="container">
		<h1>Películas disponibles</h1>
		<form:form method="get" action="${contextPath}/video-club/buscar-por-nombre">
			<div class="form-inline">
				<label class="sr-only" for="nombre">Nombre</label>
				<input type="text" name="nombre" class="form-control mb-4 mr-sm-4" id="nombre" placeholder="Buscar película...">
				<button type="submit" class="btn btn-primary mb-2">Buscar</button>	
			</div>	
		</form:form>
	    <table class="table">
	    	<thead>
	    		<tr>
	    			<th scope="col">#</th>
	    			<th scope="col">Nombre</th>
	    			<th scope="col">Duración</th>
	    			<th scope="col">Género</th>
	    			<th scope="col">Año</th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach var="pelicula" items="${peliculas}">
					<tr>
		    			<th scope="row">${pelicula.id}</th>
		    			<td>${pelicula.nombre}</td>
		    			<td>${pelicula.duracion}</td>
		    			<td>${pelicula.genero}</td>
		    			<td>${pelicula.anio}</td>
	    				<td>
	    					<form:form method="post" modelAttribute="peliculaId" action="${contextPath}/video-club/agregar-pelicula">
	    						<input id="${pelicula.id}" type="hidden" name="peliculaId" value="${pelicula.id}"/>
								<button type="submit" class="btn btn-primary mb-2">+ agregar</button>	
							</form:form>
	    				</td>    		
	    			</tr>
	    		</c:forEach>
	    	</tbody>
	    </table>
		<div class="row">
			<div class="col-sm">
				<a class="btn btn-primary" role="button" href="<c:url value="/video-club/pelicula-con-mayor-rating"/>">Película con mayor rating</a>
			</div>
		</div>
		<div class="row">
			<div class="col-sm">
				<c:if test="${not empty carrito}">
    				Tenés ${carrito.cantidad} películas agregadas a tu <a href="<c:url value="/video-club/mi-carrito"/>">carrito</a> 
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
