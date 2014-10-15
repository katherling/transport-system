package by.bigsoft.finalproject.classes;

public class MiniBus extends GroundVehicle{
	@Override
	public int getPassangerCapacity() {
		return 18;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 150;
	}

	@Override
	public double getCostPerKm() {
		return 0.4;
	}

	@Override
	public double getSpeed() {
		return 90;
	}
	
	public String getName() {
		return "MiniBus";
	}
}
