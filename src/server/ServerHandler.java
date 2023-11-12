package server;

import java.net.*;
import java.io.*;

class ServerHandler implements Runnable{
    Socket socket = null;
    BufferedReader reader = null;
    PrintWriter writer = null;

    public ServerHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try{
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));
            writer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            String readMessage = null;
            while(true){
                if((readMessage = reader.readLine()) == null){
                    break;
                }
                System.out.println("[Handler]收到消息："+readMessage);
                ServerCore.forward(readMessage);
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
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            reader = null;
            if(writer != null){
                writer.close();
            }
            writer = null;
            ServerCore.deleteThread(this);
        }
    }
    public void receiveMessage(String message) {
        writer.println(message);
        writer.flush();
    }
}