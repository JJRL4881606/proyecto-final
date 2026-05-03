package models;

import java.util.Arrays;
import java.util.List;

public class RoomType {

    private String typeId;
    private String name;
    private String bedType;
    private int capacity;
    private double price;
    private String imagePath;
    private List<String> features;
    private boolean featured;

    public RoomType() {}

    public RoomType(String typeId, String name, String bedType, int capacity,
                    double price, String imagePath,
                    List<String> features, boolean featured) {
        this.typeId = typeId;
        this.name = name;
        this.bedType = bedType;
        this.capacity = capacity;
        this.price = price;
        this.imagePath = imagePath;
        this.features = features;
        this.featured = featured;
    }

    public String toCsv() {
        String featuresString = String.join("|", features);

        return typeId + "," +
               name + "," +
               bedType + "," +
               capacity + "," +
               price + "," +
               imagePath + "," +
               featuresString + "," +
               featured;
    }

    public static RoomType fromCsv(String line) {
        String[] data = line.split(",");

        List<String> features = Arrays.asList(data[6].split("\\|"));

        return new RoomType(
                data[0], // typeId
                data[1], // name
                data[2], // bedType
                Integer.parseInt(data[3]),
                Double.parseDouble(data[4]),
                data[5], // imagePath
                features,
                Boolean.parseBoolean(data[7])
        );
    }

    // getters
    public String getTypeId() { return typeId; }
    public String getName() { return name; }
    public String getBedType() { return bedType; }
    public int getCapacity() { return capacity; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public List<String> getFeatures() { return features; }
    public boolean isFeatured() { return featured; }
}