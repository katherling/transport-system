package by.bigsoft.finalproject;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;

import by.bigsoft.finalproject.classes.*;
import by.bigsoft.finalproject.db.TransportSystemDb;
import by.bigsoft.finalproject.ui.MainFrame;

public class Main {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/transportsystem",
					"testUser", "test");
			//TransportSystemDb db = new TransportSystemDb(con);
			//City city = db.getCityById(1);
			//System.out.println(city.isHasWaterport());
			//System.out.println(continent.getContinentName());
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				}
				catch (Exception e) {	}
			ArrayList<IVehicle> vehicles = new ArrayList<IVehicle>();
			vehicles.add(new Bus());
			vehicles.add(new MiniBus());
			vehicles.add(new MotorBoat());
			vehicles.add(new RowBoat());
			vehicles.add(new Taxi());
			vehicles.add(new Plane());
			vehicles.add(new Helicopter());
			vehicles.add(new Balloon());
			vehicles.add(new Ferry());
			
			
		    MainFrame frame = new MainFrame(vehicles);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setVisible(true);
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
