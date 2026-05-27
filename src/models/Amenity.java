package models;

public class Amenity {

    private int amenityId;
    private String name;
    private String icon;

    public Amenity() {}

    public Amenity(int amenityId, String name, String icon) {
        this.amenityId = amenityId;
        this.name = name;
        this.icon = icon;
    }

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