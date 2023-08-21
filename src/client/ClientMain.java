package client;

import data_package.MyRequest;
import data_package.MyResponse;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientMain {
    public static void main(String[] args) {
        try {
            // 创建Socket对象，指定服务端的IP地址和端口号
            Socket socket = new Socket("127.0.0.1", 8000);

            // 获取输入流和输出流 输入流和输出流是通过socket对象来进行数据传输的。
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            // 从控制台读取用户输入
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                System.out.println("Enter a new message(enter 'exit' to end conversation):");
                message = reader.readLine();

                if (message.equalsIgnoreCase("exit")) {
                    // 如果用户输入 'exit'，发送终止标志给服务端并退出循环
                    MyRequest myRequest = new MyRequest("exit",null,null);
                    out.writeObject(myRequest);
                    break;
                }
                String[] messageS = message.split(",");
                List<String> ms = new ArrayList<>();
                String op = null, word = null;
                for (int i = 0; i< messageS.length;i++){
                    if (i==0){
                        op = messageS[i];
                    }else if(i==1){
                        word = messageS[i];
                    }else {
                        ms.add(messageS[i]);
                    }
                }
                MyRequest myRequest = new MyRequest(op,word,ms);
                out.writeObject(myRequest);
                // 将用户输入的信息发送给服务端

//                System.out.println(message);
                // 接收服务端的响应并打印
                MyResponse response = (MyResponse)in.readObject();
                System.out.println("Server's response: " + response);
            }
            socket.close();
            in.close();
            out.close();
            // 关闭连接
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
