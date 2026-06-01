package models;

public class RoomImage {

    private int imageId;
    private int typeId;
    private String imagePath;

    public RoomImage() {}

    public RoomImage(int imageId, int typeId, String imagePath) {
        this.imageId = imageId;
        this.typeId = typeId;
        this.imagePath = imagePath;
    }

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