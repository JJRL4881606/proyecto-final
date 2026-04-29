package repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class UserRepository {

	private final String FILE = "src/assets/files/users.json";
	
	private final ObjectMapper mapper = 
			new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	
	public void save(User user) throws IOException {
		List<User> users = getUsers();
		users.add(user);
		updateAll(users);
	}
	
	public List<User> getUsers() throws IOException {
		
		File file = new File(FILE);	
		
		if(!file.exists() || file.length() == 0) {
			return new ArrayList<>();
		}
		
		return mapper.readValue(
			file, 
			new TypeReference<List<User>>() {}
		);
	}			
	
	public void updateAll(List<User> users) throws IOException {
	    mapper.writeValue(new File(FILE), users);
	}
	
	public void delete(int index) throws IOException {
		List<User> users = getUsers();
		users.remove(index);
		updateAll(users);
	}
	
	public void update(int index, User updatedUser) throws IOException {
		List<User> users = getUsers();
		users.set(index, updatedUser);
		updateAll(users);
	}
}