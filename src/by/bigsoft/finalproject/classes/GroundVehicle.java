package by.bigsoft.finalproject.classes;

public abstract class GroundVehicle implements IVehicle {

	
	@Override
	public VehicleType getVehicleType() {
		return VehicleType.Ground;
	}

}
