/**
 * 
 */
package com.ort.risk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tibo
 * Define a region in a zone
 */
public class Region {

	/**
	 * Name of the region
	 */
	private String name;
	
	/**
	 * Bonus given by the region
	 */
	private Integer bonus;
	
	/**
	 * Every frontier of the region
	 */
	private List<Frontier> frontiers = new ArrayList<Frontier>();

	private boolean isOccupied = false;
	private boolean isRogue = false;

	private int deployedTroops = 0;

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bonus
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the frontiers
	 */
	public List<Frontier> getFrontiers() {
		return frontiers;
	}

	/**
	 * @param frontiers the frontiers to set
	 */
	public void setFrontiers(List<Frontier> frontiers) {
		this.frontiers = frontiers;
	}


	/**
	 * @param frontier add a frontier to the frontiers list
	 */
	public void addFrontier(Frontier frontier) {
		this.frontiers.add(frontier);
	}

	public boolean getIsOccupied(){
		return this.isOccupied;
	}

	public void setIsOccupied(boolean state){
		this.isOccupied = state;
	}

	public boolean getIsRogue(){
		return this.isRogue;
	}

	public void setIsRogue(boolean state){
		this.isRogue = state;
	}


	public int getDeployedTroops(){
		return this.deployedTroops;
	}

	public void setDeployedTroops(int troops){
		this.deployedTroops = troops;
	}

	public void changeDeployedTroops(int troops){
		this.deployedTroops += troops;
	}
	
	/**
	 * Printer of Region
	 * @return String representation of this Frontier
	 */
	public String toString() {
		return String.format(
				"%s - [ name = %s, bonus =%d, frontiers = [ %s ] ]",
				this.getClass().getName(), this.name, this.bonus,
				frontiers.stream().map(Frontier::toString).collect(Collectors.joining(", ")));
	}


	/**
	 * @author CS
	 * Utils functions tied to a region
	 */

	/**
	 * if the region has 2+ troops on it, and a frontier valid for war move
	 * @param player
	 * @return the region or null
	 */
	public Region isWarRegion(Player player){
		if(this.getDeployedTroops() >= 2){
			for(Frontier frontier : this.getFrontiers()){
				if(frontier.isWarFrontier(player)){
					return this;
				}
			}
			return null;
		}
		return null;
	}

	public Region isReinforcementRegion(Player player){
		if(this.getDeployedTroops() >= 2){
			for(Frontier frontier : this.getFrontiers()){
				if(frontier.isReinforcementFrontier(player)){
					return this;
				}
			}
			return null;
		}
		return null;
	}

	/**
	 *
	 * @param player
	 * @return the list of region the player is able to engage war on
	 */
	public List<Region> getAllWarTargets(Player player){
		List<Region> allWarTargets = new ArrayList<Region>();
		for(Frontier frontier : this.getFrontiers()){
			if(frontier.getWarTarget(player) != null){
				allWarTargets.add(frontier.getWarTarget(player));
			}
		}

		return allWarTargets;
	}

	public List<Region> getAllReinforcementTargets(Player player){
		List<Region> allReinfProviders = new ArrayList<Region>();
		for(Frontier frontier : this.getFrontiers()){
			if(frontier.getReinforcementTarget(player) != null){
				allReinfProviders.add(frontier.getReinforcementTarget(player));
			}
		}

		return allReinfProviders;
	}



	public List<Move> getFrontierWarMoves(String endRegionName){
		List<Move> warMoves = new ArrayList<Move>();
		for(Frontier frontier : this.getFrontiers()){
			if(frontier.getRegionEndName().equalsIgnoreCase(endRegionName)){
				for(Move move : frontier.getMoves()){
					if (!move.getName().equalsIgnoreCase("Reinforcement")){
						warMoves.add(move);
					}
				}
			}

		}
		return warMoves;
	}


	public Move getFrontierReinfMove(String endRegionName){
		for(Frontier frontier : this.getFrontiers()){
			if(frontier.getRegionEndName().equalsIgnoreCase(endRegionName)){
				for(Move move : frontier.getMoves()){
					if (move.getName().equalsIgnoreCase("Reinforcement")){
						return move;
					}
				}
			}

		}
		return null;

	}




}
