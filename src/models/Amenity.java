package models;

public class Amenity {

    private int amenityId;
    private String name;
    private String icon;

    // Constructor para crear una amenidad con todos sus datos
    // Usado en getAmenities() y getAmenitiesByRoomType() en AmenityRepository
    // y en handleSave() en AmenityFormController

    public Amenity(int amenityId, String name, String icon) {
        this.amenityId = amenityId;
        this.name = name;
        this.icon = icon;
    }
    
    // getters y setters

    public int getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(int amenityId) {
        this.amenityId = amenityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon=icon;
    }

    @Override
    public String toString(){
        return name;
    }
}