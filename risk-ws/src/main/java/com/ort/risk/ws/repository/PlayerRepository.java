package com.ort.risk.ws.repository;

import java.util.List;

import com.ort.risk.ws.dto.PlayerDTO;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ort.risk.model.Player;
import com.ort.risk.storage.repository.IRepository;

import javax.annotation.Resource;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class PlayerRepository implements IRepository<Player> {

	@Resource
	MongoOperations mongoOps;

	@Override
	public List<Player> getAll() {
		return mongoOps.findAll(Player.class);
	}

	@Override
	public Player get(Long id) {
		return mongoOps.findOne(new Query(where("_id").is(id)), Player.class);
	}

	@Override
	public void create(Player recording) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Player recording) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Player recording) {
		// TODO Auto-generated method stub
		
	}

}
