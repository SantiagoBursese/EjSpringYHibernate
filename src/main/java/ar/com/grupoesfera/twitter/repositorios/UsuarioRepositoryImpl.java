package ar.com.grupoesfera.twitter.repositorios;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.grupoesfera.twitter.modelo.Usuario;

@Repository("usuarioRepository")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UsuarioRepositoryImpl implements UsuarioRepository {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public Usuario obtener(Long idUsuario) {
        return sessionFactory.getCurrentSession().get(Usuario.class, idUsuario);
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public List<Usuario> buscarAquellosConNombreQueContengan(String nombre) {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Usuario.class)
                .add(Restrictions.like("nombre", "%" + nombre + "%"))
                .list();
    }

    @Override
    public List<Usuario> buscarTodos() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Usuario.class)
                .list();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public void borrar(Usuario usuario) {
        Session currentSession = sessionFactory.getCurrentSession();
        
        usuario = currentSession.get(Usuario.class, usuario.getId());
        currentSession.createNativeQuery("delete from USUARIO_SEGUIDO where ID_SEGUIDO = :id")
		  			  .setParameter("id", usuario.getId())
		  			  .executeUpdate();

		currentSession.delete(usuario);
    }

    @Override
    public Usuario buscarPor(String nombre, String clave) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.like("nombre", nombre))
                .add(Restrictions.like("clave", clave))
                .uniqueResult();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> obtenerSeguidoresDe(Usuario usuario) {
		
		return sessionFactory.getCurrentSession()
					.createQuery("select u from Usuario u where :usuario member of u.seguidos")
					.setParameter("usuario", usuario)
					.getResultList();
	}
}
