package shared;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = -8973404537389133541L;
    public int userID;
    public String userName;
    public User(String name) {
        
        this.userID = (new Date().toString()+name).hashCode();
        this.userName = name;
        if(name == "Anonymous")
        this.userName+= "-"+Integer.toString(Math.abs(userID)%100000);
    }
    public User(int id,String name) {
        this.userID = id;
        this.userName = name;
    }
    public String toString() {
        return userName;
    }
}
