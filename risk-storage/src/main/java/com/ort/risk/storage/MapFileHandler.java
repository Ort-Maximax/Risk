package com.ort.risk.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author tibo
 *
 * XML files manager
 */
public class MapFileHandler {

	RiskProperties properties = RiskProperties.getInstance();
	public static final String CURRENT_MAP_NAME = "played_map.xml";
			
	public void moveMapFileToCurrent(File map) {
		try {
			Files.copy(
					map.toPath(),
					Paths.get(properties.getProperty(RiskProperties.CURRENT_MAP_PATH_PROP) + CURRENT_MAP_NAME),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println(String.format(
					"%s.moveMapFileToCurrent() - %s",
					getClass().getName(),
					e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public File getCurrentMapFile() {
		return new File(RiskProperties.getInstance().getProperty(RiskProperties.CURRENT_MAP_PATH_PROP) + CURRENT_MAP_NAME);
	}
	
	public void saveMap(File map) {
		try {
			Files.copy(
					map.toPath(),
					Paths.get(properties.getProperty(RiskProperties.SAVED_MAP_PATH_PROP) + map.getName()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println(String.format(
					"%s.saveMap() - %s",
					getClass().getName(),
					e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public List<File> getSavedMapFiles() {
		List<File> savedMapFiles = new ArrayList<File>();
		try {
			savedMapFiles = Files.walk(Paths.get(properties.getProperty(RiskProperties.SAVED_MAP_PATH_PROP)))
			        .filter(Files::isRegularFile)
			        .map(Path::toFile)
			        .collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println(String.format(
					"%s.getSavedMapFiles() - %s [%s]",
					getClass().getName(),
					e.getMessage(),
					properties.getProperty(RiskProperties.SAVED_MAP_PATH_PROP)));
			e.printStackTrace();
		}
		return savedMapFiles;
	}
	
}
