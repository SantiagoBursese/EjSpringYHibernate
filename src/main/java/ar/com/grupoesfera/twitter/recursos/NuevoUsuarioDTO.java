package ar.com.grupoesfera.twitter.recursos;

public class NuevoUsuarioDTO {

    private String nombre;

    private String clave;

    private String email;

    private String rol;
    
    public NuevoUsuarioDTO() {
    	
    }

    public NuevoUsuarioDTO(String nombre, String clave, String email, String rol) {
		super();
		this.nombre = nombre;
		this.clave = clave;
		this.email = email;
		this.rol = rol;
	}

	public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
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
}
