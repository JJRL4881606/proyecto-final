package models;

public class Room {

    private String id;         
    private String typeId;
    private int floor;

    public Room() {}

    public Room(String id, String typeId, int floor) {
        this.id = id;
        this.typeId = typeId;
        this.floor = floor;
    }

    public String toCsv() {
        return id + "," +
               typeId + "," +
               floor;
    }

    public static Room fromCsv(String line) {
        String[] data = line.split(",");

        return new Room(
            data[0],
            data[1],
            Integer.parseInt(data[2])
        );
    }

    // getters
    public String getId() { return id; }
    public String getTypeId() { return typeId; }
    public int getFloor() { return floor; }
}