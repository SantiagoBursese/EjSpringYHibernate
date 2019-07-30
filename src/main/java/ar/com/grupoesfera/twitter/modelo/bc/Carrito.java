package ar.com.grupoesfera.twitter.modelo.bc;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Carrito {
	
	private int cantidad;
	private List<Pelicula> peliculas;
	private BigDecimal precioTotal;
	
	public Carrito() {
		this.peliculas = new LinkedList<Pelicula>();
		this.cantidad = 0;
		this.precioTotal = BigDecimal.ZERO;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public List<Pelicula> getPeliculas() {
		return peliculas;
	}

	public void agregarPelicula(Pelicula pelicula) {
		peliculas.add(pelicula);
		cantidad++;
		precioTotal = precioTotal.add(pelicula.getPrecio());
	}
	
	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

}
