package project2;

import java.util.ArrayList;

/**
 * This class inherits all methods and objects that are non-private from ArrayList. This class
 * functions like an ArrayList, but only holds {@link Meteorite} objects. It has 3 additional 
 * methods apart from ArrayList.
 * 
 * @author Jonason Wu
 * @version 10/5/2020
 */
class MeteoriteList extends ArrayList<Meteorite> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initializes the superclass and get an ArrayList that holds {@link Meteorite} objects.
	 */
	public MeteoriteList () {
	}
	
	/**
	 * This method finds all the {@link Meteorite} objects that have a mass between the 
	 * {@code mass} with a 
	 * range of {@code delta}. If it is a meteorite that matches the range for the mass, then it is 
	 * added into the MeteoriteLinkedList that will be returned for the method. 
	 * 
	 * @param mass the mass of the meteorite to find.
	 * @param delta the allowed range of error away from the mass.
	 * @return A {@link MeteoriteLinkedList}. A list of all meteorites having mass within the 
	 * range of {@code mass} plus or minus {@code delta} will be returned. If list is empty 
	 * (no matches found), null is returned.
	 * @throws IllegalArgumentException if {@code mass} or {@code delta} is below 0
	 */
	public MeteoriteLinkedList getByMass (int mass, int delta) throws IllegalArgumentException {
		if (mass < 0 || delta < 0) {
			throw new IllegalArgumentException("Mass cannot be less than 0.");
		}
		
		//LinkedList to hold the values of the matching masses
		MeteoriteLinkedList same = new MeteoriteLinkedList();
		//Lower range of comparing
		int lowRange = mass - delta;
		//Upper range of comparing
		int highRange = mass + delta;
		//Make lower range 0 if negative
		if (lowRange < 0) {
			lowRange = 0;
		}
		
		//Is list empty
		boolean empty = true;
		for (Meteorite meteor : this) {
			int meteorMass = meteor.getMass();
			if ((meteorMass >= (lowRange))
					&& (meteorMass <= (highRange))) {
				//Automatically sorted in the add method
				same.add(meteor);
				empty = false;					
			}
		}
		if (empty) {
			return null;
		}
		return same;
	}
	
	/**
	 * This method takes the {@link MeteoriteList} and finds the meteorite that landed closest 
	 * to {@code loc}.
	 * 
	 * @param loc The {@link Location} to base on when finding the closest meteorite.
	 * @return the {@link Meteorite} that landed closest to {@code loc}. Returns null if size of 
	 * MeteoriteList is 0.
	 * @throws IllegalArgumentException {@code loc} is null.
	 */
	public Meteorite getByLocation (Location loc) throws IllegalArgumentException {
		if (loc == null) {
			throw new IllegalArgumentException("Location is undefined.");
		}
		if (this.size() == 0) {
			return null;
		}
		
		//Initialize dummy distance
		double distance = -1.0;
		Meteorite closest = null;
		//Iterate through all values in MeteoriteList
		for (int i = 0; i < this.size(); i++) {
			Location location = this.get(i).getLocation();
			//If location is null, skip the comparison
			if (location == null) {
				continue;
			} else if (distance < 0) {
				//The first time a Meteorite has a location
				distance = loc.getDistance(this.get(i).getLocation());
				closest = this.get(i);
			} 
			else {
				//Compare distances
				double compDist = loc.getDistance(this.get(i).getLocation());
				if (distance > compDist) {
					distance = compDist;
					closest = this.get(i);
				}
			}
		}
		return closest;
	}
	
	/**
	 * This method finds all the {@link Meteorite} objects that landed at the {@code year} 
	 * specified and adds them into the MeteoriteLinkedList that will be returned for the method.
	 * 
	 * @param year the landing year of the meteorites to find.
	 * @return a {@link MeteoriteLinkedList} that has all the meteorites that landed on {@code year}
	 * @throws IllegalArgumentException year is less than 0
	 */
	public MeteoriteLinkedList getByYear (int year) throws IllegalArgumentException {
		if (year < 0) {
			throw new IllegalArgumentException("The year is invalid. " 
					+ "Need positive integer for year.");
		}
		//LinkedList to hold the matching meteors
		MeteoriteLinkedList same = new MeteoriteLinkedList();
		
		//Is list empty
		boolean empty = true;
		for (Meteorite meteor : this) {
			int meteorYear = meteor.getYear();
			if (meteorYear == 0) {
				continue;
			}
			else if (year == meteorYear){
				same.add(meteor);
				empty = false;
			}
		}
		if (empty) {
			return null;
		}
		return same;
	}
}
