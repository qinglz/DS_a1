package server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public ServerThread(Socket socket) throws IOException{
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(),true);
    }
    public void run() {
        try {
            System.out.println("New connection accepted" + socket.getInetAddress() + ":" + socket.getPort());

            String msg = null;
            // 接收和发送数据，直到通信结束
            while ((msg = reader.readLine()) != null) {
                System.out.println("from "+ socket.getInetAddress() + ":" + socket.getPort() + ">" + msg);
                writer.println(msg);
//                System.out.println("sent"+msg);
                if (msg.equalsIgnoreCase("exit")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 断开连接
                if(socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
