package models;

public class Room {

    private int roomId;
    private int roomNumber;
    private int floor;
    private int typeId;
    private String status;

    // Constructor para crear una habitación con todos sus datos.
    // usado en getRooms(), findById(int id), findByTypeId(int typeId), getByType(int typeId) en RoomRepository
    // y handleSave() en RoomFormController
    public Room(int roomId, int roomNumber, int floor, int typeId, String status){
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.typeId = typeId;
        this.status = status;
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

    public String getStatus(){
        return status;
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

    public void setStatus(String status){
        this.status = status;
    }
}