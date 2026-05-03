package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class User {
	
	private String name;
	private String surname;
	private String password;
	private String email;
	private String phone;
	private String country;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date birthDate;
	private char gender;
	
	
	public User() {}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public User(String name, String surname, String email, String phone,
		String country, Date birthDate, char gender) {
			super();
			this.name = name;
			this.surname = surname;
			this.email = email;
			this.phone = phone;
			this.country = country;
			this.birthDate = birthDate;
			this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String toString() {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	    return "Nombre: " + name +
	           "\nApellido: " + surname +
	           "\nEmail: " + email +
	           "\nTeléfono: " + phone +
	           "\nPaís: " + country +
	           "\nFecha de nacimiento: " + sdf.format(birthDate) +
	           "\nGénero: " + gender;
	}
	
	public String toCsv() {
		return name + "," +
	           surname + "," +
	           email + "," +
	           phone + "," +
	           country + "," +
	           new SimpleDateFormat("yyyy-MM-dd").format(birthDate) + "," +
	           gender;
	}
	
	public static User fromCsv(String userData) {
		String data[] = userData.split(",");
		String name = data[0];
		String surname = data[1];
		String email = data[2];
		String phone = data[3];
	    String country = data[4];
	    Date birthDate = null; 
	    
	    try {
	        birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(data[5]);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    char gender = data[6].charAt(0);
	    	    
	    return new User(name, surname, email, phone, country, birthDate, gender);
	}
}