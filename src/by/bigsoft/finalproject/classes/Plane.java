package by.bigsoft.finalproject.classes;

public class Plane extends AirVehicle {

	@Override
	public int getPassangerCapacity() {
		return 60;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 2000;
	}

	@Override
	public double getCostPerKm() {
		return 1.1;
	}

	@Override
	public double getSpeed() {
		return 500;
	}
	
	public String getName() {
		return "Plane";
	}


}
