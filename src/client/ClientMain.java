package client;

import data_package.MyRequest;
import data_package.MyResponse;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientMain {
    public static void main(String[] args) {
        try{
        ClientSocketChannel channel = new ClientSocketChannel("127.0.0.1", 8000);
        ClientWindow clientWindow = new ClientWindow(channel);
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Cannot connect to serve, make sure you run server first.");
            e.printStackTrace();
        }
    }
}
