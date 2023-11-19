package shared;

import java.io.Serializable;

public class User implements Serializable {
    public String userID;
    public String userName;
    public User(String id,String name) {
        this.userID = id;
        this.userName = name;
    }
    public String toString() {
        return userName;
    }
}
