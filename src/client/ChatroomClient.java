package client;

import java.net.SocketException;

import shared.*;

public class ChatroomClient {
    static String host = "127.0.0.1";
    static int port = 23333;
    static User userInfo = null;
    static Room roomInfo = null;
    static ClientNetwork connection = null;
    static ClientGUI gui = null;
    static Thread messeageHandler = null;
    public static void main(String[] args) {

        gui = new ClientGUI();
        
        try {
            host = gui.getInputInfo("请输入服务器地址(默认为localhost)", host);
            port = Integer.parseInt(gui.getInputInfo("请输入服务器端口(默认为23333)", "23333"));
            userInfo = new User(gui.getInputInfo("请输入用户名(默认为匿名)", "Anonymous"));
            roomInfo = new Room(gui.getInputInfo("请输入聊天室名称(默认为main)", "main"));
        }catch(Exception e){
            gui.displayInfo("输入有误，程序将退出。");
            System.exit(0);
        }

        gui.init(roomInfo.roomName);

        try{
            connection = new ClientNetwork(host, port, userInfo, roomInfo);
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
                    Thread.sleep(500);
                }catch(Exception e){
                    e.printStackTrace();
                }
                connection.close();
            }
        }
    }

    public static void sendMessage(String messageStr) {
        try{
            Message message = new Message(userInfo, roomInfo, messageStr);
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
