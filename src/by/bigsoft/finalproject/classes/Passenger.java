package by.bigsoft.finalproject.classes;

public class Passenger {
	private int id;
	private String firstName = "";
	private String lastName = "";
	private String passport = "";
	
	public boolean isValid(){
		return firstName!=null && !firstName.isEmpty() && lastName!=null && !lastName.isEmpty() 
				&& passport!=null && !passport.isEmpty(); //поправить проверку
	}

	public String getPassportSeries(){
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i<passport.length(); i++){
			if(Character.isAlphabetic(passport.charAt(i))){
				strb.append(passport.charAt(i));
			}
			else{
				break;
			}
		}
		return strb.toString();
	}
	public String getPassportNumber(){
		StringBuilder strb = new StringBuilder();
		for (int i = passport.length()-1; i>=0; i--){
			if(Character.isDigit(passport.charAt(i))){
				strb.insert(0, passport.charAt(i));
			}
			else{
				break;
			}
		}
		return strb.toString();
	}
	
	public String toString(){
		return lastName+" "+firstName+" ("+passport+")";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	
	
	
}
