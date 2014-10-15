package by.bigsoft.finalproject.classes;

public class City {
	private int id;
	private String cityName = "";
	private double latitude;
	private double longtitude;
	private boolean hasAirport;
	private boolean hasWaterport;
	private String cityContinent = "";
	
	public boolean isValid(){
		return cityName!=null && !cityName.isEmpty() && cityContinent!=null && !cityContinent.isEmpty() 
				&& (latitude>=-90 && latitude<=90) 
				&& (longtitude>=0 && longtitude<360);
	}
	
	public String toString(){
		return cityName+" ("+latitude+", "+longtitude+")";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public boolean isHasAirport() {
		return hasAirport;
	}
	public void setHasAirport(boolean hasAirport) {
		this.hasAirport = hasAirport;
	}
	public boolean isHasWaterport() {
		return hasWaterport;
	}
	public void setHasWaterport(boolean hasWaterport) {
		this.hasWaterport = hasWaterport;
	}
	public String getCityContinent() {
		return cityContinent;
	}
	public void setCityContinent(String cityContinent) {
		this.cityContinent = cityContinent;
	}
	
}
