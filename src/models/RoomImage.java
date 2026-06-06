package models;

public class RoomImage {

    private int imageId;
    private int typeId;
    private String imagePath;

    // Constructor vacío
    // usado en parseExtraImages() en RoomTypeFormController
    
    public RoomImage() {}

    // Constructor para crear una imagen extra para un roomType
    // Usado en getImagesByTypeId() en RoomImageRepository
    
    public RoomImage(int imageId, int typeId, String imagePath) {
        this.imageId = imageId;
        this.typeId = typeId;
        this.imagePath = imagePath;
    }
    
    // getters y setters

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}