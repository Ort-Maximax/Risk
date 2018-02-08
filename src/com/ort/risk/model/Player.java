/**
 *
 */
package com.ort.risk.model;

import com.ort.risk.game.actions.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author tibo
 * Define a player
 */
public class Player {

    private String name;
    private int order;
    private int nbTroops;
    private boolean isHuman;

    private List<Region> controlledRegions = new ArrayList<Region>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void setNbTroops(int nbTroops) {
        this.nbTroops = nbTroops;
    }

    public void changeNbTroops(int nbTroops) {
        this.nbTroops += nbTroops;
    }

    public int getNbTroops() {
        return this.nbTroops;
    }

    public List<Region> getControlledRegions() {
        return this.controlledRegions;
    }

    public void setControlledRegions(List<Region> controlledRegions) {
        this.controlledRegions = controlledRegions;
    }

    public void addControlledRegion(Region region) {
        this.controlledRegions.add(region);
    }

    public void removeControlledRegion(Region region) {
        this.controlledRegions.remove(region);

    }

    public void setIsHuman(boolean isHuman){
        this.isHuman = isHuman;
    }

    public boolean getIsHuman(){
        return this.isHuman;
    }

    public Player() {
    }

    public Player(String name, boolean isHuman, int order, int nbTroops) {
        this.name = name;
        this.isHuman = isHuman;
        this.order = order;
        this.nbTroops = nbTroops;
    }

    /**
     * @author CS
     * Utils functions tied to the player entity
     */

    /**
     * @return the list of region from where the player can initiate a war
     */
    public List<Region> getWarRegions() {
        List<Region> warRegions = new ArrayList<Region>();
        for (Region region : this.getControlledRegions()) {
            if (region.isWarRegion(this) != null) {
                warRegions.add(region.isWarRegion(this));
            }
        }
        return warRegions;
    }


    /**
     * @return the list of region from where the player can initiate a reinforcement
     */
    public List<Region> getReinforcementRegions() {
        List<Region> warRegions = new ArrayList<Region>();
        for (Region region : this.getControlledRegions()) {
            if (region.isReinforcementRegion(this) != null) {
                warRegions.add(region.isReinforcementRegion(this));
            }
        }
        return warRegions;
    }
}
