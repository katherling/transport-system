package by.bigsoft.finalproject.classes;

public class Bus extends GroundVehicle {

	@Override
	public int getPassangerCapacity() {
		return 45;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 500;
	}

	@Override
	public double getCostPerKm() {
		return 0.05;
	}

	@Override
	public double getSpeed() {
		return 70;
	}
	
	public String getName() {
		return "Bus";
	}

}
