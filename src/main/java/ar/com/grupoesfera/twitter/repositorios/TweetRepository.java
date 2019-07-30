package ar.com.grupoesfera.twitter.repositorios;

import ar.com.grupoesfera.twitter.modelo.Tweet;
import ar.com.grupoesfera.twitter.modelo.Usuario;

import java.util.List;

public interface TweetRepository {

    void guardar(Tweet tweet);

    List<Tweet> obtenerTweets(Usuario usuario);

    List<Tweet> obtenerUltimosTweets(Integer cantidad, Usuario usuario);

	void borrarTweets(Usuario usuario);
}
