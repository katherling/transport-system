package by.bigsoft.finalproject.classes;

public class RowBoat extends WaterVehicle {

	@Override
	public int getPassangerCapacity() {
		return 2;
	}

	@Override
	public double getCargoCapacity() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public double getCostPerKm() {
		return 0;
	}

	@Override
	public double getSpeed() {
		return 5;
	}
	
	public String getName() {
		return "Row boat";
	}


}
