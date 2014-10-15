package by.bigsoft.finalproject.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import by.bigsoft.finalproject.classes.Cargo;
import by.bigsoft.finalproject.classes.City;
import by.bigsoft.finalproject.classes.IVehicle;
import by.bigsoft.finalproject.classes.Passenger;
import by.bigsoft.finalproject.classes.Trip;
import by.bigsoft.finalproject.db.TransportSystemDb;

public class MainFrame extends JFrame {
	private JList<City> listCities;
	private JList<Passenger> listPassengers;
	private JList <Passenger> listInfoPassengersOfCurrentTrip;
	private JList <Passenger> listPassengersOfCurrentTrip;
	private DefaultListModel<Passenger> modelPassengerOfCurrentTrip;
	private JList <Cargo> listCargoOfCurrentTrip;
	private DefaultListModel<Cargo> modelCargoOfCurrentTrip;
	private JTextField textFieldNumberOfPassengers;
	private JTextField textFieldTotalWeight;
	private DefaultComboBoxModel<City> modelCityFrom;
	private DefaultComboBoxModel<City> modelCityTo;
	private DefaultComboBoxModel<Trip> modelTrips;
	private ArrayList<IVehicle> vehicles;
	
	
	public MainFrame (ArrayList<IVehicle> vehicles){
		this.vehicles = vehicles;
		JTabbedPane pane = new JTabbedPane();
		this.setSize(1000,530);
		pane.addTab("Добавление в базу данных", getPassangerCityTab());
		pane.addTab("Формирование рейса", getNewTripTab());
		pane.addTab("Информация о рейсах", getTripInfoTab());
		getContentPane().add(pane, BorderLayout.CENTER);
	}
	
	private JComponent getPassangerCityTab() {
		   JPanel panelBD = new JPanel();
		   JButton buttonAddCity = new JButton("Добавить город");
		   JButton buttonRemoveCity = new JButton("Удалить город");
		   JButton buttonEditCity = new JButton("Редактировать город");
		   listCities = new JList<City>();
		   JScrollPane scrollCities = new JScrollPane(listCities);
		   updateJListCity();
		   
		   listPassengers = new JList();
		   JScrollPane scrollPassengers = new JScrollPane(listPassengers);
		   updateJListPassenger();
		   
		   JButton buttonAddPassenger = new JButton("Добавить пассажира");
		   JButton buttonRemovePassenger = new JButton("Удалить пассажира");
		   JButton buttonEditPassenger = new JButton("Редактировать пассажира");		   
		   
		   Box boxBD = Box.createVerticalBox();
		   panelBD.add(boxBD);
		   boxBD.add(Box.createVerticalStrut(20));
		   Box boxBDCityMain = Box.createHorizontalBox();
		   boxBDCityMain.setBorder(BorderFactory.createTitledBorder("Город"));
		   boxBD.add(boxBDCityMain);
		   boxBD.add(Box.createVerticalStrut(20));
		   Box boxBDPassengerMain = Box.createHorizontalBox();
		   boxBDPassengerMain.setBorder(BorderFactory.createTitledBorder("Пассажир"));
		   boxBD.add(boxBDPassengerMain);
		   boxBD.add(Box.createVerticalStrut(20));
		   
		   boxBDCityMain.add(Box.createHorizontalStrut(20));
		   boxBDCityMain.add(scrollCities);
		   boxBDCityMain.add(Box.createHorizontalStrut(20));
		   Box boxBDCityMainNavigate = Box.createVerticalBox();
		   boxBDCityMain.add(boxBDCityMainNavigate);
		   boxBDCityMain.add(Box.createHorizontalStrut(20));
		   
		   boxBDCityMainNavigate.add(Box.createVerticalStrut(20));
		   boxBDCityMainNavigate.add(buttonAddCity);
		   boxBDCityMainNavigate.add(Box.createVerticalStrut(20));
		   boxBDCityMainNavigate.add(buttonRemoveCity);
		   boxBDCityMainNavigate.add(Box.createVerticalStrut(20));
		   boxBDCityMainNavigate.add(buttonEditCity);
		   boxBDCityMainNavigate.add(Box.createVerticalStrut(20));
		   
		   boxBDPassengerMain.add(Box.createHorizontalStrut(20));
		   boxBDPassengerMain.add(scrollPassengers);
		   boxBDPassengerMain.add(Box.createHorizontalStrut(20));
		   Box boxBDPassengerMainNavigate = Box.createVerticalBox();
		   boxBDPassengerMain.add(boxBDPassengerMainNavigate);
		   boxBDPassengerMain.add(Box.createHorizontalStrut(20));
		   
		   boxBDPassengerMainNavigate.add(Box.createVerticalStrut(20));
		   boxBDPassengerMainNavigate.add(buttonAddPassenger);
		   boxBDPassengerMainNavigate.add(Box.createVerticalStrut(20));
		   boxBDPassengerMainNavigate.add(buttonRemovePassenger);
		   boxBDPassengerMainNavigate.add(Box.createVerticalStrut(20));
		   boxBDPassengerMainNavigate.add(buttonEditPassenger);
		   boxBDPassengerMainNavigate.add(Box.createVerticalStrut(20));
		   
		   buttonAddCity.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showNewCityDialog(null);
			}
		   });
		   buttonRemoveCity.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					int dialogResult = JOptionPane.showConfirmDialog(MainFrame.this, 
	    					"Вы действительно хотите удалить этот город?", "Удаление",
	    					JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						try {
							TransportSystemDb db = getDb();
							int id = listCities.getSelectedValue().getId();
							if (db.canRemoveCity(id)) {
								db.removeCityById(id);
								updateJListCity();
							}
							else {
								JOptionPane.showMessageDialog(MainFrame.this, 
				    					"Невозможно удалить город, так как он используется", 
				    					"Ошибка", JOptionPane.ERROR_MESSAGE);
							}
							db.dispose();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					
				}
		   });
		   buttonEditCity.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					showNewCityDialog(listCities.getSelectedValue().getId());
				}
			   });
		   buttonAddPassenger.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showNewPassengerDialog(null);
				
			}
		   });
		   buttonRemovePassenger.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					int dialogResult = JOptionPane.showConfirmDialog(MainFrame.this, 
	    					"Вы действительно хотите удалить этот город?", "Удаление",
	    					JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						try {
							TransportSystemDb db = getDb();
							int id = listPassengers.getSelectedValue().getId();
							if (db.canRemovePassenger(id)) {
								db.removePassengerById(id);
								updateJListPassenger();
							}
							else {
								JOptionPane.showMessageDialog(MainFrame.this, 
				    					"Невозможно удалить пассажира, так как он учавствует в поездке", 
				    					"Ошибка", JOptionPane.ERROR_MESSAGE);
							}
							db.dispose();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			   });
		   buttonEditPassenger.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					showNewPassengerDialog(listPassengers.getSelectedValue().getId());
					
				}
			   });
		   
		   return panelBD;
		}
	
	private void showNewCityDialog(final Integer id) {
		City city = new City();
		if (id != null){
			try {
				TransportSystemDb db = getDb();
				city = db.getCityById(id);
				db.dispose();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		final JDialog dialog = new JDialog(this);
		dialog.setSize(500, 250);
		dialog.setLayout(new BorderLayout());
		
		JLabel labelEnterCityName = new JLabel("Введите название города:");
		final JTextField textFieldEnterName = new JTextField(city.getCityName());
		textFieldEnterName.setEditable(true);
		JLabel labelLatitude = new JLabel("Широта:");
		final JTextField textFieldLatitude = new JTextField(Double.toString(city.getLatitude()));
		textFieldLatitude.setSize(100, 15);
		textFieldLatitude.setEditable(true);
		JLabel labelLongtitude = new JLabel("Долгота:");
		final JTextField textFieldLongtitude = new JTextField(Double.toString(city.getLongtitude()));
		textFieldLongtitude.setEditable(true);
		JLabel labelContinent = new JLabel("Материк:");
		final JCheckBox checkBoxHasAirport = new JCheckBox("Наличие аэропорта");
		checkBoxHasAirport.setSelected(city.isHasAirport());
		final JCheckBox checkBoxHasWaterPort = new JCheckBox("Наличие порта");
		checkBoxHasWaterPort.setSelected(city.isHasWaterport());
		String[] continents = new String[0];
		try {
			TransportSystemDb db = getDb();
			continents = db.getContinents();
			db.dispose();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		final JComboBox comboBoxContinent = new JComboBox(continents);
		for (int i = 0; i<comboBoxContinent.getItemCount(); i++){
			if(city.getCityContinent().equals(comboBoxContinent.getItemAt(i))){
				comboBoxContinent.setSelectedIndex(i);
			}
		}
		Box boxBDCity = Box.createVerticalBox();
		
		boxBDCity.add(Box.createVerticalStrut(20));
		Box boxBDCityName = Box.createHorizontalBox();
		boxBDCity.add(boxBDCityName);
		boxBDCityName.add(Box.createHorizontalStrut(20));
		boxBDCityName.add(labelEnterCityName);
		boxBDCityName.add(Box.createHorizontalStrut(20));
		boxBDCityName.add(Box.createHorizontalGlue());
		boxBDCityName.add(Box.createHorizontalStrut(20));
		boxBDCityName.add(textFieldEnterName);
		boxBDCityName.add(Box.createHorizontalStrut(20));
		   
		boxBDCity.add(Box.createVerticalStrut(20));
		Box boxBDCityMetrics = Box.createHorizontalBox();
		boxBDCity.add(boxBDCityMetrics);
		boxBDCityMetrics.add(Box.createHorizontalStrut(20));
		boxBDCityMetrics.add(labelLatitude);
		boxBDCityMetrics.add(Box.createHorizontalStrut(20));
		boxBDCityMetrics.add(Box.createHorizontalGlue());
		boxBDCityMetrics.add(Box.createHorizontalStrut(20));
		boxBDCityMetrics.add(textFieldLatitude);
		boxBDCityMetrics.add(Box.createHorizontalStrut(20));
		boxBDCityMetrics.add(Box.createHorizontalGlue());
		boxBDCityMetrics.add(Box.createHorizontalStrut(20));
		boxBDCityMetrics.add(labelLongtitude);
		boxBDCityMetrics.add(Box.createHorizontalStrut(20));
		boxBDCityMetrics.add(Box.createHorizontalGlue());
		boxBDCityMetrics.add(Box.createHorizontalStrut(20));
		boxBDCityMetrics.add(textFieldLongtitude);
		boxBDCityMetrics.add(Box.createHorizontalStrut(20));
		   
		boxBDCity.add(Box.createVerticalStrut(20));
		Box boxBDCityPorts = Box.createHorizontalBox();
		boxBDCity.add(boxBDCityPorts);
		boxBDCityPorts.add(Box.createHorizontalStrut(20));
		boxBDCityPorts.add(checkBoxHasAirport);
		boxBDCityPorts.add(Box.createHorizontalStrut(20));
		boxBDCityPorts.add(checkBoxHasWaterPort);
		boxBDCityPorts.add(Box.createHorizontalStrut(20));
		
		boxBDCity.add(Box.createVerticalStrut(20));
		Box boxBDCityContinent = Box.createHorizontalBox();
		boxBDCity.add(boxBDCityContinent);
		boxBDCityContinent.add(Box.createHorizontalStrut(20));
		boxBDCityContinent.add(labelContinent);
		boxBDCityContinent.add(Box.createHorizontalStrut(20));
		boxBDCityContinent.add(Box.createHorizontalGlue());
		boxBDCityContinent.add(Box.createHorizontalStrut(20));
		boxBDCityContinent.add(comboBoxContinent);
		boxBDCityContinent.add(Box.createHorizontalStrut(20));
		boxBDCity.add(Box.createVerticalStrut(20));
	    
		Box okBox = Box.createHorizontalBox();
		boxBDCity.add(okBox);
		boxBDCity.add(Box.createVerticalStrut(20));

	    JButton okButton = new JButton("OK");
	    boxBDCity.add(okButton);
	    okButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent arg0) {
	    		try{
		    		City newCity = new City();
		    		newCity.setCityName(textFieldEnterName.getText());
		    		newCity.setLatitude(Double.parseDouble(textFieldLatitude.getText()));
		    		newCity.setLongtitude(Double.parseDouble(textFieldLongtitude.getText()));
		    		newCity.setHasAirport(checkBoxHasAirport.isSelected());
		    		newCity.setHasWaterport(checkBoxHasWaterPort.isSelected());
		    		newCity.setCityContinent((String)comboBoxContinent.getSelectedItem());
		    		if (newCity.isValid() == false) {
		    			JOptionPane.showMessageDialog(MainFrame.this, 
		    					"Проверьте правильность введенных данных", 
		    					"Ошибка", JOptionPane.ERROR_MESSAGE);
		    			return;
		    		}
		    		TransportSystemDb db = getDb();
		    		if (id == null){
		    			db.addCity(newCity);
		    		} 
		    		else {
		    			newCity.setId(id);
		    			db.editCity(newCity);
		    		}
		    		db.dispose();
	    		} catch (ClassNotFoundException e1) {
	    			e1.printStackTrace();
	    		} catch (SQLException e1) {
	    			e1.printStackTrace();
	    		} catch (NumberFormatException e1){
	    			JOptionPane.showMessageDialog(MainFrame.this, 
	    					"Проверьте правильность введенных данных", 
	    					"Ошибка", JOptionPane.ERROR_MESSAGE);
	    			return;
	    		}
	    		updateJListCity();
	    		dialog.setVisible(false);
	    		dialog.dispose();
	    		}
	    	});
	    
	    JButton cancelButton = new JButton("Отмена");
	    boxBDCity.add(cancelButton);
	    cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
	    		dialog.dispose();
			}
	    });
	    
	    
	    
	    okBox.add(Box.createHorizontalGlue());
	    okBox.add(Box.createHorizontalStrut(20));
	    okBox.add(okButton);
	    okBox.add(Box.createHorizontalStrut(20));
	    okBox.add(cancelButton);
	    okBox.add(Box.createHorizontalStrut(20));
	    
	    dialog.add(boxBDCity);
	    dialog.setVisible(true);
	   }
	
	private void showNewPassengerDialog(final Integer id) {
		Passenger passenger = new Passenger();
		if (id != null){
			try {
				TransportSystemDb db = getDb();
				passenger = db.getPassengerById(id);
				db.dispose();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		final JDialog dialog = new JDialog(this);
		dialog.setSize(400, 200);
		
		JLabel labelEnterPassengerLastName = new JLabel("Фамилия:");
		final JTextField textFieldEnterPassengerLastName = new JTextField(passenger.getLastName());
		textFieldEnterPassengerLastName.setEditable(true);
		JLabel labelEnterPassengerFirstName = new JLabel("Имя:");
		final JTextField textFieldEnterPassengerFirstName = new JTextField(passenger.getFirstName());
		textFieldEnterPassengerFirstName.setEditable(true);
		JLabel labelEnterPassengerPassportSeries = new JLabel("Серия паспорта:");
		final JTextField textFieldEnterPassengerPassportSeries = new JTextField(passenger.getPassportSeries());
		textFieldEnterPassengerPassportSeries.setEditable(true);
		JLabel labelEnterPassengerPassportNumber = new JLabel("Номер паспорта:");
		final JTextField textFieldEnterPassengerPassportNumber = new JTextField(passenger.getPassportNumber());
		textFieldEnterPassengerPassportNumber.setEditable(true);
		
		Box boxBDPassenger = Box.createVerticalBox();
		boxBDPassenger.add(Box.createVerticalStrut(20));
		Box boxBDPassengerName = Box.createHorizontalBox();
		boxBDPassenger.add(boxBDPassengerName);
		boxBDPassengerName.add(Box.createHorizontalStrut(20));
		boxBDPassengerName.add(labelEnterPassengerLastName);
		boxBDPassengerName.add(Box.createHorizontalStrut(20));
		boxBDPassengerName.add(textFieldEnterPassengerLastName);
		boxBDPassengerName.add(Box.createHorizontalStrut(20));
		boxBDPassengerName.add(labelEnterPassengerFirstName);
		boxBDPassengerName.add(Box.createHorizontalStrut(20));
		boxBDPassengerName.add(textFieldEnterPassengerFirstName);
		boxBDPassengerName.add(Box.createHorizontalStrut(20));
		boxBDPassenger.add(Box.createVerticalStrut(20));
		Box boxBDPassengerPassport = Box.createHorizontalBox();
		boxBDPassenger.add(boxBDPassengerPassport);
		boxBDPassengerPassport.add(Box.createHorizontalStrut(20));
		boxBDPassengerPassport.add(labelEnterPassengerPassportSeries);
		boxBDPassengerPassport.add(Box.createHorizontalStrut(20));
		boxBDPassengerPassport.add(textFieldEnterPassengerPassportSeries);
		boxBDPassengerPassport.add(Box.createHorizontalStrut(20));
		boxBDPassengerPassport.add(labelEnterPassengerPassportNumber);
		boxBDPassengerPassport.add(Box.createHorizontalStrut(20));
		boxBDPassengerPassport.add(textFieldEnterPassengerPassportNumber);
		boxBDPassengerPassport.add(Box.createHorizontalStrut(20));
		
		boxBDPassenger.add(Box.createVerticalStrut(20));
	    Box okBox = Box.createHorizontalBox();
		boxBDPassenger.add(okBox);
		boxBDPassenger.add(Box.createVerticalStrut(20));

	    JButton okButton = new JButton("OK");
	    boxBDPassenger.add(okButton);
	    okButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent arg0) {
	    		try{
		    		Passenger newPassenger = new Passenger();
		    		newPassenger.setLastName(textFieldEnterPassengerLastName.getText());
		    		newPassenger.setFirstName(textFieldEnterPassengerFirstName.getText());
		    		newPassenger.setPassport(textFieldEnterPassengerPassportSeries.getText()
		    				+textFieldEnterPassengerPassportNumber.getText());
		    		if (newPassenger.isValid() == false && textFieldEnterPassengerPassportSeries.getText().isEmpty()
		    				&&textFieldEnterPassengerPassportNumber.getText().isEmpty()) {
		    			JOptionPane.showMessageDialog(MainFrame.this, 
		    					"Проверьте правильность введенных данных", 
		    					"Ошибка", JOptionPane.ERROR_MESSAGE);
		    			return;
		    		}
		    		TransportSystemDb db = getDb();
		    		if (id == null){
		    			db.addPassenger(newPassenger);
		    		} 
		    		else {
		    			newPassenger.setId(id);
		    			db.editPassenger(newPassenger);
		    		}
		    		db.dispose();
	    		} catch (ClassNotFoundException e1) {
	    			e1.printStackTrace();
	    		} catch (SQLException e1) {
	    			e1.printStackTrace();
	    		} catch (NumberFormatException e1){
	    			JOptionPane.showMessageDialog(MainFrame.this, 
	    					"Проверьте правильность введенных данных", 
	    					"Ошибка", JOptionPane.ERROR_MESSAGE);
	    			return;
	    		}
	    		updateJListPassenger();
	    		dialog.setVisible(false);
	    		dialog.dispose();
	    		}
	    	});
	    
	    JButton cancelButton = new JButton("Отмена");
	    boxBDPassenger.add(cancelButton);
	    cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
	    		dialog.dispose();
			}
	    });
	    
	    okBox.add(Box.createHorizontalGlue());
	    okBox.add(Box.createHorizontalStrut(20));
	    okBox.add(okButton);
	    okBox.add(Box.createHorizontalStrut(20));
	    okBox.add(cancelButton);
	    okBox.add(Box.createHorizontalStrut(20));
	    
	    dialog.add(boxBDPassenger);
	    dialog.setVisible(true);
	   }
	
	private JComponent getNewTripTab() {
		   JPanel panelNewTrip = new JPanel();
		   JLabel labelFrom = new JLabel("Пункт отправления: ");
		   final JComboBox<City> comboBoxFieldFrom = new JComboBox<City>(modelCityFrom);
		   comboBoxFieldFrom.setEditable(false);
		   JLabel labelTo = new JLabel("Пункт назначения: ");
		   final JComboBox<City> comboBoxTo = new JComboBox<City>(modelCityTo);
		   comboBoxTo.setEditable(false);
		   modelPassengerOfCurrentTrip = new DefaultListModel<Passenger>();
		   listPassengersOfCurrentTrip = new JList<Passenger>(modelPassengerOfCurrentTrip);
		   JScrollPane scrollPassengersOfCurrentTrip = new JScrollPane(listPassengersOfCurrentTrip);
		   modelCargoOfCurrentTrip = new DefaultListModel<Cargo>();
		   listCargoOfCurrentTrip = new JList<Cargo>(modelCargoOfCurrentTrip);
		   JScrollPane scrollCargoOfCurrentTrip = new JScrollPane(listCargoOfCurrentTrip);
		   JButton buttonAddPassengerToCurrentTrip = new JButton("Добавить пассажира");
		   JButton buttonRemovePassengerFromCurrentTrip = new JButton("Удалить пассажира");
		   JButton buttonRemoveAllPassengersFromCurrentTrip = new JButton("Удалить всех пассажиров");
		   JButton buttonAddCargoToCurrentrip = new JButton("Добравить груз");
		   JButton buttonRemoveCargoFromCurrentrip = new JButton("Удалить груз");
		   JButton buttonRemoveAllCargoFromCurrentrip = new JButton("Удалить все грузы");
		   JButton buttonCalculate = new JButton("Рассчитать маршрут");
		   JLabel labelNumberOfPassengers = new JLabel("Количество пассажиров рейса: ");
		   textFieldNumberOfPassengers = new JTextField("0");
		   textFieldNumberOfPassengers.setEditable(false);
		   JLabel labelTotalWeight = new JLabel("Общий вес: ");
		   textFieldTotalWeight = new JTextField("0");
		   textFieldTotalWeight.setEditable(false);
		   
		   Box boxNewTrip = Box.createVerticalBox();			//общая структура вкладки Формирование нового рейса
		   panelNewTrip.add(boxNewTrip);
		   boxNewTrip.add(Box.createVerticalStrut(20));
		   Box boxNewTripPlaces = Box.createHorizontalBox();
		   boxNewTrip.add(boxNewTripPlaces);
		   boxNewTrip.add(Box.createVerticalStrut(20));
		   Box boxNewTripTransferring = Box.createHorizontalBox();
		   boxNewTrip.add(boxNewTripTransferring);
		   boxNewTrip.add(Box.createVerticalStrut(20));
		   Box boxNewTripVariants = Box.createHorizontalBox();
		   boxNewTripVariants.setBorder(BorderFactory.createTitledBorder("Варианты"));
		   boxNewTrip.add(boxNewTripVariants);
		   boxNewTrip.add(Box.createVerticalStrut(20));
		   Box boxNewTripMotions = Box.createHorizontalBox();
		   boxNewTrip.add(boxNewTripMotions);
		   boxNewTrip.add(Box.createVerticalStrut(20));
		   
		   boxNewTripPlaces.add(Box.createHorizontalStrut(20));		//коробка с местами назначения и отправления
		   Box boxNewTripPlacesFrom = Box.createVerticalBox();
		   boxNewTripPlaces.add(boxNewTripPlacesFrom);
		   boxNewTripPlacesFrom.add(Box.createVerticalStrut(20));
		   boxNewTripPlacesFrom.add(labelFrom);
		   boxNewTripPlacesFrom.add(Box.createVerticalStrut(20));
		   boxNewTripPlacesFrom.add(comboBoxFieldFrom);
		   boxNewTripPlacesFrom.add(Box.createVerticalStrut(20));
		   boxNewTripPlaces.add(Box.createHorizontalStrut(20));
		   Box boxNewTripPlacesTo = Box.createVerticalBox();
		   boxNewTripPlaces.add(boxNewTripPlacesTo);
		   boxNewTripPlacesTo.add(Box.createVerticalStrut(20));
		   boxNewTripPlacesTo.add(labelTo);
		   boxNewTripPlacesTo.add(Box.createVerticalStrut(20));
		   boxNewTripPlacesTo.add(comboBoxTo);
		   boxNewTripPlacesTo.add(Box.createVerticalStrut(20));
		   boxNewTripPlaces.add(Box.createHorizontalStrut(20));
		   Box boxNewTripPlacesTripNumber = Box.createVerticalBox();
		   boxNewTripPlaces.add(boxNewTripPlacesTripNumber);
		   boxNewTripPlacesTripNumber.add(Box.createVerticalStrut(20));
		   boxNewTripPlaces.add(Box.createHorizontalStrut(20));
		   
		   boxNewTripTransferring.add(Box.createHorizontalStrut(20));		//коробка с добавлением пассажиров и груза
		   boxNewTripTransferring.add(scrollPassengersOfCurrentTrip);
		   boxNewTripTransferring.add(Box.createHorizontalStrut(20));
		   Box boxNewTripTransferringNavigatePassengers = Box.createVerticalBox();
		   boxNewTripTransferring.add(boxNewTripTransferringNavigatePassengers);
		   boxNewTripTransferringNavigatePassengers.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigatePassengers.add(buttonAddPassengerToCurrentTrip);
		   boxNewTripTransferringNavigatePassengers.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigatePassengers.add(buttonRemovePassengerFromCurrentTrip);
		   boxNewTripTransferringNavigatePassengers.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigatePassengers.add(buttonRemoveAllPassengersFromCurrentTrip);
		   boxNewTripTransferringNavigatePassengers.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigatePassengers.add(labelNumberOfPassengers);
		   boxNewTripTransferringNavigatePassengers.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigatePassengers.add(textFieldNumberOfPassengers);
		   boxNewTripTransferringNavigatePassengers.add(Box.createVerticalStrut(20));
		   boxNewTripTransferring.add(Box.createHorizontalStrut(20));
		   Box boxNewTripTransferringNavigateCargo = Box.createVerticalBox();
		   boxNewTripTransferring.add(boxNewTripTransferringNavigateCargo);
		   boxNewTripTransferringNavigateCargo.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigateCargo.add(buttonAddCargoToCurrentrip);
		   boxNewTripTransferringNavigateCargo.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigateCargo.add(buttonRemoveCargoFromCurrentrip);
		   boxNewTripTransferringNavigateCargo.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigateCargo.add(buttonRemoveAllCargoFromCurrentrip);
		   boxNewTripTransferringNavigateCargo.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigateCargo.add(labelTotalWeight);
		   boxNewTripTransferringNavigateCargo.add(Box.createVerticalStrut(20));
		   boxNewTripTransferringNavigateCargo.add(textFieldTotalWeight);
		   boxNewTripTransferringNavigateCargo.add(Box.createVerticalStrut(20));
		   boxNewTripTransferring.add(Box.createHorizontalStrut(20));
		   boxNewTripTransferring.add(scrollCargoOfCurrentTrip);
		   boxNewTripTransferring.add(Box.createHorizontalStrut(20));
		   
		   boxNewTripMotions.add(Box.createHorizontalStrut(20));
		   boxNewTripMotions.add(Box.createHorizontalGlue());
		   boxNewTripMotions.add(buttonCalculate);
		   boxNewTripMotions.add(Box.createHorizontalStrut(20));
		   buttonAddPassengerToCurrentTrip.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					showListOfPassengersDialog();
					//обновить список текущего рейса
					textFieldNumberOfPassengers.setText(Integer.toString(modelPassengerOfCurrentTrip.size()));
				}
		   });
		   
		   buttonRemovePassengerFromCurrentTrip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modelPassengerOfCurrentTrip.removeElementAt(listPassengersOfCurrentTrip.getSelectedIndex());
					textFieldNumberOfPassengers.setText(Integer.toString(modelPassengerOfCurrentTrip.size()));
				}
		   });
		   
		   buttonRemoveAllPassengersFromCurrentTrip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modelPassengerOfCurrentTrip.clear();
					textFieldNumberOfPassengers.setText(Integer.toString(modelPassengerOfCurrentTrip.size()));
				}
		   });
		   
		   buttonAddCargoToCurrentrip.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					showAddCargoDialog();
					double sum = 0;
					for (int i=0; i<modelCargoOfCurrentTrip.size(); i++) {
						sum += modelCargoOfCurrentTrip.elementAt(i).getWeight();
					}
					textFieldTotalWeight.setText(Double.toString(sum));
				}
		   });
		   
		   buttonRemoveCargoFromCurrentrip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					modelCargoOfCurrentTrip.removeElementAt(listCargoOfCurrentTrip.getSelectedIndex());
					double sum = 0;
					for (int i=0; i<modelCargoOfCurrentTrip.size(); i++) {
						sum += modelCargoOfCurrentTrip.elementAt(i).getWeight();
					}
					textFieldTotalWeight.setText(Double.toString(sum));
				}
		   });
		   
		   buttonRemoveAllCargoFromCurrentrip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					modelCargoOfCurrentTrip.clear();
					double sum = 0;
					for (int i=0; i<modelCargoOfCurrentTrip.size(); i++) {
						sum += modelCargoOfCurrentTrip.elementAt(i).getWeight();
					}
					textFieldTotalWeight.setText(Double.toString(sum));
				}
		   });
		   
		   buttonCalculate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					showAvaliableVehicles();
				}
		   });
		   
		   return panelNewTrip;
		}
	
	
	private void showListOfPassengersDialog(){
		final JDialog dialog = new JDialog(this);
		dialog.setSize(400, 200);
		JButton buttonCancel = new JButton("Отмена");
		JButton buttonCreatePassenger = new JButton("Создать нового пассажира");
		JButton buttonAddPassenger = new JButton("Добавить выделенного пассажира");
		JScrollPane scrollPassengersForCurrentTrip = new JScrollPane(listPassengers);
		updateJListPassenger();
		
		Box boxListOfPassengers = Box.createHorizontalBox();
		boxListOfPassengers.add(Box.createHorizontalStrut(20));
		boxListOfPassengers.add(scrollPassengersForCurrentTrip);
		boxListOfPassengers.add(Box.createHorizontalStrut(20));
		Box boxListOfPassengersNavigate = Box.createVerticalBox();
		boxListOfPassengers.add(boxListOfPassengersNavigate);
		boxListOfPassengers.add(Box.createHorizontalStrut(20));
		boxListOfPassengersNavigate.add(Box.createVerticalStrut(20));
		boxListOfPassengersNavigate.add(buttonAddPassenger);
		boxListOfPassengersNavigate.add(Box.createVerticalStrut(20));
		boxListOfPassengersNavigate.add(buttonCreatePassenger);
		boxListOfPassengersNavigate.add(Box.createVerticalStrut(20));
		boxListOfPassengersNavigate.add(buttonCancel);
		boxListOfPassengersNavigate.add(Box.createVerticalStrut(20));
		
		
	    buttonCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dialog.setVisible(false);
	    		dialog.dispose();
			}
	    });
	    
	    buttonCreatePassenger.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showNewPassengerDialog(null);
			}
	    });
	    buttonAddPassenger.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Passenger passenger = listPassengers.getSelectedValue();
				for (int i=0; i<modelPassengerOfCurrentTrip.getSize(); i++){
					if (passenger.getId()==modelPassengerOfCurrentTrip.elementAt(i).getId()){
						return;
					}
				}
				modelPassengerOfCurrentTrip.addElement(passenger);
				textFieldNumberOfPassengers.setText(Integer.toString(modelPassengerOfCurrentTrip.size()));
			}
	    	
	    });
	    
	    
	    
	    
	    dialog.add(boxListOfPassengers);
	    dialog.setVisible(true);
	}

	
	private void showAddCargoDialog() {
	    final JDialog dialog = new JDialog();
	    dialog.setSize(500, 120);
	    
	    Box inputFieldsBox = Box.createHorizontalBox();
	    inputFieldsBox.add(Box.createHorizontalStrut(10));
	    inputFieldsBox.add(new JLabel("Название груза: "));
	    final JTextField cargoTextField = new JTextField();
	    inputFieldsBox.add(cargoTextField);
	    inputFieldsBox.add(Box.createHorizontalStrut(10));
	    inputFieldsBox.add(Box.createHorizontalStrut(10));
	    inputFieldsBox.add(new JLabel("Вес: "));
	    final JTextField weightTextField = new JTextField();
	    inputFieldsBox.add(weightTextField);
	    inputFieldsBox.add(new JLabel("кг"));
	    inputFieldsBox.add(Box.createHorizontalStrut(10));
	    
	    Box buttonsBox = Box.createHorizontalBox();
	    buttonsBox.add(Box.createGlue());
	    JButton cancelButton = new JButton("Отмена");
	    buttonsBox.add(cancelButton);
	    buttonsBox.add(Box.createHorizontalStrut(10));
	    JButton okButton = new JButton("OK");
	    buttonsBox.add(okButton);
	    buttonsBox.add(Box.createHorizontalStrut(10));
	    
	    Box dialogBox = Box.createVerticalBox();
	    dialogBox.add(Box.createVerticalStrut(10));
	    dialogBox.add(inputFieldsBox);
	    dialogBox.add(Box.createVerticalStrut(10));
	    dialogBox.add(buttonsBox);
	    dialogBox.add(Box.createVerticalStrut(10));
	    
	    dialog.setLayout(new BorderLayout());
	    dialog.add(dialogBox, BorderLayout.CENTER);
	    
	    dialog.setVisible(true);
	    
	    cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
	    		dialog.dispose();
			}
	    });
	    
	    okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Cargo cargo = new Cargo();
					cargo.setName(cargoTextField.getText());
					cargo.setWeight(Double.parseDouble(weightTextField.getText()));
					modelCargoOfCurrentTrip.addElement(cargo);
					double sum = 0;
					for (int i=0; i<modelCargoOfCurrentTrip.size(); i++) {
						sum += modelCargoOfCurrentTrip.elementAt(i).getWeight();
					}
					textFieldTotalWeight.setText(Double.toString(sum));
					dialog.setVisible(false);
		    		dialog.dispose();
				}
				catch(NumberFormatException e1){
					JOptionPane.showMessageDialog(MainFrame.this, 
	    					"Проверьте правильность введенных данных", 
	    					"Ошибка", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	    
	}
	
	
	private JComponent getTripInfoTab() {
		   JPanel panelInfo = new JPanel();
		   
		   JLabel labelInfoTripNumber = new JLabel("Номер рейса: ");
		   modelTrips = new DefaultComboBoxModel<Trip>();
		   final JComboBox<Trip> comboBoxInfoTripNumber = new JComboBox<Trip>(modelTrips);
		   comboBoxInfoTripNumber.setEditable(false);
		   JButton buttonGetInfo = new JButton("Вывести информацию");
		   JButton buttonExportInfo = new JButton("Экспорт в XML");
		   listInfoPassengersOfCurrentTrip = new JList<Passenger>();
		   JScrollPane scrollInfoPassengersOfCurrentTrip = new JScrollPane(listInfoPassengersOfCurrentTrip);
		   final JList<Cargo> listInfoCargoOfCurrentTrip = new JList<Cargo>();
		   JScrollPane scrollInfoCargoOfCurrentTrip = new JScrollPane(listInfoCargoOfCurrentTrip);
		   JLabel labelInfoTripFrom = new JLabel("Пункт отправления: ");
		   final JTextField textFieldInfoTripFrom = new JTextField("");
		   textFieldInfoTripFrom.setEditable(true);
		   JLabel labelInfoTripTo = new JLabel("Пункт назначения: ");
		   final JTextField textFieldInfoTripTo = new JTextField("");
		   textFieldInfoTripTo.setEditable(true);
		   JLabel labelInfoTripStatement = new JLabel("Состояние рейса: ");
		   final JTextField textFieldInfoTripStatement = new JTextField("");
		   textFieldInfoTripStatement.setEditable(true);
		   
		   Box boxInfo = Box.createVerticalBox();
		   panelInfo.add(boxInfo);
		   boxInfo.add(Box.createVerticalStrut(20));
		   Box boxInfoHead = Box.createHorizontalBox();
		   boxInfo.add(boxInfoHead);
		   boxInfoHead.add(Box.createHorizontalStrut(20));
		   boxInfoHead.add(labelInfoTripNumber);
		   boxInfoHead.add(Box.createHorizontalStrut(20));
		   boxInfoHead.add(comboBoxInfoTripNumber);
		   boxInfoHead.add(Box.createHorizontalStrut(20));
		   boxInfoHead.add(Box.createHorizontalGlue());
		   boxInfoHead.add(buttonGetInfo);
		   boxInfoHead.add(Box.createHorizontalStrut(20));
		   boxInfoHead.add(buttonExportInfo);
		   boxInfoHead.add(Box.createHorizontalStrut(20));
		   boxInfo.add(Box.createVerticalStrut(20));
		   Box boxInfoDetails = Box.createHorizontalBox();
		   boxInfo.add(boxInfoDetails);
		   boxInfo.add(Box.createVerticalStrut(20));
		   boxInfoDetails.add(Box.createHorizontalStrut(20));
		   boxInfoDetails.add(scrollInfoPassengersOfCurrentTrip);
		   boxInfoDetails.add(Box.createHorizontalStrut(20));
		   boxInfoDetails.add(scrollInfoCargoOfCurrentTrip);
		   boxInfoDetails.add(Box.createHorizontalStrut(20));
		   Box boxInfoDetailsNavigate = Box.createVerticalBox();
		   boxInfoDetails.add(boxInfoDetailsNavigate);
		   boxInfoDetails.add(Box.createHorizontalStrut(20));
		   boxInfoDetailsNavigate.add(Box.createVerticalStrut(20));
		   boxInfoDetailsNavigate.add(labelInfoTripFrom);
		   boxInfoDetailsNavigate.add(Box.createVerticalStrut(20));
		   boxInfoDetailsNavigate.add(textFieldInfoTripFrom);
		   boxInfoDetailsNavigate.add(Box.createVerticalStrut(20));
		   boxInfoDetailsNavigate.add(labelInfoTripTo);
		   boxInfoDetailsNavigate.add(Box.createVerticalStrut(20));
		   boxInfoDetailsNavigate.add(textFieldInfoTripTo);
		   boxInfoDetailsNavigate.add(Box.createVerticalStrut(20));
		   boxInfoDetailsNavigate.add(labelInfoTripStatement);
		   boxInfoDetailsNavigate.add(Box.createVerticalStrut(20));
		   boxInfoDetailsNavigate.add(textFieldInfoTripStatement);
		   boxInfoDetailsNavigate.add(Box.createVerticalStrut(20));
		   
		   updateComboBoxTrip();
		   
		   buttonGetInfo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Trip t = (Trip) comboBoxInfoTripNumber.getSelectedItem();
					DefaultListModel<Passenger> passengersModel = new DefaultListModel<Passenger>();
					for (Passenger p : t.getPassengers()) {
						passengersModel.addElement(p);
					}
					listInfoPassengersOfCurrentTrip.setModel(passengersModel);
					
					DefaultListModel<Cargo> cargoModel = new DefaultListModel<Cargo>();
					for (Cargo c : t.getCargo()) {
						cargoModel.addElement(c);
					}
					listInfoCargoOfCurrentTrip.setModel(cargoModel);
					
					textFieldInfoTripFrom.setText(t.getSourceCity().toString());
					textFieldInfoTripTo.setText(t.getDestinationCity().toString());
				}
			});
		   buttonExportInfo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						Trip t = (Trip) comboBoxInfoTripNumber.getSelectedItem();
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = dbf.newDocumentBuilder();
						Document d = builder.newDocument();
						
						Element rootElement = d.createElement("trip");
						rootElement.setAttribute("from", t.getSourceCity().toString());
						rootElement.setAttribute("to", t.getDestinationCity().toString());
						rootElement.setAttribute("duration", t.getTravelTimeString());
						rootElement.setAttribute("cost", t.getCost() + "$");
						
						Element vehicleElement = d.createElement("Vehicle");
						vehicleElement.setAttribute("name", t.getVehicle().getName());
						vehicleElement.setAttribute("type", t.getVehicle().getVehicleType().toString());
						vehicleElement.setAttribute("speed", Double.toString(t.getVehicle().getSpeed()));
						rootElement.appendChild(vehicleElement);
						
						Element passengersElement = d.createElement("passengers");
						for (Passenger p : t.getPassengers()) {
							Element e = d.createElement("passenger");
							e.setAttribute("Name", p.getLastName() + " " + p.getFirstName());
							e.setAttribute("Passport", p.getPassport());
							passengersElement.appendChild(e);
						}
						rootElement.appendChild(passengersElement);
						
						Element cargoElement = d.createElement("cargoList");
						for (Cargo c : t.getCargo()) {
							Element e = d.createElement("cargo");
							e.setAttribute("Name", c.getName());
							e.setAttribute("Weight", Double.toString(c.getWeight()));
							cargoElement.appendChild(e);
						}
						rootElement.appendChild(cargoElement);
						
						d.appendChild(rootElement);
						DOMImplementationLS ls = (DOMImplementationLS)d.getImplementation();
						LSSerializer ser = ls.createLSSerializer();
						LSOutput out = ls.createLSOutput();
						ser.setNewLine("\r\n");
						FileOutputStream output = new FileOutputStream("output.xml");
						try {
						  out.setByteStream(output);
						  ser.write(d, out);
						} finally {
						  output.close();
						}
						Runtime rt = Runtime.getRuntime();
						rt.exec("notepad output.xml");
						
					}
					catch (Exception e1) {
						e1.printStackTrace();
					}
				}
		});
		   
		   return panelInfo;
		}
	

	private TransportSystemDb getDb() throws ClassNotFoundException, SQLException {
		    Class.forName("com.mysql.jdbc.Driver");
		    return new TransportSystemDb(DriverManager.getConnection("jdbc:mysql://localhost:3306/transportsystem",
					"testUser", "test"));
		    }
	
	private void updateJListCity(){
	    	DefaultListModel<City> modelCity = new DefaultListModel<City>();
	    	if (modelCityFrom == null) {
	    		modelCityFrom = new DefaultComboBoxModel<City>();
	    	}
	    	if (modelCityTo == null) {
	    		modelCityTo = new DefaultComboBoxModel<City>();
	    	}
	    	modelCityFrom.removeAllElements();
	    	modelCityTo.removeAllElements();
	    	
	    	ArrayList<City> cities = new ArrayList<City>();
	    	try {
				TransportSystemDb db = getDb();
				cities = db.getCities();
				db.dispose();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	   
	    	for(City c : cities){
	    		modelCity.addElement(c);
	    		modelCityFrom.addElement(c);
	    		modelCityTo.addElement(c);
	    	}    
	    	listCities.setModel(modelCity);     
	    	listCities.setSelectedIndex(0);
	    	
	}
	
	private void updateJListPassenger(){
		DefaultListModel<Passenger> modelPassenger = new DefaultListModel<Passenger>();
    	ArrayList<Passenger> passengers = new ArrayList<Passenger>();
    	try {
			TransportSystemDb db = getDb();
			passengers = db.getPassengers();
			db.dispose();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	for(Passenger p : passengers){
    		modelPassenger.addElement(p);
    	}    
    	listPassengers.setModel(modelPassenger);     
    	listPassengers.setSelectedIndex(0);
	}
	
	private void updateComboBoxTrip() {
		ArrayList<Trip> trips = new ArrayList<Trip>();
    	try {
			TransportSystemDb db = getDb();
			trips = db.getTrips();
			db.dispose();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	
    	for (Trip t : trips) {
    		modelTrips.addElement(t);
    	}
	}
	
	
	private void showAvaliableVehicles() {
		final JDialog dialog = new JDialog();
		dialog.setSize(500, 500);
		
		final JList<Trip> list = new JList<Trip>();
		JScrollPane pane = new JScrollPane(list);
		
		Box boxDialog = Box.createVerticalBox();
		boxDialog.add(Box.createVerticalStrut(10));
		boxDialog.add(pane);
		
		Box buttonsBox = Box.createHorizontalBox();
		buttonsBox.add(Box.createHorizontalGlue());
		JButton buttonCancel = new JButton("Отмена");
		JButton buttonStartTrip = new JButton("Отправить");
		buttonsBox.add(buttonCancel);
		buttonsBox.add(Box.createHorizontalStrut(10));
		buttonsBox.add(buttonStartTrip);
		buttonsBox.add(Box.createHorizontalStrut(10));
		
		boxDialog.add(buttonsBox);
		
		dialog.add(boxDialog);
		dialog.setVisible(true);
		double cargoWeight = 0;
		for (int i=0; i<modelCargoOfCurrentTrip.size(); i++) {
			cargoWeight += modelCargoOfCurrentTrip.getElementAt(i).getWeight();
		}
		int passengerCount = modelPassengerOfCurrentTrip.size();
		DefaultListModel<Trip> avaliableVehicles = new DefaultListModel<Trip>();

		City sourceCity = (City)modelCityFrom.getSelectedItem();
		City destinationCity = (City) modelCityTo.getSelectedItem();
		
		for (IVehicle v : vehicles) {
			// проверка на вместимость по грузу и пассажирам
			if (v.getCargoCapacity() <= cargoWeight || v.getPassangerCapacity() <= passengerCount) {
				continue;
			}
			
			// проверка по типу транспортного средства
			switch (v.getVehicleType()) {
				case Aircraft: {
					if (!sourceCity.isHasAirport() || !destinationCity.isHasAirport()) {
						continue;
					}
					break;
				}
				case Water: {
					if (!sourceCity.isHasWaterport() || !destinationCity.isHasWaterport()) {
						continue;
					}
					break;
				}
				case Ground: {
					if (sourceCity.getCityContinent() != destinationCity.getCityContinent()) {
						continue;
					}
					break;
				}
			}
			
			Trip t = new Trip();
			t.setVehicle(v);
			t.setSourceCity(sourceCity);
			t.setDestinationCity(destinationCity);
			
			for (int i=0; i<modelCargoOfCurrentTrip.size(); i++) {
				t.getCargo().add(modelCargoOfCurrentTrip.elementAt(i));
			}
			for (int i=0; i<modelPassengerOfCurrentTrip.size(); i++) {
				t.getPassengers().add(modelPassengerOfCurrentTrip.elementAt(i));
			}
			
			avaliableVehicles.addElement(t);
		}
		
		list.setModel(avaliableVehicles);
		
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		
		buttonStartTrip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Trip t = list.getSelectedValue();
				if (t == null) {
					JOptionPane.showMessageDialog(MainFrame.this, 
	    					"Не выбрано транспортное средство", 
	    					"Ошибка", JOptionPane.ERROR_MESSAGE);
				}
				try {
					TransportSystemDb db = getDb();
					db.sendTrip(t);
					db.dispose();
					updateComboBoxTrip();
					dialog.setVisible(false);
					dialog.dispose();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
	}
}
