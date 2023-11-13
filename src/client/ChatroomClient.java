package client;

import java.util.*;

import shared.*;

public class ChatroomClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 23333;
        User userInfo = new User("100", "marshuni");
        ClientNetwork connection = null;

        Scanner s = new Scanner(System.in);
        try{
            connection = new ClientNetwork(host, port,userInfo);

            
            while(true){
                Message nowMessage = new Message(userInfo,s.nextLine());
                if(nowMessage.content.equals("exit")){
                    break;
                }
                connection.sendMessage(nowMessage);
                System.out.println(connection.readMessage().output());
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
