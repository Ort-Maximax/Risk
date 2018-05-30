/**
 * 
 */
package com.ort.risk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tibo
 * Define a zone in the map
 */
public class Zone {
	
	/**
	 * Name of the zone
	 */
	private String name;
	
	/**
	 * Bonus given by the zone
	 */
	private int bonus;
	
	/**
	 * Every region in the zone
	 */
	private List<Region> regions = new ArrayList<Region>();

	
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
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the regions
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
	 * @param region add a region to the region list
	 */
	public void addRegion(Region region) {
		this.regions.add(region);
	}
	
	
	/**
	 * Printer of Zone
	 * @return String representation of this Zone
	 */
	public String toString() {
		return String.format(
				"%s - [ name = %s, bonus = %d, regions = [ %s ] ]",
				this.getClass().getName(), this.name, this.bonus,
				regions.stream().map(Region::toString).collect(Collectors.joining(", ")));
	}
	
}
