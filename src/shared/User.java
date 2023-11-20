package shared;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -8973404537389133541L;
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
