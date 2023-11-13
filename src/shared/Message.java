package shared;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {
    public int status; // 状态码为100表示请求消息(or系统消息)，状态码为200表示包含内容的消息
    public User user;
    public String content;
    public Date time;
    public Message(int status,String content) {
        // 系统传送消息
        this.status = 100;
        this.user = new User("0","Server");
        this.content = content;
        this.time = new Date();
    }
    public Message(int status,User user) {
        // 系统传送消息
        this.status = 100;
        this.user = user;
        content = "传送用户信息："+user.userName;
        this.time = new Date();
    }
    public Message(User user,String content) {
        // 用户传送消息
        this.status = 200;
        this.user = user;
        this.content = content;
        this.time = new Date();
    }
    public String output() {
        if(content!= null){
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
            return user.userName+ "("+ ft.format(time)+ "): "+ content;
        }
        else return "";
    }
}
