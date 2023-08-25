package client;
import java.io.*;


public class ClientMain {
    public static void main(String[] args) {
        try{
        ClientSocketChannel channel = new ClientSocketChannel(args[0], Integer.parseInt(args[1]));
        ClientWindow clientWindow = new ClientWindow(channel);
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Cannot connect to serve, make sure you run server first.");
            e.printStackTrace();
        }
    }
}
