package ar.com.grupoesfera.twitter.aplicacion;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class Inicializador {
	
	@Inject
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void insertarDatos() {
		
		Session session = sessionFactory.openSession();
		Transaction transaccion = session.beginTransaction();
		session.createNativeQuery("INSERT INTO USUARIO (ID, NOMBRE, EMAIL, ROL, CLAVE) VALUES(1, '@dspizzirri', 'damian.spizzirri@grupoesfera.com.ar', 'ADMIN', '12345678')").executeUpdate();
		session.createNativeQuery("INSERT INTO USUARIO (ID, NOMBRE, EMAIL, ROL, CLAVE) VALUES(2, '@sismael', 'sebastian.ismael@grupoesfera.com.ar', 'ADMIN', '123456789')").executeUpdate();
		session.createNativeQuery("INSERT INTO USUARIO (ID, NOMBRE, EMAIL, ROL, CLAVE) VALUES(3, '@mgore', 'marcelo.gore@grupoesfera.com.ar', 'ADMIN', '1234567890')").executeUpdate();
		
		// TWEETS DE DAMI
		session.createNativeQuery("INSERT INTO TWEET (ID, AUTOR_ID, MENSAJE, FECHA) VALUES(1, 1, '¡Bienvenidas y bienvenidos al Módulo de Angular!', '2019-06-23')").executeUpdate();
		session.createNativeQuery("INSERT INTO TWEET (ID, AUTOR_ID, MENSAJE, FECHA) VALUES(2, 1, '¡Angular Rocks!', '2019-06-27')").executeUpdate();
		session.createNativeQuery("INSERT INTO TWEET (ID, AUTOR_ID, MENSAJE, FECHA) VALUES(3, 1, 'Vamos a consumir la apir rest del modulo de Spring', '2019-06-24')").executeUpdate();
		
		// TWEETS DE SEBA
		session.createNativeQuery("INSERT INTO TWEET (ID, AUTOR_ID, MENSAJE, FECHA) VALUES(4, 2, 'Hoy empezamos con Spring MVC', '2019-06-20')").executeUpdate();
		session.createNativeQuery("INSERT INTO TWEET (ID, AUTOR_ID, MENSAJE, FECHA) VALUES(5, 2, 'IoC: No nos llames, nosotros te llamamos', '2019-06-21')").executeUpdate();
		session.createNativeQuery("INSERT INTO TWEET (ID, AUTOR_ID, MENSAJE, FECHA) VALUES(6, 3, 'Hoy vamos a hacer servicios rest con CXF', '2019-06-22')").executeUpdate();
		
		//DAMI SIGUE A SEBA
		session.createNativeQuery("INSERT INTO USUARIO_SEGUIDO (ID, ID_SEGUIDO) VALUES(1, 2)").executeUpdate();
		
		transaccion.commit();
		session.close();
	}
	
	@PreDestroy
	public void destruir() {
		sessionFactory.close();
	}
}
