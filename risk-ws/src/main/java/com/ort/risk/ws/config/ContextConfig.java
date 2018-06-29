package com.ort.risk.ws.config;

import com.mongodb.MongoClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@ComponentScan(value = { "com.ort.risk.ws.repository" })
public class ContextConfig {

	private static final Logger logger = LogManager.getLogger(ContextConfig.class);

	private static String DB = "risk-db";
	private static String HOST = "127.0.0.1";
	private static int PORT = 27017;

	@Bean
	public MongoTemplate getMongoTemplate() {
		MongoTemplate template = null;
		try {
			template = new MongoTemplate(mongoDbFactory());
		} catch (Exception e) {
			logger.fatal("Error while configuring database connection : {}", () -> e.getMessage());
		}
		return template;
	}

	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(HOST, PORT), DB);
	}

}
