package ar.com.grupoesfera.twitter.aceptacion;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class TestDeAceptacion {

	protected static SqlSessionFactory sessionFactory;
	protected static ScriptRunner scriptRunner;
	
    protected String port = System.getProperty("servlet.port", "8080");
    protected String urlBase = "http://localhost:" + port + "/sitio/api/rest";

    // esto es necesario para poder obtener los resultados de la api serializados como objetos usando Unirest
    static{
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    
    @BeforeClass
    public static void cargarDatos() throws UnirestException {
    	
    	try {
    		
    		InputStream configuracion = Resources.getResourceAsStream("test-myBatisConfiguration.xml");
    		sessionFactory = new SqlSessionFactoryBuilder().build(configuracion);
    		scriptRunner = new ScriptRunner(sessionFactory.openSession().getConnection());
    		
    	}catch (Exception e) {
			throw new AssertionError("No se pudo crear la conexion a la base de datos", e);
		}
    }
    
    @AfterClass
    public static void limpiarConexiones() {
    	scriptRunner.closeConnection();
    }
    
    @Before
    public void limpiarDatos() {
    	
    	try {

    		Reader script = Resources.getResourceAsReader("borrarDatos.sql");
    		scriptRunner.runScript(script);
    		
    		script = Resources.getResourceAsReader("crearUsuarioDePrueba.sql");
    		scriptRunner.runScript(script);
    		
    	}catch (Exception e) {
    		throw new AssertionError("No se pudo borrar los datos de la base de datos", e);
		}
    }
    
    protected HttpURLConnection invocarAPI(String path, String httpMethod, String cookie, String body) throws Exception {
        URL url = new URL(urlBase + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(httpMethod);
        if(cookie != null){
            connection.setRequestProperty("Cookie", cookie);
        }
        if(body != null){
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();
        }
        connection.connect();
        return connection;
    }

    protected HttpURLConnection invocarAPI(String path, String httpMethod, String cookie) throws Exception {
        return invocarAPI(path, httpMethod, cookie, null);
    }

    protected HttpURLConnection invocarAPI(String path, String httpMethod) throws Exception {
        return invocarAPI(path, httpMethod, null, null);
    }

    protected HttpURLConnection invocarAPISegura(String path, String httpMethod, String usuario, String password) throws Exception {
        URL url = new URL(urlBase + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(httpMethod);
        String encoded = Base64.getEncoder().encodeToString((usuario + ":" + password).getBytes(StandardCharsets.UTF_8));  //Java 8
        connection.setRequestProperty("Authorization", "Basic "+ encoded);
        connection.connect();
        return connection;
    }

}
