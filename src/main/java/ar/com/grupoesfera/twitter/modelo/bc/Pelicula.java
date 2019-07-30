package ar.com.grupoesfera.twitter.modelo.bc;

import java.math.BigDecimal;

public class Pelicula {
	
	public enum Genero { ACCION, TERROR, DRAMA, AVENTURA, COMEDIA }
	
	private Long id;
	private String nombre;
	private String duracion;
	private Genero genero;
	private String anio;
	private Long ratingDeUsuarios;
	private BigDecimal precio;
	
	public static Pelicula nueva() {
		return new Pelicula();
	}
	
	public Pelicula conId(Long id) {
		setId(id);
		return this;
	}
	public Pelicula conNombre(String nombre) {
		setNombre(nombre);
		return this;
	}
	
	public Pelicula conDuracion(String duracion) {
		setDuracion(duracion);
		return this;
	}
	
	public Pelicula conGenero(Genero genero) {
		setGenero(genero);
		return this;
	}
	
	public Pelicula conAnio(String anio) {
		setAnio(anio);
		return this;
	}
	
	public Pelicula conRatingDeUsuarios(Long ratingDeUsuarios) {
		setRatingDeUsuarios(ratingDeUsuarios);
		return this;
	}
	
	public Pelicula conPrecion(BigDecimal precio) {
		setPrecio(precio);
		return this;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	
	public String getDuracion() {
		return duracion;
	}
	
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public Genero getGenero() {
		return genero;
	}
	
	public void setAnio(String anio) {
		this.anio = anio;
	}
	
	public String getAnio() {
		return anio;
	}
	
	public void setRatingDeUsuarios(Long ratingDeUsuarios) {
		this.ratingDeUsuarios = ratingDeUsuarios;
	}
	
	public Long getRatingDeUsuarios() {
		return ratingDeUsuarios;
	}
	
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
	public BigDecimal getPrecio() {
		return precio;
	}
}
