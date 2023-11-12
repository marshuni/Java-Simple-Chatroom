package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientNetwork {
    Socket socket = null;
    String host = "server.marshuni.fun";
    int port = 23333;

    BufferedReader reader = null;
    PrintWriter writer = null;

    public ClientNetwork(String host, int port) throws IOException{
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);

        reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), "UTF-8"));
        writer = new PrintWriter(
                socket.getOutputStream(), true);
    }
    public void sendMessage(String message){
        writer.println(message);
        writer.flush();
    }
    public String readMessage() throws IOException {
            return reader.readLine();
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
    }
}
