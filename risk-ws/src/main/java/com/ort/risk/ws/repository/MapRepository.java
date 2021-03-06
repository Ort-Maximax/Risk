package com.ort.risk.ws.repository;

import com.ort.risk.model.Map;
import com.ort.risk.storage.repository.IRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MapRepository implements IRepository<Map> {

    @Resource
    MongoOperations mongoOps;

    @Override
    public List<Map> getAll() {
    	List<Map> maps = mongoOps.findAll(Map.class);
        return maps;
    }

    @Override
    public Map get(Long id) {
        return mongoOps.findOne(new Query(where("_id").is(id)), Map.class);
    }

    @Override
    public void create(Map recording) {
    	mongoOps.insert(recording);
    }

    @Override
    public void delete(Map recording) {
    	mongoOps.remove(recording);
    }

    @Override
    public void update(Map recording) {
    	mongoOps.save(recording);
    }
}
