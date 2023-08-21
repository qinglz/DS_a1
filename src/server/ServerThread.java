package server;

import data_package.MyRequest;
import data_package.MyResponse;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Dictionary dictionary;

    public ServerThread(Socket socket, Dictionary dictionary) throws IOException{
        this.dictionary = dictionary;
        this.socket = socket;
        this.reader = new ObjectInputStream(socket.getInputStream());
        this.writer = new ObjectOutputStream(socket.getOutputStream());
    }
    public void run() {
        try {
            System.out.println("New connection accepted" + socket.getInetAddress() + ":" + socket.getPort());

            MyRequest myRequest;
            MyResponse myResponse;
            // 接收和发送数据，直到通信结束
            while ((myRequest = (MyRequest)reader.readObject()) != null) {
                System.out.println("from "+ socket.getInetAddress() + ":" + socket.getPort() + ">" + myRequest);
                if (myRequest.getOperation().equalsIgnoreCase("exit")){
                    break;
                }else if (myRequest.getOperation().equalsIgnoreCase("add")){
                    if (myRequest.getMeanings()!=null&&myRequest.getWord()!=null){
                        try{
                            dictionary.addNewWord(myRequest.getWord(),myRequest.getMeanings());
                            myResponse = new MyResponse(0,"Succeed",null);
                        }catch (IOException e){
                            myResponse = new MyResponse(2,"Fail to add word",null);
                        }
                    }else {
                        myResponse = new MyResponse(1,"Invalid input",null);
                    }
                    writer.writeObject(myResponse);
                }else if (myRequest.getOperation().equalsIgnoreCase("delete")){
                    if (myRequest.getWord()!=null){
                        try{
                            dictionary.deleteWord(myRequest.getWord());
                            myResponse = new MyResponse(0,"Succeed",null);
                        }catch (IOException e){
                            myResponse = new MyResponse(3,"Fail to delete word",null);
                        }
                    }else {
                        myResponse = new MyResponse(1,"Invalid input",null);
                    }
                    writer.writeObject(myResponse);
                }else if (myRequest.getOperation().equalsIgnoreCase("update")){
                    if (myRequest.getMeanings()!=null&&myRequest.getWord()!=null){
                        try{
                            dictionary.updateWord(myRequest.getWord(),myRequest.getMeanings());
                            myResponse = new MyResponse(0,"Succeed",null);
                        }catch (IOException e){
                            myResponse = new MyResponse(2,"Fail to update word",null);
                        }
                    }else {
                        myResponse = new MyResponse(1,"Invalid input",null);
                    }
                    writer.writeObject(myResponse);
                }else if (myRequest.getOperation().equalsIgnoreCase("meanings")){

                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 断开连接
                if(socket != null) socket.close();
                this.writer.close();
                this.reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
