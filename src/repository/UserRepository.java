package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import models.User;

public class UserRepository {

	private final String FILE = "src/assets/files/users.csv";
	
	public void save(User user) throws IOException {
		
		try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE, true), StandardCharsets.UTF_8))) {
			writer.write(user.toCsv());
			writer.newLine();
		}
		
	}
	
	public List<User> getUsers() throws IOException {
		
		List<User> users = new ArrayList<User>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
			String line;
			
			while((line = reader.readLine()) != null) {
				User user = User.fromCsv(line);
				users.add(user);
			}
		}
		
		return users;
		
	}
	
			
}










