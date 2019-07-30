package ar.com.grupoesfera.twitter.repositorios;

import java.util.List;
import java.util.Set;

import ar.com.grupoesfera.twitter.modelo.Usuario;

public interface UsuarioRepository {

    Usuario obtener(Long idUsuario);

    void guardar(Usuario usuario);

    List<Usuario> buscarAquellosConNombreQueContengan(String nombre);

    List<Usuario> buscarTodos();

    void modificar(Usuario usuario);

    void borrar(Usuario usuario);

    Usuario buscarPor(String nombre, String clave);

	List<Usuario> obtenerSeguidoresDe(Usuario usuario);

}
