package ar.com.grupoesfera.twitter.recursos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ar.com.grupoesfera.twitter.modelo.Tweet;
import ar.com.grupoesfera.twitter.modelo.Usuario;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.web.bind.annotation.RequestBody;

@Path("/rest")
@CrossOriginResourceSharing(
        allowAllOrigins = true
)
public interface TwitterApi {

    @GET
    @Path("/isAlive")
    Response isAlive();
    
    @GET
    @Path("/admin/isAlive")
    Response isAliveAdmin();

    // Crear un tweet. Crea un tweet a nombre del usuario actual.
    @POST
    @Path("/tweets")
    @Consumes({MediaType.APPLICATION_JSON})
    Response twitear(@RequestBody Tweet tweet, @CookieParam("usuario") String idUsuario);

    // Ver los tweets de un usuario (especificando el usuario por id).
    @GET
    @Path("/tweets/{idUsuario}")
    @Produces({MediaType.APPLICATION_JSON})
    Response obtenerTweets(@PathParam("idUsuario")Long idUsuario);

    // Buscar usuarios por nombre. Idealmente estaría bueno poder conseguir una lista de usuarios cuyo nombre
    // matchee con cierto string. Asi podemos implementar un autocomplete
    @GET
    @Path("/usuariosPorNombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    Response buscarUsuarios(@PathParam("nombre")String nombre);

    // Buscar usuario por id
    @GET
    @Path("/usuarios/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response obtenerUsuario(@PathParam("id") Long id, @CookieParam("usuario") String idUsuarioActual);

    // usuario logueado
    @GET
    @Path("/usuarioActual")
    @Produces(MediaType.APPLICATION_JSON)
    Response obtenerUsuario(@CookieParam("usuario") String idUsuarioActual);

    // Buscar todos los usuarios
    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    Response obtenerUsuarios();

    // Crear nuevo usuario
    @POST
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    Response crear(@RequestBody NuevoUsuarioDTO usuario);

    // Actualizar usuario, no permite modificar nombre ni clave
    @PUT
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    Response modificar(@RequestBody Usuario usuario);

    // Borrar usuario
    @DELETE
    @Path("/usuarios/{idUsuario}")
    Response borrar(@PathParam("idUsuario")Long idUsuario);

    // Seguir a un usuario. La idea sería indicar que el usuario actual epieza a seguir a un usuario dado.
    @POST
    @Path("/seguir")
    @Consumes({MediaType.APPLICATION_JSON})
    Response seguir(@CookieParam("usuario") String idUsuarioActual, @RequestBody String iUsuarioSeguido);

    // Dejar de seguir a un usuario. La idea sería indicar que el usuario actual deja de seguir a un usuario dado
    @POST
    @Path("/dejarDeSeguir")
    @Consumes({MediaType.APPLICATION_JSON})
    Response dejarDeSeguir(@CookieParam("usuario") String idUsuarioActual, @RequestBody String idUsuarioNoSeguido);

    // usuarios seguidos por el usuario indicado
    @GET
    @Path("/usuarios/{idUsuario}/seguidos")
    @Produces(MediaType.APPLICATION_JSON)
    Response seguidos(@PathParam("idUsuario") Long idUsuario);

    // usuarios seguidores de el usuario indicado
    @GET
    @Path("/usuarios/{idUsuario}/seguidores")
    @Produces(MediaType.APPLICATION_JSON)
    Response seguidores(@PathParam("idUsuario") Long idUsuario);

    // los twits (limitado a 50 resultados) de los usuario a quien el usuario acutal sigue, ordenados por fecha (el mas nuevo primero)
    @GET
    @Path("/timeline/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    Response timeline(@PathParam("idUsuario") Long idUsuario);

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    Response login(@RequestBody LoginDTO login);

    @GET
    @Path("/initTests")
    Response initTests();
}
