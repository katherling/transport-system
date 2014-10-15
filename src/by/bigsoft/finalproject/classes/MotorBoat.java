package by.bigsoft.finalproject.classes;

public class MotorBoat extends WaterVehicle {

	@Override
	public int getPassangerCapacity() {
		return 15;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 60;
	}

	@Override
	public double getCostPerKm() {
		return 0.6;
	}

	@Override
	public double getSpeed() {
		return 20;
	}
	
	public String getName() {
		return "Motor boat";
	}

}
