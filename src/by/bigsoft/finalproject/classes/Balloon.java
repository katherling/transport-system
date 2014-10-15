package by.bigsoft.finalproject.classes;

public class Balloon extends AirVehicle {

	@Override
	public int getPassangerCapacity() {
		return 3;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public double getCostPerKm() {
		return 0.07;
	}

	@Override
	public double getSpeed() {
		return 15;
	}
	
	public String getName() {
		return "Balloon";
	}

}
