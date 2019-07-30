package ar.com.grupoesfera.twitter.modelo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity	
public class Tweet implements Comparable<Tweet> {

	private static final long serialVersionUID = -4728558817050434324L;

	private String mensaje;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(optional = false, cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name="AUTOR_ID")
	private Usuario autor;
	
	private Date fecha;
	
	public Tweet(Long id) {
		this.setId(id);
	}
	
	public Tweet() {
		this(null);
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	
	public Usuario getAutor() {
		return autor;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(Tweet o) {
		return fecha.compareTo(o.fecha);
	}
}
