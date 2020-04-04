package Utilities;


public class GeographicCoordinate {

	private double latitude;
	private double longitude;

	public GeographicCoordinate(String LatLon) {
		this.latitude = splitLatLon(LatLon)[0];
		this.longitude = splitLatLon(LatLon)[1];
	}

	public GeographicCoordinate(double lat, double lon) {
		this.latitude = lat;
		this.longitude = lon;
	}

	public GeographicCoordinate(double latDeg, double latMin, double latSec, double lonDeg, double lonMin,
			double lonSec) {
		this.latitude = Math.abs(latDeg) + (latMin / 60.0) + (latSec / 3600.0);
		if(latDeg < 0) {
			this.latitude = this.latitude *-1;
		}
		this.longitude = Math.abs(lonDeg) + (lonMin / 60.0) + (lonSec / 3600.0);
		if(lonDeg < 0) {
			this.longitude = this.longitude *-1;
		}
	}

	private double[] splitLatLon(String latLonString) {
		String[] split = latLonString.split(",");
		double[] geoPt = { Double.parseDouble(split[0]), Double.parseDouble(split[1]) };

		return geoPt;
	}

	public double getLat() {
		return latitude;
	}

	public double getLon() {
		return longitude;
	}

	public String toString() {
		return latitude + ", " + longitude;
	}
	
	public static double DegreesToRadians(double degrees) {
		return degrees * (Math.PI / 180);
	}

	public static double RadiansToDegrees(double radians) {
		return radians * (180 / Math.PI);
	}

}
