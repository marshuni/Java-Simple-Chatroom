package server;

import java.net.*;
import java.io.*;

public class ChatroomServer {
    public static void main(String[] args) {
        int port = 23333;
        ServerSocket server = null;
        
        try{
            server = new ServerSocket(port);
            ServerCore.init(port, 16);
            System.out.println("Server started!");
            while(true){
                Socket socket = server.accept();
                ServerCore.newThread(socket);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(server != null){
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            server = null;
        }
    }
}
