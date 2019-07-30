package ar.com.grupoesfera.twitter.servicios;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.com.grupoesfera.twitter.SpringTest;
import ar.com.grupoesfera.twitter.modelo.Tweet;
import ar.com.grupoesfera.twitter.modelo.Usuario;
import ar.com.grupoesfera.twitter.repositorios.TweetRepository;

public class TweetServiceTest extends SpringTest {

    @Inject
    private TweetService tweetService;

    @Inject
    private TweetRepository tweetRepository;

    private Usuario juan;
    private Usuario pedro;
    private Usuario pepe;

    @Before
    @Transactional @Rollback
    public void setUp(){
        crearUsuarios();
    }

    @Test @Transactional @Rollback
    public void alTuitearDeberiaGuardarElTweetParaElUsuario(){

        tweetService.twitear(crearTwitDe(juan));
        tweetService.twitear(crearTwitDe(pedro));

        validarQueHayaUnUnicoTuitDe(juan);
    }

    @Test @Transactional @Rollback
    public void obtenerLosTuitsDeUnUsuarioDeberiaDevolverSoloLosTweetsDeEseUsuario(){

        tweetService.twitear(crearTwitDe(juan));
        tweetService.twitear(crearTwitDe(juan));

        final List<Tweet> tweetsDeJuan = tweetService.obtenerTweets(juan);

        assertThat(tweetsDeJuan).hasSize(2);
    }
    
    @Test @Transactional @Rollback
    public void alSeguirAUnUsuarioDeberiaAparecerEntreLosSeguidos() {
    	tweetService.seguir(juan.getId().toString(), pedro.getId().toString());
    	
    	final Usuario juanBuscado = session().get(Usuario.class, juan.getId());
    	assertThat(juanBuscado.getSeguidos().contains(pedro));
    }
    
    @Test @Transactional @Rollback
    public void alSeguirAUnUsuarioDeberiaAparecerEnLaListaDeSeguidores() {
    	tweetService.seguir(juan.getId().toString(), pedro.getId().toString());
    	tweetService.seguir(juan.getId().toString(), pepe.getId().toString());
    	Set <Usuario> seguidosDeJuan = tweetService.seguidos(juan.getId());
    	assertThat(seguidosDeJuan.contains(pedro));
    	assertThat(seguidosDeJuan.contains(pepe));
    	
    }
    
    @Test @Transactional @Rollback
    public void juanYPepeSiguenAPedroYPedroTieneDosSeguidores() {
    	tweetService.seguir(juan.getId().toString(), pedro.getId().toString());
    	tweetService.seguir(pepe.getId().toString(), pedro.getId().toString());
    	Set <Usuario> seguidoresPedro = tweetService.seguidores(pedro.getId());
    	assertEquals(2, seguidoresPedro.size());	
    }
    
    @Test @Transactional @Rollback
    public void juanSigueAPedroYLoDejaDeSeguir() {
    	tweetService.seguir(juan.getId().toString(), pedro.getId().toString());
    	tweetService.dejarDeSeguir(juan.getId().toString(), pedro.getId().toString());
    	assertThat(!juan.getSeguidos().contains(pedro));	
    }
    
    @Test @Transactional @Rollback
    public void devuelveElTimelineDelUsuarioJuan() {
    	tweetService.seguir(juan.getId().toString(), pedro.getId().toString());
    	tweetService.seguir(juan.getId().toString(), pepe.getId().toString());
    	tweetService.twitear(crearTwitDe(juan));
    	tweetService.twitear(crearTwitDe(juan));
    	tweetService.twitear(crearTwitDe(pepe));
    	tweetService.twitear(crearTwitDe(pepe));
    	tweetService.twitear(crearTwitDe(pepe));
    	tweetService.twitear(crearTwitDe(pepe));
    	tweetService.twitear(crearTwitDe(pepe));
    	tweetService.twitear(crearTwitDe(pedro));
    	tweetService.twitear(crearTwitDe(pedro));
    	tweetService.twitear(crearTwitDe(pedro));
    	tweetService.twitear(crearTwitDe(pedro));
    	tweetService.twitear(crearTwitDe(pedro));
    	tweetService.twitear(crearTwitDe(pedro));
    	List<Tweet> timeline = tweetService.timeline(juan);
    	assertEquals(8, timeline.size());
    }
    

    private void crearUsuarios() {
        juan = new Usuario();
        juan.setNombre("Juan");
        juan.setEmail("juan@perez.com");
        juan.setRol("ADMIN");
        juan.setClave("12345678");

        session().save(juan);

        pedro = new Usuario();
        pedro.setNombre("Pedro");
        pedro.setEmail("pedro@perez.com");
        pedro.setRol("ADMIN");
        pedro.setClave("12345678");

        session().save(pedro);
        
        pepe = new Usuario();
        pepe.setNombre("Pepe");
        pepe.setEmail("pepe@perez.com");
        pepe.setRol("ADMIN");
        pepe.setClave("2345678");

        session().save(pepe);
        
    }

    private Tweet crearTwitDe(Usuario usuario) {
        Tweet tweet = new Tweet();
        tweet.setMensaje("Este es el mensaje");
        tweet.setAutor(usuario);
        return tweet;
    }

    private void validarQueHayaUnUnicoTuitDe(Usuario juan) {
        assertThat(tweetRepository.obtenerTweets(juan)).hasSize(1);
    }
}
