package ar.com.grupoesfera.twitter.aplicacion;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.h2.tools.Server;
import org.springframework.stereotype.Component;

@Component
public class ServerH2 {
	
	private Server server;
	
	@PostConstruct
	public void construir() throws SQLException {
		
		server = Server.createTcpServer("-tcpPort", "9123", "-tcpAllowOthers").start();
	}
	
	@PreDestroy
	public void destruir() {
		server.stop();
	}
}
