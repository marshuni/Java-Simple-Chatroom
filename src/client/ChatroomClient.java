package client;

import java.net.SocketException;

import shared.*;

public class ChatroomClient {
    static String host = "127.0.0.1";
    static int port = 23333;
    static User userInfo = new User("100", "marshuni");
    // TODO: 上述内容的初始化、聊天室信息
    static ClientNetwork connection = null;
    static ClientGUI gui = null;
    static Thread messeageHandler = null;
    public static void main(String[] args) {

        gui = new ClientGUI();
        gui.init();

        try{
            connection = new ClientNetwork(host, port,userInfo);
            messeageHandler = new Thread(new ClientMessageHandler());
            messeageHandler.start();
            while(true) {
                if(gui.getWindowStatus() != true){
                    ClientMessageHandler.exit = true;
                    break;
                }
                //若不执行该休眠动作，在运行时主线程无法及时检测到退出动作
                Thread.sleep(1000);
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
class ClientMessageHandler implements Runnable {
    
    public static boolean exit = false;
    @Override
    public void run(){
        while (!exit) {
            try{
                ChatroomClient.gui.displayMessage(ChatroomClient.connection.readMessage());
            }catch(SocketException e){

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
