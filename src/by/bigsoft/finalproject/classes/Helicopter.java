package by.bigsoft.finalproject.classes;

public class Helicopter extends AirVehicle {

	@Override
	public int getPassangerCapacity() {
		return 5;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public double getCostPerKm() {
		return 1;
	}

	@Override
	public double getSpeed() {
		return 180;
	}
	
	public String getName() {
		return "Helicopter";
	}


}
