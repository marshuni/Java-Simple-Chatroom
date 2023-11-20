package shared;

import java.io.Serializable;

public class Room implements Serializable{
    public int roomID;
    public String roomName;
    public Room(String name) {
        this.roomID = name.hashCode();
        this.roomName = name;
    }
    public String toString() {
        return roomName;
    }
}
