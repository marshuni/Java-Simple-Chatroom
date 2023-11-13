package server;

import java.net.*;
import java.io.*;

import shared.*;

class ServerHandler implements Runnable{
    Socket socket = null;

    ObjectInputStream receiver = null;
    ObjectOutputStream sender = null;

    User userInfo = null;

    public ServerHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try{
            receiver = new ObjectInputStream(socket.getInputStream());
            sender = new ObjectOutputStream(socket.getOutputStream());
            sender.writeObject((Message)new Message(100,"成功连接，欢迎来到聊天室！"));
            sender.flush();
            //ObjectInputStream的构造函数在收到一个对象之后才会继续，因此双方建立连接后需要先发送一条消息。

            Message nowMessage = (Message)receiver.readObject();
            userInfo = nowMessage.user;
            System.out.println("[Handler:"+userInfo.userName+"]建立连接："+nowMessage.content);

            while(true){
                if((nowMessage = (Message)receiver.readObject()) == null){
                    break;
                }
                System.out.println("[Handler:"+userInfo.userName+"]收到消息："+nowMessage.content);
                ServerCore.forward(nowMessage);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket = null;
            if(receiver != null){
                try {
                    receiver.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            receiver = null;
            if(sender != null){
                try {
                    sender.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sender = null;
            ServerCore.deleteThread(this);
        }
    }
    public void receiveMessage(Message message){
        try {
            sender.writeObject(message);
            sender.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}