package by.bigsoft.finalproject.classes;

public class Ferry extends WaterVehicle {

	@Override
	public int getPassangerCapacity() {
		return 1000;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 100000;
	}

	@Override
	public double getCostPerKm() {
		return 0.1;
	}

	@Override
	public double getSpeed() {
		return 50;
	}
	
	public String getName() {
		return "Ferry";
	}

}
