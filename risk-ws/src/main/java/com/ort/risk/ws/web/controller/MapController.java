package com.ort.risk.ws.web.controller;

import com.ort.risk.model.Map;
import com.ort.risk.storage.RiskProperties;
import com.ort.risk.ws.dto.MapDTO;
import com.ort.risk.ws.repository.MapRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping(value="/maps")
public class MapController {

    RiskProperties properties = RiskProperties.getInstance();

    @Resource
    private MapRepository mapRepository;

    @GetMapping
    public List<Map> getMaps() {
        return mapRepository.getAll();
    }

    @PostMapping
    public boolean createMap(@RequestBody MapDTO map) {
        if (map == null)
            return false;

        //https://raw.githubusercontent.com/EISAWESOME/orbit-360/master/404.html

        File file = new File(properties.getProperty(RiskProperties.SAVED_MAP_PATH_PROP) + map.getName());


        try {
            FileUtils.copyURLToFile(new URL(map.getDataUrl()), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map mapToInsert = new Map();
        // TODO : transfer data from  object to map

        // mapRepository.create(mapFound);
        return true;
    }

    @GetMapping(value="/{id}")
    public Map getMap(@PathVariable Long id) {
        return mapRepository.get(id);
    }


    @PutMapping(value="/{id}")
    public boolean updateMap(@PathVariable Long id, @RequestBody Map map) {
        if (map == null)
            return false;

        Map mapFound = mapRepository.get(id);
        if (mapFound == null)
            return false;
        else {
            // TODO : transfer data from  object to map
            mapRepository.update(map);
            return true;
        }
    }

    @DeleteMapping(value="/{id}")
    public boolean deleteMap(@PathVariable Long id) {

        Map mapFound = mapRepository.get(id);
        if (mapFound == null)
            return false;
        else {
            // TODO : transfer data from  object to map
            mapRepository.delete(mapFound);
            return true;
        }
    }

}
