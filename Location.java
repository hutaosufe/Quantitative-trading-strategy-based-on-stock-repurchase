package project2;

/**
 * This class represents the location of the Meteorite object. Each Location object has 
 * 2 double values, first one ranging from [-90.0, 90.0] representing {@code latitude} and 
 * second one ranging from [-180.0, 180.0] representing {@code longitude}, passed to the constructor 
 * to create Location object.
 * 
 * @author Jonason Wu
 * @version 10/5/2020
 */
class Location {
	private double latitude;
	private double longitude;
	/**
	 * Constructor creates the location for the Meteorite by taking in a latitude between 
	 * -90.0 and 90.0 and a longitude between -180.0 and 180.0. 
	 * 
	 * @param latitude the latitude of the Meteorite landing location.
	 * @param longitude the longitude of the Meteorite landing location.
	 * @throws IllegalArgumentException if latitude is out of range ([-90.0, 90.0]) or 
	 * longitude is out of range ([-180.0, 180.0]).
	 */
	public Location (double latitude, double longitude) throws IllegalArgumentException {
		if (latitude >= -90.0 && latitude <= 90.0) {
			this.setLatitude(latitude);
		}
		else {
			throw new IllegalArgumentException("The latitude is out of range for: " 
					+ latitude + ", " + longitude + ". Valid range is [-90.0,90.0]");
		}
		if (longitude >= -180.0 && longitude <= 180.0) {
			this.setLongitude(longitude);
		}
		else {
			throw new IllegalArgumentException("The longitude is out of range for: " 
					+ latitude + ", " + longitude + ". Valid range is [-180.0, 180.0]");
		}
	}
	
	/**
	 * Get the latitude of the Location that called this method. Should not be called 
	 * by a Location object that has not been initialized.
	 * 
	 * @return the latitude of the Location object.
	 */
	public double getLatitude() {
		return this.latitude;
	}
	
	/**
	 * Sets the latitude of the Location object. Only used in constructor to initialize location.
	 * 
	 * @param latitude the latitude of the Location object.
	 */
	private void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Get the longitude of the Location that called this method. Should not be activated
	 * on a Location object that has not been initialized.
	 * 
	 * @return the longitude of the Location object.
	 */
	public double getLongitude() {
		return this.longitude;
	}
	
	/**
	 * Sets the longitude of the Location object. Only used in constructor to initialize location.
	 * 
	 * @param longitude the longitude of the Location object.
	 */
	private void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * This method finds the distance between the Location object that calls the method and
	 * the {@code loc} parameter. This method uses the haversine formula provided by Geeks for Geeks.
	 * See: {@link https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/}
	 * 
	 * @param loc the location to get the distance from.
	 * @return the distance between 2 Location objects.
	 * @throws IllegalArgumentException if {@code loc} is null.
	 */
	public double getDistance(Location loc) throws IllegalArgumentException {
		if (loc == null) {
			throw new IllegalArgumentException("The location is undefined. Cannot compute distance.");
		}
		double lat1 = this.getLatitude();
		double lon1 = this.getLongitude();
		double lat2 = loc.getLatitude();
		double lon2 = loc.getLongitude();
		
		//Adapted from the Haversine Formula
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formula
		double a = Math.pow(Math.sin(dLat / 2), 2) +
				   Math.pow(Math.sin(dLon / 2), 2) *
				   Math.cos(lat1) *
				   Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}
	
	/**
	 * This method finds out whether 2 Location objects are equal. They are considered equal if
	 * the differences between the latitudes and longitudes of the 2 Location objects are less
	 * than 0.00001.
	 * 
	 * @param obj the Location object to compare locations with.
	 * @return true if differences between the latitudes and longitudes of the 2 Location objects 
	 * are less than 0.00001. False if otherwise or if {@code obj} does not have location.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Location))
			return false;
		Location l1 = this;
		Location l2 = (Location) obj;
		if (Math.abs(l1.latitude-l2.latitude) < 0.00001 
				&& Math.abs(l2.longitude-l2.longitude) < 0.00001) {
			return true;
		}
		return false;
	}
}
