package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String args[]){
        Dictionary dictionary = null;
        try {
            dictionary = new Dictionary(args[1]);
        } catch (IOException e) {
            System.out.println("Read Dictionary File Failed.\nMake Sure .csv File Exists.");
//            e.printStackTrace();
            return;
        }
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running now.");
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Server running failed.");
            return;
        }
        while (true) {
            Socket socket;
            try {
                // 接教客户连接
                socket = serverSocket.accept();
                // 创建一个工作线程
                Thread workThread = new Thread(new ServerThread(socket,dictionary));
                // 启动工作线程
                workThread.start();
            } catch (IOException e) {
                System.out.println("Fail To Start WorkerThread");
//                e.printStackTrace();
            }
        }
    }

}
