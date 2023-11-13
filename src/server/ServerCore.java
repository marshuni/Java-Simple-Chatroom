package server;

import java.net.*;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shared.*;

public class ServerCore {
    static int port=23333,maxConnection=12;
    static ExecutorService service = null;
    static final HashSet<ServerHandler> connections = new HashSet<ServerHandler>();
    
    static void init(int nowPort,int maxConnection) {
        port = nowPort;
        service = Executors.newFixedThreadPool(maxConnection);
        System.out.printf("[Core]服务器已启动，监听端口：%d,最大连接数：%d\n",port,maxConnection);
    }
    static void newThread(Socket socket) {
        ServerHandler nowHandler = new ServerHandler(socket);
        connections.add(nowHandler);
        System.out.printf("[Core]传入新的连接，当前活跃连接数：%d\n",connections.size());
        service.execute(nowHandler);
    }
    static void forward(Message message) {
        for(ServerHandler connection:connections)
            connection.receiveMessage(message);
    }
    static public void deleteThread(ServerHandler nowHandler) {
        connections.remove(nowHandler);
        System.out.printf("[Core]断开一个连接，当前活跃连接数：%d\n",connections.size());
    }

}
