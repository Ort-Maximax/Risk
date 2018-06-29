package com.ort.risk.ws.dto;

public class PlayerDTO {

	private String name;
    private int order;
    private int nbTroops;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getNbTroops() {
		return nbTroops;
	}
	public void setNbTroops(int nbTroops) {
		this.nbTroops = nbTroops;
	}
		
}
