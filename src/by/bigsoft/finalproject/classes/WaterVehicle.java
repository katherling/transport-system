package by.bigsoft.finalproject.classes;

public abstract class WaterVehicle implements IVehicle {

	@Override
	public VehicleType getVehicleType() {
		return VehicleType.Water;
	}

}
