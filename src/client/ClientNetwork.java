package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
import java.net.Socket;

import shared.*;

public class ClientNetwork {
    Socket socket = null;
    String host = "localhost";
    int port = 23333;

    ObjectInputStream receiver = null;
    ObjectOutputStream sender = null;
    

    public ClientNetwork(String host, int port, User userInfo) throws IOException{
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);

        sender = new ObjectOutputStream(socket.getOutputStream());
        sender.writeObject((Message)new Message(100,userInfo));
        sender.flush();

        receiver = new ObjectInputStream(socket.getInputStream());
    }
    public void sendMessage(Message message) throws IOException{
        sender.writeObject(message);
        sender.flush();
    }
    public Message readMessage() throws IOException,ClassNotFoundException {
            return (Message) receiver.readObject();
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
    }
}
