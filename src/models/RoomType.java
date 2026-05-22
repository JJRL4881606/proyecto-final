package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomType {

    private int typeId;
    private String name;
    private String bedType;
    private int capacity;
    private double price;
    private String imagePath;
    private List<Amenity> amenities;
    private boolean featured;
    
    private String description;
    private List<String> extraImages;
    
    public RoomType() {}

    public RoomType(int typeId, String name, String bedType, int capacity,
        double price, String imagePath, List<Amenity> amenities,
        boolean featured,String description, List<String> extraImages) {
	        this.typeId = typeId;
	        this.name = name;
	        this.bedType = bedType;
	        this.capacity = capacity;
	        this.price = price;
	        this.imagePath = imagePath;
	        this.amenities = amenities;
	        this.featured = featured;
	        
	        this.description = description;
	        this.extraImages = extraImages;
    }

    
    public static List<String> stringToFeatures(String text){
        if(text == null || text.isEmpty()){
            return List.of();
        }

        return Arrays.stream(text.split("\\|"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();    
    }
    
    public String extraImagesToString(){
        if(extraImages == null){
            return "";
        }

        return String.join("|", extraImages);
    }
    
    public static List<String> stringToImages(String text) {

        List<String> list = new ArrayList<>();

        if (text == null || text.trim().isEmpty()) {
            return list;
        }

        String[] parts = text.split("\\|");

        for (String p : parts) {
            if (p != null && !p.trim().isEmpty()) {
                list.add(p.trim());
            }
        }

        return list;
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

    public List<Amenity> getAmenities(){
        return amenities;
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
    
    public void setName(String name){
    	this.name = name; 
    }
    
    public void setBedType(String bedType){
    	this.bedType = bedType; 
    }
    
    public void setCapacity(int capacity){
    	this.capacity = capacity; 
    }
    
    public void setPrice(double price){
    	this.price = price;
    }
    
    public void setImagePath(String imagePath){
    	this.imagePath = imagePath; 
    }
    
    public void setAmenities(List<Amenity> amenities){
        this.amenities=amenities;
    }
    
    public void setFeatured(boolean featured){
    	this.featured = featured; 
    }

    public boolean isFeatured() {
        return featured;
    }
}