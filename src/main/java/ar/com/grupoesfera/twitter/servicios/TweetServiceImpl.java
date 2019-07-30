package ar.com.grupoesfera.twitter.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.grupoesfera.twitter.modelo.Tweet;
import ar.com.grupoesfera.twitter.modelo.Usuario;
import ar.com.grupoesfera.twitter.repositorios.TweetRepository;
import ar.com.grupoesfera.twitter.repositorios.TweetRepositoryImpl;
import ar.com.grupoesfera.twitter.repositorios.UsuarioRepository;

@Service("tweetService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class TweetServiceImpl implements TweetService {
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private TweetRepository tweetRepository;

    @Override
    public Usuario buscarPor(Long idUsuario, String idUsuarioActual) {
    	Usuario usuario = usuarioRepository.obtener(idUsuario);
    	return usuario;
    }

    @Override
    public Usuario buscarPor(Long idUsuario) {
    	Usuario usuario = usuarioRepository.obtener(idUsuario);
    	return usuario;
    }

    @Override
    public void twitear(Tweet tweet) {
    	tweet.setFecha(new Date());
    	tweetRepository.guardar(tweet);
    }

    @Override
    public List<Tweet> obtenerTweets(Usuario usuario) {
    	return tweetRepository.obtenerTweets(usuario);
    }

    @Override
    public void crearUsuario(Usuario usuario) {
    	usuarioRepository.guardar(usuario);
    }

    @Override
    public List<Usuario> buscarUsuarios(String nombre) {
        return usuarioRepository.buscarAquellosConNombreQueContengan(nombre);
    }

    @Override
    public List<Usuario> buscarTodosLosUsuarios() {
        return usuarioRepository.buscarTodos();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void modificarUsuario(Usuario usuario) {
    	Usuario usuarioParaModificar = buscarPor(usuario.getId());
    	usuarioParaModificar.setEmail(usuario.getEmail());
    	usuarioParaModificar.setRol(usuario.getRol());
    	usuarioRepository.modificar(usuarioParaModificar);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void borrarUsuario(Usuario usuario) {
    	usuarioDejaDeSeguir(usuario, usuario.getSeguidos());
    	seguidoresDejanDeSeguirlo(usuario, usuario.getSeguidos());
    	borrarTweets(usuario);
    	usuarioRepository.borrar(usuario);
    }

    private void borrarTweets(Usuario usuario) {
		tweetRepository.borrarTweets(usuario);
	}

	private void usuarioDejaDeSeguir(Usuario usuario, Set<Usuario> seguidos) {
		for(Usuario user: seguidos) {
			dejarDeSeguir(usuario.getId().toString(), user.getId().toString());
		}
	}

	private void seguidoresDejanDeSeguirlo(Usuario usuario, Set<Usuario> seguidos) {
		for(Usuario user: seguidos) {
			dejarDeSeguir(user.getId().toString(), usuario.getId().toString());
		}
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void seguir(String idUsuarioActual, String idUsuarioSeguido) {
    	Usuario usuarioActual = usuarioRepository.obtener(Long.valueOf(idUsuarioActual));
    	Usuario usuarioSeguido = usuarioRepository.obtener(Long.valueOf(idUsuarioSeguido));
    	usuarioActual.seguir(usuarioSeguido);
    	usuarioRepository.modificar(usuarioActual);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void dejarDeSeguir(String idUsuarioActual, String idUsuarioNoSeguido) {
    	Usuario usuarioActual = usuarioRepository.obtener(Long.valueOf(idUsuarioActual));
    	Usuario usuarioSeguido = usuarioRepository.obtener(Long.valueOf(idUsuarioNoSeguido));
    	usuarioActual.dejarDeSeguir(usuarioSeguido);
    	usuarioRepository.modificar(usuarioActual);
    }

    @Override
    public Set<Usuario> seguidos(Long idUsuario) {
    	Usuario usuario = usuarioRepository.obtener(idUsuario);
    	return usuario.getSeguidos();
    }

    @Override
    public Set<Usuario> seguidores(Long idUsuario) {
    	Usuario usuario = usuarioRepository.obtener(idUsuario);
    	List <Usuario> seguidores = usuarioRepository.obtenerSeguidoresDe(usuario);
    	Set <Usuario> seguidoresDe = new HashSet<Usuario>();
    	seguidoresDe.addAll(seguidores);
    	return seguidoresDe;
    }

    @Override
    public List<Tweet> timeline(Usuario usuario) {
        Set <Usuario> seguidos = seguidos(usuario.getId());
        List <Tweet> tweetsTimeline = new ArrayList <Tweet>();
        for(Usuario user: seguidos) {
        	tweetsTimeline.addAll(tweetRepository.obtenerUltimosTweets(3, user));
        }
        tweetsTimeline.addAll(tweetRepository.obtenerTweets(usuario));
        return tweetsTimeline;
    }

    @Override
    public Usuario validar(Usuario usuario) {
    	Usuario usuarioValidado = usuarioRepository.buscarPor(usuario.getNombre(), usuario.getClave());
    	if(usuarioValidado != null) {
    		return usuarioValidado;
    	}
    	return null;
    }
}
