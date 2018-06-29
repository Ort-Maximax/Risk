package com.ort.risk.ws.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ort.risk.model.Map;
import com.ort.risk.model.Player;
import com.ort.risk.ws.repository.PlayerRepository;
import com.ort.risk.ws.dto.PlayerDTO;

@RestController
@RequestMapping(value="/players")
public class PlayerController {

	@Resource
	private PlayerRepository playerRepository;
	
	@GetMapping
	public List<Player> getPlayers() {
		return playerRepository.getAll();
	}
	
	@PostMapping
	public boolean createPlayer(@RequestBody PlayerDTO playerDto) {
		if (playerDto == null)
			return false;
		
		Player player = new Player();
		// TODO : transfer data from DTO object to player
		playerRepository.create(player);
		
		return true;
	}
	
	@GetMapping(value="/{id}")
	public Player getPlayer(@PathVariable Long id) {
		return playerRepository.get(id);
	}
	
	@PutMapping(value="/{id}")
	public boolean updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDto) {
		if (playerDto == null)
			return false;
		
		Player player = playerRepository.get(id);
		if (player == null)
			return false;
		else {
			// TODO : transfer data from DTO object to player
			playerRepository.update(player);
			return true;
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public boolean deleteMap(@PathVariable Long id) {
		return false;
	}
	
}
