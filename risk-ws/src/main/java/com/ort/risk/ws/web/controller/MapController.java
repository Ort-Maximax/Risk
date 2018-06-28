package com.ort.risk.ws.web.controller;

import com.ort.risk.model.Map;
import com.ort.risk.storage.RiskProperties;
import com.ort.risk.ws.dto.MapDTO;
import com.ort.risk.ws.repository.MapRepository;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping(value="/maps")
public class MapController {

	private static final Logger logger = LogManager.getLogger(MapController.class);
    RiskProperties properties = RiskProperties.getInstance();

    @Resource
    private MapRepository mapRepository;

    @GetMapping
    public List<Map> getMaps() {
    	logger.info("getMaps() : Getting every maps.");
        return mapRepository.getAll();
    }

    @PostMapping
    public boolean createMap(@RequestBody MapDTO map) {
        if (map == null) {
        	logger.error("createMap() : No data in the request body.");
        	return false;
        }

        //https://raw.githubusercontent.com/EISAWESOME/orbit-360/master/404.html
        File file = new File(properties.getProperty(RiskProperties.SAVED_MAP_PATH_PROP) + map.getName());

        try {
            FileUtils.copyURLToFile(new URL(map.getDataUrl()), file);
        } catch (IOException e) {
            logger.fatal("createMap() : Error while copying the map file -> {}", () -> e.getMessage());
            return false;
        }

        Map mapToInsert = new Map();
        // TODO : transfer data from  object to map

        // mapRepository.create(mapFound);
        logger.info("createMap() : Creating a map.");
        return true;
    }

    @GetMapping(value="/{id}")
    public MapDTO getMap(@PathVariable Long id) {
    	Map map = mapRepository.get(id);
    	MapDTO mapToReturn = null;
    	
    	if (map == null) {
    		logger.info("getMap() : No map found with id {}", id);
    		return null;
    	} else {
    		mapToReturn = new MapDTO();
    		// TODO : transfer data of map to mapToReturn
    		logger.info("getMap() : Getting the map {}", id);
    	}
    	return mapToReturn;
    }


    @PutMapping(value="/{id}")
    public boolean updateMap(@PathVariable Long id, @RequestBody MapDTO map) {
    	if (map == null) {
        	logger.error("updateMap() : No data in the request body.");
        	return false;
        }

        Map mapFound = mapRepository.get(id);
        if (mapFound == null) {
        	logger.info("updateMap() : No map found with id {}", id);
        	return false;
        } else {
        	Map mapToUpdate = new Map();
            // TODO : transfer data from  object to map
            mapRepository.update(mapToUpdate);
            logger.info("updateMap() : Updating the map {}", id);
            return true;
        }
    }

    @DeleteMapping(value="/{id}")
    public boolean deleteMap(@PathVariable Long id) {
        Map mapFound = mapRepository.get(id);
        if (mapFound == null) {
        	logger.info("deleteMap() : No map found with id {}", id);
        	return false;
        }
        else {
            // TODO : transfer data from  object to map
            mapRepository.delete(mapFound);
            logger.info("deleteMap() : Deleting the map {}", id);
            return true;
        }
    }

}
