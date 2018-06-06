package com.ort.risk.model;


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
	 *
	 **/
}
