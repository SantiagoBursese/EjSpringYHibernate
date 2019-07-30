package ar.com.grupoesfera.twitter.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.com.grupoesfera.twitter.recursos.NuevoUsuarioDTO;

@Entity
@Table(name = "USUARIO")
@JsonIgnoreProperties({"new"})
public class Usuario {
	
    private static final long serialVersionUID = -4333050855838768124L;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "CLAVE", nullable = false)
    private String clave;

    @Column(name= "EMAIL", nullable = false)
    private String email;

    @Column(name= "ROL", nullable = false)
    private String rol;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="USUARIO_SEGUIDO",
                joinColumns={@JoinColumn(name="ID")},
                inverseJoinColumns={@JoinColumn(name="ID_SEGUIDO")})
    private Set<Usuario> seguidos = new HashSet<>();

    private Boolean seguidoPorElUsuarioActual;

    public Usuario(Long id) {
        this.setId(id);
    }
    
    public Usuario() {
    }

    public Usuario(NuevoUsuarioDTO usuario) {
        nombre = usuario.getNombre();
        clave = usuario.getClave();
        rol = usuario.getRol();
        email = usuario.getEmail();
    }


    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setSeguidos(Set<Usuario> seguidos) {
        this.seguidos = seguidos;
    }

    @JsonIgnore
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Set<Usuario> getSeguidos() {
        return seguidos;
    }

    @Transient
    public void seguir(Usuario seguido) {
        this.seguidos.add(seguido);
    }

    public void seguidoPorElUsuarioActual(){
        seguidoPorElUsuarioActual = Boolean.TRUE;
    }

    public Boolean getSeguidoPorElUsuarioActual() {
        if(seguidoPorElUsuarioActual == null){
            return Boolean.FALSE;
        } else {
            return seguidoPorElUsuarioActual;
        }
    }

    @Transient
	public boolean sigueA(Usuario usuario) {
    	return this.seguidos
		    		.stream()
		    		.filter(seguido -> seguido.equals(usuario))
		    		.findFirst()
		    		.isPresent();
	}

    @Transient
	public void dejarDeSeguir(Usuario usuarioSeguido) {
		this.seguidos.remove(usuarioSeguido);
	}
}
