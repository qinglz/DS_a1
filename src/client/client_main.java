package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client_main {
    public static void main(String[] args) {
        try {
            // 创建Socket对象，指定服务端的IP地址和端口号
            Socket socket = new Socket("127.0.0.1", 8000);

            // 获取输入流和输出流 输入流和输出流是通过socket对象来进行数据传输的。
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 从控制台读取用户输入
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                System.out.println("Enter a new message(enter 'exit' to end conversation):");
                message = reader.readLine();

                if (message.equalsIgnoreCase("exit")) {
                    // 如果用户输入 'exit'，发送终止标志给服务端并退出循环
                    out.println("exit");
                    break;
                }

                // 将用户输入的信息发送给服务端
                out.println(message);
//                System.out.println(message);
                // 接收服务端的响应并打印
                String response = in.readLine();
                System.out.println("Server's response: " + response);
            }
            if(socket != null) socket.close();
            // 关闭连接
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
