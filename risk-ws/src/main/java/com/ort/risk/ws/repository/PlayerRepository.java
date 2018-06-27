package com.ort.risk.ws.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ort.risk.model.Player;
import com.ort.risk.storage.repository.IRepository;

@Repository
public class PlayerRepository implements IRepository<Player> {

	@Override
	public List<Player> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player get(Long id) {
		// TODO Auto-generated method stub
		return null;
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
