package ar.com.grupoesfera.twitter.servicios;

import java.util.List;
import java.util.Set;

import ar.com.grupoesfera.twitter.modelo.Tweet;
import ar.com.grupoesfera.twitter.modelo.Usuario;

public interface TweetService {

    Usuario buscarPor(Long idUsuario, String idUsuarioActual);

    Usuario buscarPor(Long idUsuario);

    void twitear(Tweet tweet);

    List<Tweet> obtenerTweets(Usuario usuario);

    void crearUsuario(Usuario juan);

    List<Usuario> buscarUsuarios(String nombre);

    List<Usuario> buscarTodosLosUsuarios();

    void modificarUsuario(Usuario usuario);

    void borrarUsuario(Usuario usuario);

    void seguir(String idUsuarioActual, String idUsuarioSeguido);

    Usuario validar(Usuario usuario);

    void dejarDeSeguir(String idUsuarioActual, String idUsuarioNoSeguido);

    Set<Usuario> seguidos(Long idUsuario);

    Set<Usuario> seguidores(Long idUsuario);

    List<Tweet> timeline(Usuario usuario);

}
