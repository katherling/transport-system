package by.bigsoft.finalproject.classes;

import java.sql.Time;
import java.util.ArrayList;

public class Trip {
	private int id;
	private City sourceCity;
	private City destinationCity;
	private IVehicle vehicle;
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private ArrayList<Cargo> cargo = new ArrayList<Cargo>();
	
	public String toString() {
		return id == 0
			   ? vehicle.getName() + " (" + getTravelTimeString() + ", " + String.format("%.2f", getCost()) + "$)"
			   : id + ". " + sourceCity.getCityName() + " - " + destinationCity.getCityName();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public City getSourceCity() {
		return sourceCity;
	}
	public void setSourceCity(City sourceCity) {
		this.sourceCity = sourceCity;
	}
	public City getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(City destinationCity) {
		this.destinationCity = destinationCity;
	}
	public IVehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(IVehicle vehicle) {
		this.vehicle = vehicle;
	}
	public double getTravelTime() {
		return getDistance()/vehicle.getSpeed();
	}
	public String getTravelTimeString() {
		double fractionalHours = getTravelTime();
		double hours = Math.floor(fractionalHours);
		double fractionalMinutes = (fractionalHours - hours) * 60;
		double minutes = Math.floor(fractionalMinutes);
		double seconds = Math.floor((fractionalMinutes - minutes) * 60);
		
		return String.format("%.0f", hours) + " ÷ " + String.format("%.0f", minutes) 
				+ " ì " + String.format("%.0f", seconds) + " ñ";
	}
	public ArrayList<Cargo> getCargo() {
		return cargo;
	}
	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

	public void setCargo(ArrayList<Cargo> cargo) {
		this.cargo = cargo;
	}
	
	public double getDistance() {
		final double earthConst = 637;
		final double rToDeg = 0.17;
		
		
		return earthConst*
			Math.acos(
			  Math.sin(rToDeg * sourceCity.getLatitude()) * Math.sin(rToDeg * destinationCity.getLatitude()) +
			  Math.cos(rToDeg * sourceCity.getLatitude()) * Math.cos(rToDeg * destinationCity.getLatitude()) *
			  Math.cos(rToDeg * destinationCity.getLongtitude() - rToDeg * sourceCity.getLongtitude())
			);
	}
	
	public double getCost() {
		double dist = getDistance();
		return dist*vehicle.getCostPerKm();
	}
}
