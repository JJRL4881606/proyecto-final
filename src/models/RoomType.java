package models;

import java.util.Arrays;
import java.util.List;

public class RoomType {

    private int typeId;
    private String name;
    private String bedType;
    private int capacity;
    private double price;
    private String imagePath;
    private List<String> features;
    private boolean featured;
    
    private String description;
    private List<String> extraImages;
    
    public RoomType() {}

    public RoomType(int typeId, String name, String bedType, int capacity,
        double price, String imagePath, List<String> features,
        boolean featured,String description, List<String> extraImages) {
	        this.typeId = typeId;
	        this.name = name;
	        this.bedType = bedType;
	        this.capacity = capacity;
	        this.price = price;
	        this.imagePath = imagePath;
	        this.features = features;
	        this.featured = featured;
	        
	        this.description = description;
	        this.extraImages = extraImages;
    }

    public String featuresToString(){
        if(features == null){
            return "";
        }

        return String.join("|", features);
    }
    
    public static List<String> stringToFeatures(String text){
        if(text == null || text.isEmpty()){
            return List.of();
        }

        return Arrays.asList(
            text.split("\\|")
        );
    }
    
    public String extraImagesToString(){
        if(extraImages == null){
            return "";
        }

        return String.join("|", extraImages);
    }
    
    public static List<String> stringToImages(String text){
        if(text == null || text.isEmpty()){
            return List.of();
        }

        return Arrays.asList(
            text.split("\\|")
        );
    }

    //getters y setters
    public int getTypeId() {
        return typeId;
    }
    
    public void setTypeId(int typeId){
    	this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public String getBedType() {
        return bedType;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<String> getFeatures() {
        return features;
    }
    
    public String getDescription(){
        return description;
    }

    public List<String> getExtraImages(){
        return extraImages;
    }
    
    public void setDescription(String description){
        this.description = description;
    }

    public void setExtraImages(List<String> extraImages){
        this.extraImages = extraImages;
    }
    
    public void setName(String name){ this.name = name; }
    public void setBedType(String bedType){ this.bedType = bedType; }
    public void setCapacity(int capacity){ this.capacity = capacity; }
    public void setPrice(double price){ this.price = price; }
    public void setImagePath(String imagePath){ this.imagePath = imagePath; }
    public void setFeatures(List<String> features){ this.features = features; }
    public void setFeatured(boolean featured){ this.featured = featured; }

    public boolean isFeatured() {
        return featured;
    }
}