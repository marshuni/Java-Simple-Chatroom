package server;

import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

import java.io.*;

import shared.*;

class ServerHandler implements Runnable{
    Socket socket = null;

    ObjectInputStream receiver = null;
    ObjectOutputStream sender = null;

    User userInfo = null;

    private static final int TIMEOUT = 300000; // 超时时间为5分钟，单位为毫秒

    public ServerHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try{
            InputStream receiveStream = socket.getInputStream();
            receiver = new ObjectInputStream(receiveStream);
            sender = new ObjectOutputStream(socket.getOutputStream());
            sender.writeObject((Message)new Message(100,"成功连接，欢迎来到聊天室！"));
            sender.flush();
            //ObjectInputStream的构造函数在收到一个对象之后才会继续，因此双方建立连接后需要先发送一条消息。

            Message nowMessage = (Message)receiver.readObject();
            userInfo = nowMessage.user;
            System.out.println("[Handler:"+userInfo.userName+"]建立连接："+nowMessage.content);

            // 计时器，当输入流超过5分钟没有输入时，自动断开连接。
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 在计时器超时时停止程序
                    System.out.println("[Handler:\"+userInfo.userName+\"]连接超时，断开连接");
                    close();
                }
            }, TIMEOUT);

            while(true){
                if(receiveStream.available() > 0) {
                    nowMessage = (Message)receiver.readObject();
                    System.out.println("[Handler:"+userInfo.userName+"]收到消息："+nowMessage.content);
                    ServerCore.forward(nowMessage);
                    if(nowMessage.status==150)
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            close();
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
    public void close() {
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