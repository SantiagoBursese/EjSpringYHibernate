package ar.com.grupoesfera.twitter.aceptacion;

import static com.mashape.unirest.http.Unirest.get;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;

public class TwitterApiIT extends TestDeAceptacion{
	
    @Test
    public void smoke() throws Exception {
        // when
        final HttpResponse<String> httpResponse = get(urlBase + "/isAlive").asString();
        // then
        assertThat(httpResponse.getStatus()).isEqualTo(200);
    }

    @Test
    public void smokeAdmin() throws Exception {
        HttpURLConnection connection = invocarAPISegura("/admin/isAlive", "GET", "admin", "1234");
        assertThat(connection.getResponseCode()).isEqualTo(200);
    }

    @Test
    public void alTuitearDeberiaGuardarElTweetParaElUsuario() throws Exception {
        // given
        String body = "{\"mensaje\":\"hola seba\"}";
        // when
        HttpURLConnection connection = invocarAPI("/tweets", "POST", "usuario=1", body);
        final int responseCode = connection.getResponseCode();
        // then
        assertThat(responseCode).isEqualTo(200);
    }

    @Test
    public void alTuitearConUsuarioInvalidoDeberiaDarError() throws Exception {
        // given
        String body = "{\"mensaje\":\"hola seba\"}";
        // when
        HttpURLConnection connection = invocarAPI("/tweets", "POST", "usuario=791", body);
        final int responseCode = connection.getResponseCode();
        // then
        assertThat(responseCode).isEqualTo(500);
    }

    @Test
    public void obtenerLosTuitsDeUnUsuarioDeberiaDevolverSoloLosTweetsDeEseUsuario() throws Exception{
        // given
        String body = "{\"mensaje\":\"obtener tuits del usuario\"}";
        HttpURLConnection tuitear = invocarAPI("/tweets", "POST", "usuario=1", body);
        tuitear.getResponseCode();
        // when
        HttpURLConnection tuits = invocarAPI("/tweets/1", "GET");
        String json = obtenerRespuesta(tuits);
        // then
        assertThat(json).contains("obtener tuits del usuario");
    }

    @Test
    public void obtenerLosTuitsDeUnUsuarioInvalidoDeberiaDarError() throws Exception{
        // when
        HttpURLConnection connection = invocarAPI("/tweets/791", "GET");
        final int responseCode = connection.getResponseCode();
        // then
        assertThat(responseCode).isEqualTo(500);
    }

    @Test
    public void buscarUsuariosDeberiaDevolverUsuariosQueCoincidanPorNombre() throws Exception {
        // when
        HttpURLConnection connection = invocarAPI("/usuariosPorNombre/ua", "GET");
        String json = obtenerRespuesta(connection);
        // then
        assertThat(json).contains("@juan");
    }

    @Test
    public void buscarUsuarioPorIdDeberiaDevolverElUsuarioCorrespondiente() throws Exception{
        // when
        HttpURLConnection connection = invocarAPI("/usuarios/1", "GET", "usuario=1");
        String json = obtenerRespuesta(connection);
        // then
        assertThat(json).contains("@juan");
    }

    @Test
    public void obtenerUsuariosDeberiaDevolverTodosLosUsuariosExistentes() throws Exception {
        // when
        final HttpResponse<List> httpResponse = get(urlBase + "/usuarios").asObject(List.class);
        final List usuarios = httpResponse.getBody();
        // then
        assertThat(usuarios.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void crearUsuarioDeberiaAsignarIdDeUsuario() throws Exception {
        // given
        String body = "{\"nombre\":\"@ema\", \"clave\":\"1234\", \"email\":\"ema@paez.com\", \"rol\":\"USER\"}";
        // when
        HttpURLConnection connection = invocarAPI("/usuarios", "POST", null, body);
        String json = obtenerRespuesta(connection);
        // then
        Map<String,Object> result = new ObjectMapper().readValue(json, HashMap.class);
        assertThat(result.get("id")).isNotNull();
    }

    @Test
    public void modificarUsuarioDeberiaModificarLosDatosDelMismo() throws Exception {
        // given
        String id = crearUsuarioNuevo("@maxi", "maxi@pino.com");
        // modifico algun dato del usuario
        String body = "{\"id\":" + id + ", \"nombre\":\"@maxi\", \"clave\":\"1234\", \"email\":\"maxi@arbol.com\", \"rol\":\"USER\"}";
        // when
        HttpURLConnection connection = invocarAPI("/usuarios", "PUT", null, body);
        connection.getResponseCode();
        // then
        connection = invocarAPI("/usuarios/" + id, "GET");
        String json = obtenerRespuesta(connection);
        assertThat(json).contains("maxi@arbol.com");
    }

    @Test
    public void alBorrarUnUsuarioNoLoDeberiaPoderRecuperar() throws Exception {
        // given
        String id = crearUsuarioNuevo("@ana", "ana@maria.com");
        // when
        HttpURLConnection connection = invocarAPI("/usuarios/" + id, "DELETE");
        connection.getResponseCode();
        // then
        connection = invocarAPI("/usuarios/" + id, "GET");
        final int responseCode = connection.getResponseCode();
        assertThat(responseCode).isEqualTo(500);
    }

    @Test
    public void alSeguirUnUsuarioElMismoDebeIndicarQueEsSeguidoPorElUsuarioActual() throws Exception{
        // given
        String idSeguido = crearUsuarioNuevo("@seguido", "seguido@mail.com");
        String idSeguidor = crearUsuarioNuevo("@seguidor", "seguidor@mail.com");
        // when
        HttpURLConnection connection = invocarAPI("/seguir", "POST", "usuario=" + idSeguidor, idSeguido);
        connection.getResponseCode();
        // then
        connection = invocarAPI("/usuarios/" + idSeguido, "GET", "usuario=" + idSeguidor);
        String json = obtenerRespuesta(connection);
        // busco al seguido y verifico que la propiedad 'seguidoPorElUsuarioActual' sea TRUE
        Map<String,Object> result = new ObjectMapper().readValue(json, HashMap.class);
        assertThat(result.get("seguidoPorElUsuarioActual")).isEqualTo(Boolean.TRUE);
    }

    @Test
    public void alDejarDeSeguirUnUsuarioElMismoDebeIndicarQueNoEsSeguidoPorElUsuarioActual() throws Exception{
        // given
        String idSeguido = crearUsuarioNuevo("@seguido2", "seguido@mail.com");
        String idSeguidor = crearUsuarioNuevo("@seguidor2", "seguidor@mail.com");
        HttpURLConnection connection = invocarAPI("/seguir", "POST", "usuario=" + idSeguidor, idSeguido);
        connection.getResponseCode();
        // when
        connection = invocarAPI("/dejarDeSeguir", "POST", "usuario=" + idSeguidor, idSeguido);
        connection.getResponseCode();
        // then
        connection = invocarAPI("/usuarios/" + idSeguido, "GET", "usuario=" + idSeguidor);
        String json = obtenerRespuesta(connection);
        // busco al seguido y verifico que la propiedad 'seguidoPorElUsuarioActual' sea TRUE
        Map<String,Object> result = new ObjectMapper().readValue(json, HashMap.class);
        assertThat(result.get("seguidoPorElUsuarioActual")).isEqualTo(Boolean.FALSE);
    }

    @Test
    public void alSeguirUnUsuarioElServicioSeguidosDeberiaDevolverAlUsuarioSeguido() throws Exception{
        // given
        String idSeguido = crearUsuarioNuevo("@seguido3", "seguido@mail.com");
        String idSeguidor = crearUsuarioNuevo("@seguidor3", "seguidor@mail.com");
        // when
        HttpURLConnection connection = invocarAPI("/seguir", "POST", "usuario=" + idSeguidor, idSeguido);
        connection.getResponseCode();
        // then
        connection = invocarAPI("/usuarios/" + idSeguidor + "/seguidos", "GET");
        String json = obtenerRespuesta(connection);
        assertThat(json).contains("@seguido3");
    }

    @Test
    public void alSeguirUnUsuarioElServicioSeguidoresDeberiaDevolverAlUsuarioSeguidor() throws Exception{
        // given
        String idSeguido = crearUsuarioNuevo("@seguido4", "seguido@mail.com");
        String idSeguidor = crearUsuarioNuevo("@seguidor4", "seguidor@mail.com");
        // when
        HttpURLConnection connection = invocarAPI("/seguir", "POST", "usuario=" + idSeguidor, idSeguido);
        connection.getResponseCode();
        // then
        connection = invocarAPI("/usuarios/" + idSeguido + "/seguidores", "GET");
        String json = obtenerRespuesta(connection);
        assertThat(json).contains("@seguidor4");
    }

    @Test
    public void alPedirElTimelineSoloDeboVerTuitsDeUsuariosSeguidos() throws Exception {
        HttpURLConnection connection = null;

        String idSeguidor = crearUsuarioNuevo("@seguidor5", "seguidor@mail.com");

        String idSeguido1 = crearUsuarioNuevo("@seguido51", "seguido@mail.com");
        connection = invocarAPI("/seguir", "POST", "usuario=" + idSeguidor, idSeguido1);
        connection.getResponseCode();

        connection = invocarAPI("/tweets", "POST", "usuario=" + idSeguido1, "{\"mensaje\":\"tuit usuario 1\"}");
        connection.getResponseCode();

        String idSeguido2 = crearUsuarioNuevo("@seguido52", "seguido@mail.com");
        connection = invocarAPI("/seguir", "POST", "usuario=" + idSeguidor, idSeguido2);
        connection.getResponseCode();

        connection = invocarAPI("/tweets", "POST", "usuario=" + idSeguido2, "{\"mensaje\":\"tuit usuario 2\"}");
        connection.getResponseCode();

        String idSeguido3 = crearUsuarioNuevo("@seguido53", "seguido@mail.com");
        connection = invocarAPI("/seguir", "POST", "usuario=" + idSeguidor, idSeguido3);
        connection.getResponseCode();

        connection = invocarAPI("/tweets", "POST", "usuario=" + idSeguido3, "{\"mensaje\":\"tuit usuario 3\"}");
        connection.getResponseCode();

        String idNoSeguido = crearUsuarioNuevo("@noSeguido", "seguido@mail.com");
        // A este usuario no lo sigo

        connection = invocarAPI("/tweets", "POST", "usuario=" + idNoSeguido, "{\"mensaje\":\"tuit usuario no seguido\"}");
        connection.getResponseCode();

        // when
        connection = invocarAPI("/timeline/" + idSeguidor, "GET");
        String json = obtenerRespuesta(connection);

        assertThat(json).doesNotContain("tuit usuario no seguido");
        assertThat(json).contains("tuit usuario 1");
        assertThat(json).contains("tuit usuario 2");
        assertThat(json).contains("tuit usuario 3");
    }

    @Test
    public void alHacerLoginConUsuarioYClaveCorrectaDeberiaRetornarElUsuarioCorrespondiente() throws Exception {
        // given
        String body = "{\"nombre\":\"@juan\", \"clave\":\"12345678\"}";
        // when
        HttpURLConnection connection = invocarAPI("/login", "POST", null, body);
        String json = obtenerRespuesta(connection);
        // then
        assertThat(json).contains("@juan");
    }

    @Test
    public void alHacerLoginConUsuarioYClaveIncorrectaDeberiaDarError() throws Exception {
        // given
        String body = "{\"nombre\":\"@juan\", \"clave\":\"5\"}";
        // when
        HttpURLConnection connection = invocarAPI("/login", "POST", null, body);
        final int responseCode = connection.getResponseCode();
        // then
        assertThat(responseCode).isEqualTo(500);
    }

    @Test
    public void alPedirElUsuarioLogueadoDeberiaDevolverJuan() throws Exception {
        // when
        HttpURLConnection connection = invocarAPI("/usuarioActual", "GET", "usuario=1");
        String json = obtenerRespuesta(connection);
        // then
        assertThat(json).contains("@juan");
    }

    private String obtenerRespuesta(HttpURLConnection connection) throws IOException {
        return new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
    }

    private String crearUsuarioNuevo(String nombre, String mail) throws Exception {
        String body = "{\"nombre\":\"" + nombre + "\", \"clave\":\"1234\", \"email\":\"" + mail + "\", \"rol\":\"USER\"}";
        HttpURLConnection connection = invocarAPI("/usuarios", "POST", null, body);
        String json = obtenerRespuesta(connection);
        Map<String,Object> result = new ObjectMapper().readValue(json, HashMap.class);
        return result.get("id").toString();
    }
}
