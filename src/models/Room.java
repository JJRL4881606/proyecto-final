package models;

public class Room {

    private int roomId;
    private int roomNumber;
    private int floor;
    private int typeId;
    private boolean available;

    public Room(){}

    public Room(int roomId, int roomNumber, int floor, int typeId, boolean available){
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.typeId = typeId;
        this.available = available;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public int getTypeId() {
        return typeId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setRoomId(int roomId){
        this.roomId = roomId;
    }

    public void setRoomNumber(int roomNumber){
        this.roomNumber = roomNumber;
    }

    public void setFloor(int floor){
        this.floor = floor;
    }

    public void setTypeId(int typeId){
        this.typeId = typeId;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }
}