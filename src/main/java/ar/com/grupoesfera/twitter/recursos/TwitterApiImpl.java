package ar.com.grupoesfera.twitter.recursos;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import ar.com.grupoesfera.twitter.modelo.Tweet;
import ar.com.grupoesfera.twitter.modelo.Usuario;
import ar.com.grupoesfera.twitter.servicios.TweetService;

@Service("twitterApi")
public class TwitterApiImpl implements TwitterApi {
	
	@Inject
    private TweetService tweetService;

    @Override
    public Response isAlive() {
        return Response.ok("=)").build();
    }

    @Override
    public Response isAliveAdmin() {
        return Response.ok("=) admin").build();
    }

    @Override
    public Response twitear(Tweet tweet, String idUsuario){
        final Usuario usuarioActual = tweetService.buscarPor(new Long(idUsuario));
        if(usuarioActual != null){
            tweet.setAutor(usuarioActual);
            tweetService.twitear(tweet);
            return Response.ok("ok").build();
        }
        return Response.serverError().entity("Usuario invalido").build();
    }

    @Override
    public Response obtenerTweets(Long idUsuario) {
        final Usuario usuario = tweetService.buscarPor(idUsuario);
        if(usuario != null){
            final List<Tweet> tweets = tweetService.obtenerTweets(usuario);
            return Response.ok(tweets).build();
        }
        return Response.serverError().entity("Usuario invalido").build();
    }

    @Override
    public Response buscarUsuarios(String nombre) {
        final List<Usuario> usuarios = tweetService.buscarUsuarios(nombre);
        return Response.ok(usuarios).build();
    }

    @Override
    public Response obtenerUsuario(Long id, String idUsuarioActual) {
        final Usuario usuario = tweetService.buscarPor(id, idUsuarioActual);
        if(usuario != null){
            return Response.ok(usuario).build();
        }
        return Response.serverError().entity("Usuario invalido").build();
    }

    @Override
    public Response obtenerUsuario(String idUsuarioActual) {
        final Usuario usuario = tweetService.buscarPor(new Long(idUsuarioActual));
        if(usuario != null){
            return Response.ok(usuario).build();
        }
        return Response.serverError().entity("Usuario invalido").build();
    }

    @Override
    public Response obtenerUsuarios() {
        final List<Usuario> usuarios = tweetService.buscarTodosLosUsuarios();
        return Response.ok(usuarios).build();
    }

    @Override
    public Response crear(NuevoUsuarioDTO usuario) {
        Usuario nuevoUsuario = new Usuario(usuario);
        tweetService.crearUsuario(nuevoUsuario);
        return Response.ok(nuevoUsuario).build();
    }

    @Override
    public Response modificar(Usuario usuario) {
        tweetService.modificarUsuario(usuario);
        return Response.ok(usuario).build();
    }

    @Override
    public Response borrar(Long idUsuario) {
        final Usuario usuario = tweetService.buscarPor(idUsuario);
        if(usuario != null){
            tweetService.borrarUsuario(usuario);
            return Response.ok().build();
        }
        return Response.serverError().entity("Usuario invalido").build();
    }

    @Override
    public Response seguir(String idUsuarioActual, String idUsuarioSeguido) {
        tweetService.seguir(idUsuarioActual, idUsuarioSeguido);
        return Response.ok().build();
    }

    @Override
    public Response dejarDeSeguir(String idUsuarioActual, String idUsuarioNoSeguido) {
        tweetService.dejarDeSeguir(idUsuarioActual, idUsuarioNoSeguido);
        return Response.ok().build();
    }

    @Override
    public Response seguidos(Long idUsuario) {
        return Response.ok(tweetService.seguidos(idUsuario)).build();
    }

    @Override
    public Response seguidores(Long idUsuario) {
        return Response.ok(tweetService.seguidores(idUsuario)).build();
    }

    @Override
    public Response timeline(Long idUsuario) {
        final Usuario usuario = tweetService.buscarPor(idUsuario);
        if(usuario != null){
            return Response.ok(tweetService.timeline(usuario)).build();
        }
        return Response.serverError().entity("Usuario invalido").build();
    }

    @Override
    public Response login(LoginDTO login) {
        Usuario usuario = new Usuario();
        usuario.setNombre(login.getNombre());
        usuario.setClave(login.getClave());
        Usuario usuarioValidado = tweetService.validar(usuario);
        if(usuarioValidado != null){
            NewCookie cookie = new NewCookie("usuario", usuarioValidado.getId().toString());
            return Response.ok(usuarioValidado).cookie(cookie).build();
        }
        return Response.serverError().entity("Usuario invalido").build();
    }

    @Override
    public Response initTests() {
        Usuario juan = new Usuario();
        juan.setNombre("@juan");
        juan.setEmail("juan@perez.com");
        juan.setRol("ADMIN");
        juan.setClave("12345678");
        tweetService.crearUsuario(juan);
        return Response.ok().build();
    }

}
