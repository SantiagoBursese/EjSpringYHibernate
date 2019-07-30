<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Video Club Red Link Iinicia</title>
    <link href="<c:url value="/css/bootstrap-theme.min.css" />" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
</head>
<body style="margin: 30px">
	<h1>Mi carrito</h1>
	<hr>
	<div class="container">
		<table class="table">
	    	<thead>
	    		<tr>
	    			<th scope="col">Nombre</th>
	    			<th scope="col">Precio</th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach var="pelicula" items="${carrito.peliculas}">
					<tr>
		    			<td></td>
	    			</tr>
	    		</c:forEach>
	    	</tbody>
	    </table>
	    Precio Total:
	</div>
</body>
</html>
