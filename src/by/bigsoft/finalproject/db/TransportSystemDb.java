package by.bigsoft.finalproject.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import by.bigsoft.finalproject.classes.Cargo;
import by.bigsoft.finalproject.classes.City;
import by.bigsoft.finalproject.classes.Continent;
import by.bigsoft.finalproject.classes.IVehicle;
import by.bigsoft.finalproject.classes.Passenger;
import by.bigsoft.finalproject.classes.Trip;

public class TransportSystemDb {
	private Connection con;
	
	public TransportSystemDb(Connection con) {
		this.con = con;
	}
	
	public void dispose() {
		try {
			//con.commit();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Continent getContinentById(int id) throws SQLException {
		Continent result;
		
		Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Continents WHERE Id = " + id);
        if (rs.next()) {
        	result = new Continent();
        	result.setId(rs.getInt("Id"));
        	result.setContinentName(rs.getString("ContinentName"));
        	return result;
        }
        else {
        	return null;
        }
	}
	
	public String[] getContinents () throws SQLException  {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Continents");
		
		ArrayList<String> continents = new ArrayList<String>();
		while (rs.next()) {
		  continents.add(rs.getString("ContinentName"));
		}

		String[] result = new String[continents.size()];
		return continents.toArray(result);
		
	}
	
	public ArrayList<City> getCities() throws SQLException {
		ArrayList<City> cities = new ArrayList<City>();
		Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Cities");
        while (rs.next()) {
  		  City city = new City();
  		  city.setId(rs.getInt("Id"));
  		  city.setCityName(rs.getString("CityName"));
  		  city.setLatitude(rs.getDouble("Latitude"));
  		  city.setLongtitude(rs.getDouble("Longtitude"));
  		  city.setHasAirport(rs.getBoolean("HasAirport"));
  		  city.setHasWaterport(rs.getBoolean("HasWaterport"));
  		  city.setCityContinent(rs.getString("ContinentName"));
  		  cities.add(city);
  		}
        return cities;
	}
	
	public City getCityById(int id) throws SQLException {
		Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Cities Where Id =" + id);
        if (rs.next()) {
  		  City city = new City();
  		  city.setId(rs.getInt("Id"));
  		  city.setCityName(rs.getString("CityName"));
  		  city.setLatitude(rs.getDouble("Latitude"));
  		  city.setLongtitude(rs.getDouble("Longtitude"));
  		  city.setHasAirport(rs.getBoolean("HasAirport"));
  		  city.setHasWaterport(rs.getBoolean("HasWaterport"));
  		  city.setCityContinent(rs.getString("ContinentName"));
  		  return city;
  		}
        else return null;
        
	}
	
	public boolean canRemoveCity(int id) throws SQLException {
		Statement statement = con.createStatement();
		return !statement.executeQuery("SELECT * FROM Trip "
				+ "Where SourceCityId = " + id + " or DestinationCityId = " + id).next();
	}
	
	public void removeCityById(int id) throws SQLException{
		Statement statement = con.createStatement();
		statement.execute("Delete FROM Cities Where Id = " + id);
	}
	
	public void addCity(City city) throws SQLException {
		PreparedStatement statement = con.prepareStatement("Insert into Cities "
        		+ "(CityName, Latitude, Longtitude,HasAirport, HasWaterPort, ContinentName) "
        		+ "values (?, ?, ?, ?, ?, ?)");
		
		statement.setString(1, city.getCityName());
		statement.setDouble(2, city.getLatitude());
		statement.setDouble(3, city.getLongtitude());
		statement.setBoolean(4, city.isHasAirport());
		statement.setBoolean(5, city.isHasWaterport());
		statement.setString(6, city.getCityContinent());
		statement.executeUpdate();
		//con.commit();
	}
	
	public void editCity(City city) throws SQLException {
		Statement statement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = statement.executeQuery("SELECT * FROM Cities WHERE Id = " + city.getId());
		if (rs.next()) {	
			rs.updateString("CityName", city.getCityName());
			rs.updateDouble("Latitude", city.getLatitude());
			rs.updateDouble("Longtitude", city.getLongtitude());
			rs.updateBoolean("HasAirport", city.isHasAirport());
			rs.updateBoolean("HasWaterPort", city.isHasWaterport());
			rs.updateString("ContinentName", city.getCityContinent());
			rs.updateRow();
		}
		//con.commit();
	}
	
	
	public ArrayList<Passenger> getPassengers() throws SQLException {
		ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Passengers");
        while (rs.next()) {
        	Passenger passenger = new Passenger();
  		  	passenger.setId(rs.getInt("Id"));
  		  	passenger.setLastName(rs.getString("LastName"));
  		  	passenger.setFirstName(rs.getString("FirstName"));
  		  	passenger.setPassport(rs.getString("Passport"));
  		  	
  		  	passengers.add(passenger);
  		}
        return passengers;
	}
	
	public Passenger getPassengerById(int id) throws SQLException {
		Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Passengers Where Id =" + id);
        if (rs.next()) {
  		  Passenger passenger = new Passenger();
  		  passenger.setId(rs.getInt("Id"));
  		  passenger.setLastName(rs.getString("LastName"));
  		  passenger.setFirstName(rs.getString("FirstName"));
  		  passenger.setPassport(rs.getString("Passport"));
  		  return passenger;
  		}
        else return null;
        
	}
	
	public boolean canRemovePassenger(int id) throws SQLException {
		Statement statement = con.createStatement();
		return !statement.executeQuery("SELECT * FROM PassengerTrip Where PassengerId = " + id).next();
	}
	
	public void removePassengerById(int id) throws SQLException{
		Statement statement = con.createStatement();
		statement.execute("Delete FROM Passengers Where Id = " + id);
	}
	
	public void addPassenger (Passenger passenger) throws SQLException {
		PreparedStatement statement = con.prepareStatement("Insert into Passengers "
        		+ "(LastName, FirstName, Passport) "
        		+ "values (?, ?, ?)");
		
		statement.setString(1, passenger.getLastName());
		statement.setString(2, passenger.getFirstName());
		statement.setString(3, passenger.getPassport());
		statement.executeUpdate();
		//con.commit();
	}
	
	public void editPassenger(Passenger passenger) throws SQLException {
		Statement statement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = statement.executeQuery("SELECT * FROM Passengers WHERE Id = " + passenger.getId());
		
		if (rs.next()) {	
			rs.updateString("LastName", passenger.getLastName());
			rs.updateString("FirstName", passenger.getFirstName());
			rs.updateString("Passport", passenger.getPassport());
			rs.updateRow();
		}
		//con.commit();
	}

	public void sendTrip(Trip t) throws SQLException {
		PreparedStatement statement = con.prepareStatement("Insert into Trip "
        		+ "(SourceCityId, DESTINATIONCITYID, VehicleClass) "
        		+ "values (?, ?, ?)");
		
		statement.setInt(1, t.getSourceCity().getId());
		statement.setInt(2, t.getDestinationCity().getId());
		statement.setString(3, t.getVehicle().getClass().getName());
		
		statement.executeUpdate(statement.RETURN_GENERATED_KEYS);
		ResultSet keys = statement.getGeneratedKeys();
		if (keys.next()) {
			int id = keys.getInt(1);
			PreparedStatement addPassenger = con.prepareStatement("Insert into PassengerTrip "
	        		+ "(PassengerId, TripId) "
	        		+ "values (?, ?)");
			
			
			for (Passenger p : t.getPassengers()) {
				addPassenger.setInt(1, p.getId());
				addPassenger.setInt(2, id);
				addPassenger.executeUpdate();
			}
			
			PreparedStatement addCargo = con.prepareStatement("Insert into CargoTrip "
	        		+ "(TripId, CargoName, Weight) "
	        		+ "values (?, ?, ?)");
			
			for (Cargo c : t.getCargo()) {
				addCargo.setInt(1, id);
				addCargo.setString(2, c.getName());
				addCargo.setDouble(3, c.getWeight());
				addCargo.executeUpdate();
			}
			
			t.setId(id);
		}
		//con.commit();
	}

	public ArrayList<Trip> getTrips() throws SQLException {
		ArrayList<Trip> res = new ArrayList<Trip>();
		
		Statement selectTrips = con.createStatement();
        ResultSet rs = selectTrips.executeQuery("SELECT * FROM Trip");
        while (rs.next()) {
        	try {
	        	Trip t = new Trip();
	        	t.setId(rs.getInt("Id"));
	        	t.setSourceCity(getCityById(rs.getInt("SourceCityId")));
	        	t.setDestinationCity(getCityById(rs.getInt("DestinationCityId")));
				t.setVehicle((IVehicle)Class.forName(rs.getString("VEHICLECLASS")).newInstance());
				t.setCargo(getTripCargo(t.getId()));
				t.setPassengers(getTripPassengers(t.getId()));
				res.add(t);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
		
		return res;
	}
	
	public ArrayList<Passenger> getTripPassengers(int tripId) throws SQLException {
		ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		PreparedStatement statement = con.prepareStatement(
				"SELECT * FROM PassengerTrip " +
        		"JOIN Passengers ON Passengers.Id = PassengerTrip.PassengerId" +
        		" WHERE PassengerTrip.TripId = ?");
		statement.setInt(1, tripId);
        ResultSet rs = statement.executeQuery();
        
        while (rs.next()) {
        	Passenger passenger = new Passenger();
  		  	passenger.setId(rs.getInt("Id"));
  		  	passenger.setLastName(rs.getString("LastName"));
  		  	passenger.setFirstName(rs.getString("FirstName"));
  		  	passenger.setPassport(rs.getString("Passport"));
  		  	
  		  	passengers.add(passenger);
  		}
        return passengers;
	}
	
	public ArrayList<Cargo> getTripCargo(int tripId) throws SQLException {
		ArrayList<Cargo> cargo = new ArrayList<Cargo>();
		PreparedStatement statement = con.prepareStatement(
				"SELECT * FROM CargoTrip WHERE TripId = ?");
		statement.setInt(1, tripId);
        ResultSet rs = statement.executeQuery();
        
        while (rs.next()) {
        	Cargo c = new Cargo();
  		  	c.setName(rs.getString("CargoName"));
  		  	c.setWeight(rs.getDouble("Weight"));
  		  	cargo.add(c);
  		}
        return cargo;
	}
	
	
}
