package com.ort.risk.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author tibo
 *
 * Application's properties --> config.properties
 */
public class RiskProperties {
	
	private static Properties prop = new Properties();
	
	public static final String SAVED_MAP_PATH_PROP = "savedMapPath";
	public static final String CURRENT_MAP_PATH_PROP = "currentMapPath";

	private RiskProperties() {
		FileInputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public static RiskProperties getInstance() {
		return new RiskProperties();
	}
	
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
}
