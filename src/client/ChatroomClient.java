package client;

import java.util.*;


public class ChatroomClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 23333;
        ClientNetwork connection = null;

        Scanner s = new Scanner(System.in);
        try{
            connection = new ClientNetwork(host, port);
            String message = null;
            
            while(true){
                message = s.nextLine();
                if(message.equals("exit")){
                    break;
                }
                connection.sendMessage(message);
                System.out.println(connection.readMessage());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(connection != null)
                connection.close();
        }
        s.close();
    }
}
