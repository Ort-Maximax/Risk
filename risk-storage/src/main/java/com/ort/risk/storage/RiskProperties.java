package com.ort.risk.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author tibo
 *
 *         Application's properties --> config.properties
 */
public class RiskProperties {

	private static final Logger logger = LogManager.getLogger(RiskProperties.class);
	
	private static Properties prop = new Properties();
	private static RiskProperties INSTANCE = null;

	public static final String SAVED_MAP_PATH_PROP = "savedMapPath";
	public static final String CURRENT_MAP_PATH_PROP = "currentMapPath";

	private RiskProperties() {
		FileInputStream input = null;
		try {
			input = new FileInputStream("resources/config.properties");
			logger.info("Loading the properties' file.");
			prop.load(input);
		} catch (IOException e) {
			logger.fatal("Error while accessing to the properties' file : {}", () -> e.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.fatal("Error while closing the properties file's stream : {}", () -> e.getMessage());
				}
			}

		}
	}

	public static RiskProperties getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RiskProperties();
			logger.info("Initating RiskProperties Singleton.");
		}
		return INSTANCE;
	}

	public String getProperty(String key) {
		logger.info("Getting property : {}", key);
		return prop.getProperty(key);
	}

}
