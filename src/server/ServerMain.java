package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String args[]){
        int port = 8000;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running now.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server running failed.");
        }
        while (true) {
            Socket socket;
            try {
                // 接教客户连接
                socket = serverSocket.accept();
                // 创建一个工作线程
                Thread workThread = new Thread(new ServerThread(socket));
                // 启动工作线程
                workThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
