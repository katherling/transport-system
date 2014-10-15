package by.bigsoft.finalproject.classes;

public abstract class AirVehicle implements IVehicle {

	@Override
	public VehicleType getVehicleType() {
		return VehicleType.Aircraft;
	}

}
