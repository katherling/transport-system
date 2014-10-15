package by.bigsoft.finalproject.classes;

public class Taxi extends GroundVehicle{
	@Override
	public int getPassangerCapacity() {
		return 4;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 50;
	}

	@Override
	public double getCostPerKm() {
		return 0.8;
	}

	@Override
	public double getSpeed() {
		return 110;
	}
	
	public String getName() {
		return "Taxi";
	}
}
