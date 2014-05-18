package ru.springtest.app.util;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H2Server {
	
	private static final Logger log = LoggerFactory.getLogger(H2Server.class);
	
	private static Server server;
	
	@PostConstruct
	public void startH2Server() {
		if(server != null) {
			log.error("Database already started.");			
		}
		
		try {
			server = Server.createTcpServer().start();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}		
	}
	
	@PreDestroy
	public void stopH2Server() {
		if(server == null) {
			log.error("Database not started.");
		}
		server.stop();
	}
	
}
