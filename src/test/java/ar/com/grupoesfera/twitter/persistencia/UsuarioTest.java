package ar.com.grupoesfera.twitter.persistencia;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.com.grupoesfera.twitter.SpringTest;
import ar.com.grupoesfera.twitter.modelo.Usuario;

public class UsuarioTest extends SpringTest {

    @Test @Transactional @Rollback
    public void guardarUsuario(){
        Usuario juan = new Usuario();
        juan.setNombre("Juan");
        juan.setEmail("juan@perez.com");
        juan.setRol("ADMIN");
        juan.setClave("12345678");

        session().save(juan);

        Usuario buscado = session().get(Usuario.class, juan.getId());
        assertThat(buscado).isNotNull();
    }

    @SuppressWarnings("unchecked")
	@Test @Transactional @Rollback
    public void seguir(){
        Usuario juan = new Usuario();
        juan.setNombre("Juan");
        juan.setEmail("juan@perez.com");
        juan.setRol("ADMIN");
        juan.setClave("12345678");
        session().save(juan);

        Usuario ema = new Usuario();
        ema.setNombre("@ema");
        ema.setEmail("ema@ema.com");
        ema.setRol("ADMIN");
        ema.setClave("12345678");
        session().save(ema);

        ema.seguir(juan);
        session().update(ema);
        session().update(juan);
       // session().getTransaction().commit();
        
        final Usuario usuarioEma = session().get(Usuario.class, ema.getId());
        assertThat(usuarioEma.getSeguidos()).hasSize(1);
        
        // Quien sigue a juan
        List<Usuario> usuarios = session()
        			.createQuery("select u from Usuario u where :usuario member of u.seguidos")
        			.setParameter("usuario", juan)
        			.getResultList();
        
        assertThat(usuarios).extracting("nombre").containsExactly("@ema");
    }
}
