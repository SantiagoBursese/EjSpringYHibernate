package ar.com.grupoesfera.twitter.repositorios;

import ar.com.grupoesfera.twitter.modelo.Tweet;
import ar.com.grupoesfera.twitter.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("tweetRepository")
@Transactional
public class TweetRepositoryImpl implements TweetRepository {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void guardar(Tweet tweet) {
        sessionFactory.getCurrentSession().save(tweet);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Tweet> obtenerTweets(Usuario usuario) {

        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Tweet.class)
            .add(Restrictions.eq("autor", usuario))
            .list();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Tweet> obtenerUltimosTweets(Integer cantidad, Usuario usuario) {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Tweet.class)
                .add(Restrictions.eq("autor", usuario))
                .addOrder(Order.desc("fecha"))
                .setMaxResults(cantidad)
                .list();
    }

	@Override
	public void borrarTweets(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();
		session.createQuery("delete from Tweet t where autor = :autor")
			.setParameter("autor", usuario)
			.executeUpdate();
	}
}
