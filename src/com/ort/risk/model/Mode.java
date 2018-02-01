package com.ort.risk.model;


/**
 * @author tibo
 * Define a game mode
 */
public class Mode {
	
	/**
	 * Number of player in the game
	 */
	private int nbPlayer;
	
	/**
	 * Number of troops per player at the start of the game
	 */
	private int nbInitTroops;
	
	
	/**
	 * @return the nbPlayer
	 */
	public int getNbPlayer() {
		return nbPlayer;
	}
	
	/**
	 * @param nbPlayer the nbPlayer to set
	 */
	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}
	
	/**
	 * @return the nbInitTroops
	 */
	public int getNbInitTroops() {
		return nbInitTroops;
	}
	
	/**
	 * @param nbInitTroops the nbInitTroops to set
	 */
	public void setNbInitTroops(int nbInitTroops) {
		this.nbInitTroops = nbInitTroops;
	}
	
	
	/**
	 * Printer of Mode
	 * @return String representation of this Mode
	 */
	public String toString() {
		return String.format(
				"%s - [ nbPlayer = %d, nbInitTroop = %d ]",
				this.getClass().getName(), this.nbPlayer, this.nbInitTroops);
	}
	
}
