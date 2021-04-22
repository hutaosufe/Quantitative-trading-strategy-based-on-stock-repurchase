package project2;

/**
 * This class creates a Meteorite object that has a {@code name}, {@code id}, {@code mass}, 
 * {@code year}, and {@link Location}.
 * A Meteorite object must have a name and id to be considered a Meteorite, but mass, year,
 * and location do not need to be identified to create the Meteorite object. {@code mass} is 
 * defaulted at -1, {@code year} is defaulted at 0, and {@code loc} is defaulted as null.
 * 
 * @author Jonason Wu
 * @version 10/5/2020
 */
class Meteorite implements Comparable<Meteorite> {
	private String name;
	private int id;
	private int mass = -1;
	private int year = 0;
	private Location loc = null;
	/**
	 * This constructor creates a Meteorite object given a valid name and id. {@code name} 
	 * should not be an empty string and {@code id} should be a positive integer.
	 * 
	 * @param name the name of the Meteorite.
	 * @param id the id of the Meteorite.
	 * @throws IllegalArgumentException if name is a empty string or id is not positive.
	 */
	public Meteorite (String name, int id) throws IllegalArgumentException {
		if (name != "" && id > 0) {
			this.setName(name);
			this.setId(id);
		}
		else {
			throw new IllegalArgumentException("Either invalid name or invalid id. "
					+ "Valid names are non-empty strings. "
					+ "Valid id's are positive integers."
					+ "\nname: " + name + "\nid: "+ id);
		}
	}
	
	/**
	 * Get the name of the Meteorite object.
	 * 
	 * @return the name of the Meteorite object that calls this method.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the Meteorite object. Only used inside the constructor to instantiate name.
	 * 
	 * @param name the name of the Meteorite object.
	 */
	private void setName(String name) {
		this.name = name;
	}
	/**
	 * Get the id of the Meteorite object.
	 * 
	 * @return the id of the Meteorite object that calls this method.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Set the id of the Meteorite object. Only used inside the constructor to instantiate id.
	 * 
	 * @param id the id of the Meteorite object.
	 */
	private void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Set the mass of the Meteorite object. {@code mass} should be greater than 0.
	 * 
	 * @param mass the mass of the Meteorite.
	 * @throws IllegalArgumentException mass is not positive.
	 */
	public void setMass(int mass) throws IllegalArgumentException {
		if (mass > 0) {
			this.mass = mass;
		}
		else {
			throw new IllegalArgumentException("A valid mass is a positive integer."
					+ "\nProvided mass: " + mass);
		}
	}
	
	/**
	 * Get the mass of the Meteorite object. 
	 * 
	 * @return the mass of the Meteorite object that calls this method. 
	 * Returns -1 if mass was not defined for the Meteorite.
	 */
	public int getMass() {
		return this.mass;
	}
	
	/**
	 * Set the year the Meteorite landed on Earth. {@code year} should be between 0 and 2020.
	 * 
	 * @param year the year the Meteorite landed on Earth.
	 * @throws IllegalArgumentException the year is not a positive number less than 2020
	 */
	public void setYear(int year) throws IllegalArgumentException {
		if (year < 2020 && year > 0) {
			this.year = year;
		}
		else {
			throw new IllegalArgumentException("A valid year is a positive integer."
					+ "less than the current year (2020).\nProvided year: " + year);
		}
	}
	
	/**
	 * Get the year the Meteorite landed on Earth.
	 * 
	 * @return the year the Meteorite landed on Earth. Returns 0 if year was not defined for 
	 * the Meteorite.
	 */
	public int getYear() {
		return this.year;
	}
	
	/**
	 * Set the location the Meteorite landed. See {@link Location}
	 * 
	 * @param loc A Location object; usually has loc.latitude and loc.longitude
	 */
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	
	/**
	 * Get the location of the Meteorite object.
	 * 
	 * @return the location the Meteorite landed as a {@link Location} object. Returns null 
	 * if location was not defined or defined as null.
	 */
	public Location getLocation() {
		return this.loc;
	}
	
	/**
	 * Compares the Meteorites according to name first, disregarding upper or lower case. 
	 * If they are equal, then compare them by their id's.
	 * 
	 * @param o Meteorite object to be compared
	 * @return negative number if the Meteorite calling this method is considered "less" than 
	 * the Meteorite that is compared. 0 if the Meteorite has the same name and id. Positive if 
	 * the Meteorite calling this method is considered "greater" than the Meteorite that is 
	 * compared.
	 */
	@Override
	public int compareTo(Meteorite o) {
		int relation = this.name.compareToIgnoreCase(o.name);
		if (relation == 0) {
			relation = Integer.toString(this.id).compareToIgnoreCase(Integer.toString(o.id));
		}
		return relation;
	}
	
	/**
	 * Finds out whether the Meteorite object that calls this method has the same {@code name} 
	 * and {@code id} as {@code obj}.
	 * 
	 * @return true if the Meteorites have same {@code name} and {@code id}, false if the 
	 * Meteorites have different
	 * {@code name} or {@code id} or {@code obj} is not an initialized Meteorite object.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null) 
			return false;
		if (!(obj instanceof Meteorite))
			return false;
		
		Meteorite m2 = (Meteorite) obj;
		
		if (this.name.equalsIgnoreCase(m2.name) && this.id == m2.id) {
			return true;
		}
		return false;
	}
	
	/**
	 * Formats the Meteorite to return as a String object like: 
	 * "NAME ID YEAR MASS LATITUDE LONGITUDE" (the parameters
	 * of the Meteorite). 
	 * 
	 * 
	 * @return A formatted String. NAME takes 20 spaces and is left aligned. ID and YEAR both take 
	 * 4 spaces and is right
	 * aligned. MASS is 6 spaces, right aligned. LATITUDE and LONGITUDE both take 10 spaces and
	 * displayed to 5 digits after the decimal point. Any undefined data value will be replaced 
	 * by spaces instead of a data value.
	 */
	@Override
	public String toString() {
		if (this.year != 0) {
			//Year exists
			if (this.mass != -1) {
				//Year and mass exists
				if (this.loc != null) {
					//Year, mass, and location exists
					return String.format("%-20s %4d %4d %6d %10.5f %10.5f", this.name, this.id,
							this.year, this.mass, this.loc.getLatitude(), this.loc.getLongitude());
				}
				else {
					//Year and mass exists, location does not exist
					return String.format("%-20s %4d %4d %6d %10s %10s", this.name, this.id,
							this.year, this.mass, "", "");
				}
			}
			else {
				//Year exists, but mass does not exist
				if (this.loc != null) {
					//Year and location exists, mass does not
					return String.format("%-20s %4d %4d %6s %10.5f %10.5f", this.name, this.id,
							this.year, "", this.loc.getLatitude(), this.loc.getLongitude());
				}
				else {
					//Only year exists
					return String.format("%-20s %4d %4d %6s %10s %10s", this.name, this.id,
							this.year, "", "", "");
				}
			}
		} 
		else {
			//Year does not exist
			if (this.mass != -1) {
				//Mass exists but year does not
				if (this.loc != null) {
					//Mass and location exists but year does not exist
					return String.format("%-20s %4d %4s %6d %10.5f %10.5f", this.name, this.id, "",
							this.mass, this.loc.getLatitude(), this.loc.getLongitude());
				}
				else {
					//Mass exists but location and year does not exist
					return String.format("%-20s %4d %4s %6d %10s %10s", this.name, this.id, "",
							this.mass, "", "");
				}
			}
			else {
				//Mass and year does not exist
				if (this.loc != null) {
					//Location exists but mass and year does not
					return String.format("%-20s %4d %4s %6s %10.5f %10.5f", this.name, this.id, "",
							"", this.loc.getLatitude(), this.loc.getLongitude());
				}
				else {
					//Mass, year, and location does not exist
					return String.format("%-20s %4d %4s %6s %10s %10s", this.name, this.id, "",
							"", "", "");
				}
			}
		}
	}
}
