package client;

import shared.*;

public class ChatroomClient {
    static String host = "server.marshuni.fun";
    static int port = 23333;
    static User userInfo = new User("100", "marshuni");
    static ClientNetwork connection = null;

    public static void main(String[] args) {

        ClientGUI gui = new ClientGUI();
        Thread threadGUI = new Thread(gui);

        try{
            connection = new ClientNetwork(host, port,userInfo);
            threadGUI.start();
            while(true) {
                if(threadGUI.isAlive() == false) {
                    break;
                }
                gui.displayMessage(connection.readMessage());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(connection != null) {
                Message closeMessage = new Message(150,userInfo.toString()+"离开了。");
                try {
                    connection.sendMessage(closeMessage);
                }catch(Exception e){
                    e.printStackTrace();
                }
                connection.close();
            }
        }
    }

    public static void sendMessage(String messageStr) {
        try{
            Message message = new Message(userInfo, messageStr);
            connection.sendMessage(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
