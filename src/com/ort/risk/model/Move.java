package com.ort.risk.model;

import com.ort.risk.game.actions.ReinforcementAction;
import com.ort.risk.game.actions.WarAction;

/**
 * @author tibo
 * Define a possible move between regions
 */
public class Move {
	
	/**
	 * The name of the move
	 */
	private String name;
	
	
	/**
	 * name getter
	 * @return name of the move
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Printer of Move
	 * @return String representation of this Move
	 */
	public String toString() {
		return String.format(
				"%s - [ name = %s ]",
				this.getClass().getName(), this.name);
	}

	/**
	 * @author CS
	 * Utils functions tied modes, and the execution of their respective action
	 */

	public Object[] execute(Region startRegion, Region endRegion, Player atkPlayer, int nbAttack, int nbDef){
		Object[] ret = null;
		switch(this.getName()){
			case "Assault":
				//System.out.println(atkPlayer.getName() + " utilise 'Assaut'");
				ret = WarAction.Assault(startRegion, atkPlayer, endRegion, nbAttack, nbDef);
				//System.out.println("Mais cela n'a aucun effet...\n");
				break;
			default:
				System.out.println("Y'a erreur\n");
		}
		return ret;
	}

	public void execute(Region startRegion, Region endRegion, int nbTroops){
		switch(this.getName()){
			case "Reinforcement":
				ReinforcementAction.execute(startRegion, endRegion, nbTroops);
				break;
			default:
				System.out.println("Y'a erreur\n");
		}
	}
}
