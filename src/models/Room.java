package models;

import java.util.Arrays;
import java.util.List;

public class Room {

    private String name;
    private String bedType;
    private int capacity;
    private double price;
    private String imagePath;
    private boolean available;
    private List<String> features;
    private boolean featured;

    public Room() {}

    public Room(String name, String bedType, int capacity, double price, String imagePath,
	            boolean available, List<String> features, boolean featured) {
	    this.name = name;
	    this.bedType = bedType;
	    this.capacity = capacity;
	    this.price = price;
	    this.imagePath = imagePath;
	    this.available = available;
	    this.features = features;
	    this.featured = featured;
	}

    public String toCsv() {
        String featuresString = String.join("|", features);

        return name + "," +
               bedType + "," +
               capacity + "," +
               price + "," +
               imagePath + "," +
               available + "," +
               featuresString + "," +
               featured;
    }
    
    public static Room fromCsv(String line) {
        String[] data = line.split(",");

        List<String> features = Arrays.asList(data[6].split("\\|"));

        return new Room(
                data[0],
                data[1],
                Integer.parseInt(data[2]),
                Double.parseDouble(data[3]),
                data[4],
                Boolean.parseBoolean(data[5]),
                features,
                Boolean.parseBoolean(data[7])
        );
    }

    // getters
    public String getName() { return name; }
    public String getBedType() { return bedType; }
    public int getCapacity() { return capacity; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public boolean isAvailable() { return available; }
    public List<String> getFeatures() { return features; }

	public boolean isFeatured() {
		return featured;
	}
}