package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class User {
	
	private int id;
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

	public User(int id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	public User(String name, String surname, String email, String phone,
		String country, Date birthDate, char gender) {
			this.name = name;
			this.surname = surname;
			this.email = email;
			this.phone = phone;
			this.country = country;
			this.birthDate = birthDate;
			this.gender = gender;
	}

	
	public User(int id, String name, String surname, String email, String phone,
		String country, Date birthDate, char gender) {
			this.id = id;
			this.name = name;
			this.surname = surname;
			this.email = email;
			this.phone = phone;
			this.country = country;
			this.birthDate = birthDate;
			this.gender = gender;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public void setPassword(String password) {
		this.password = password;
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
}