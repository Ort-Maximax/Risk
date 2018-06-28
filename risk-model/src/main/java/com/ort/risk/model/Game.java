package com.ort.risk.model;

import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author tibo
 *  + un petit peu clément wallah
 * Define a map
 */
public class Game {
	
	/**
	 * The map's name
	 */
	private String name;
	
	/**
	 * Url leading to the map's image
	 */
	private String img;
	
	/**
	 * Every zones in the map
	 */
	private List<Zone> zones = new ArrayList<Zone>();

    /**
     * Every regions of every zones of the map
     */
    private List<Region> regions = new ArrayList<Region>();
	/**
	 * Every moves possible
	 */
	private List<Mode> modes = new ArrayList<Mode>();

	/**
	 * All players with their turn order
	 */
	private List<Player> playerList = new ArrayList<Player>();

	/**
	 * Minimum number of reinforcement per tour
	 */
	private int nbMinReinforcement;
	
	/**
	 * Reinforcement bonus divider
	 */
	private int divider;

	private int exMode;

	
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
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @return the zones
	 */
	public List<Zone> getZones() {
		return zones;
	}

	/**
	 * @param zones the zones to set
	 */
	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

    /**
     * @param zone the zones to add
     */
    public void addZone(Zone zone) {
        this.zones.add(zone);
    }


    /**
     * @return the zones
     */
    public List<Region> getRegions() {
        return regions;
    }

    /**
     * @param regions the regions to set
     */
    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    /**
     * @param region the region to add
     */
    public void addRegion(Region region) {
        this.regions.add(region);
    }

	/**
	 * @return the modes
	 */
	public List<Mode> getModes() {
		return modes;
	}

	/**
	 * @param modes the moves to set
	 */
	public void setModes(List<Mode> modes) {
		this.modes = modes;
	}

	/**
	 * @param mode add a mode to the existing list
	 */
	public void addMode(Mode mode) {
		this.modes.add(mode);
	}

	/**
	 * @return the nbMinReinforcement
	 */
	public int getNbMinReinforcement() {
		return nbMinReinforcement;
	}

	/**
	 * @param nbMinReinforcement the nbMinReinforcement to set
	 */
	public void setNbMinReinforcement(int nbMinReinforcement) {
		this.nbMinReinforcement = nbMinReinforcement;
	}

	/**
	 * @return the divider
	 */
	public int getDivider() {
		return divider;
	}

	/**
	 * @param divider the divider to set
	 */
	public void setDivider(int divider) {
		this.divider = divider;
	}

	/**
	 *
	 * @return the player list
	 */
	public List<Player> getPlayerList(){ return this.playerList;}

	public void setPlayerList(List<Player> playerList){
		this.playerList = playerList;
	}

	public void addPlayer(Player player){
		this.playerList.add(player);
	}
	
	/**
	 * Printer of Game
	 * @return String representation of this Game
	 */

	/**
	 * Singleton
	 */
	private Game()
	{}

	private static final Game INSTANCE = new Game();

	public static Game getInstance(){
		return INSTANCE;
	}

	public String toString() {
		return String.format(
				"%s - [ name = %s, img = %s, nbMinReinforcement = %d, divider = %d, modes = [ %s ], zones = [ %s ] ]",
				this.getClass().getName(), this.name, this.img, this.nbMinReinforcement, this.divider,
				modes.stream().map(Mode::toString).collect(Collectors.joining(", ")),
				zones.stream().map(Zone::toString).collect(Collectors.joining(", ")));
	}

	/**
	 * Execution mode, can be 0 : Random, 1 : Console; 2 : GUI
	 */
	public int getExMode() {
		return exMode;
	}

	public void setExMode(int exMode) {
		this.exMode = exMode;
	}

	public void conquerRegion(Player conquerer, Region conqueredRegion){

		//If the region is owned by a player
		if(!conqueredRegion.getIsRogue()){
			for(Player p : this.getPlayerList()){
				List<Region> controlledRegions = p.getControlledRegions();
				if(controlledRegions.contains(conqueredRegion)){
					//The losing player lose control of the region
					p.getControlledRegions().remove(conqueredRegion);
					// The conquerer gain control of the region
					conquerer.addControlledRegion(conqueredRegion);
				}
			}
		} else {
			//else this mus be ORC controlled region
			conquerer.addControlledRegion(conqueredRegion);
			conqueredRegion.setIsRogue(false);
		}

	}

    public List<Region> getRogueRegions(){
        List<Region> allRegions = this.getRegions();

        List<Region> rogueRegions =  allRegions.stream().filter(r -> r.getIsRogue()).collect(Collectors.toList());

        return rogueRegions;

    }


	public Region getRegionByName(String regionName){
		Region matchedRegion = null;
		for(Player p : this.getPlayerList()){
			List<Region> controlledRegions = p.getControlledRegions();
			Predicate<Region> pre = e -> e.getName().equalsIgnoreCase(regionName);
			if(controlledRegions.stream().anyMatch(pre)){

				List<Region> a = controlledRegions.stream().filter(o -> o.getName().equals(regionName)).collect(Collectors.toList());
				matchedRegion =  a.get(0);

			}
		}
		return matchedRegion;
	}

	public Player getOwnerOfRegion(Region region){
		Player matchedPlayer = null;
		for(Player p : this.getPlayerList()){
			List<Region> controlledRegions = p.getControlledRegions();
			if(controlledRegions.contains(region)){
				matchedPlayer = p;

			}
		}
		return matchedPlayer;

	}
}
