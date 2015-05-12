package Sample;

import VectorClockLogger.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NatchaS on 4/19/15.
 */
public class Server {
    ServerSocket serverSocket;
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(3000);
        final VectorClockLogger vlogger = new VectorClockLogger("Server");
        System.out.println("Server started at 3000 " + vlogger.toString() + ". 'ls' to see Server's vector clock");
        ExecutorService tpool = Executors.newFixedThreadPool(10);
        tpool.submit(new Runnable(){
           @Override
            public void run(){
               String msg = "";
               Scanner in = new Scanner(System.in);
               while(!msg.equals("Q")){
                   msg = in.nextLine();
                   if (msg.equals("ls")){
                       System.out.println(vlogger);
                   }
               }
           }
        });
        try{
            while(true){
                Socket client = serverSocket.accept();
                tpool.submit(new ClientHandler(client, vlogger));

            }
        } finally {
            return;
        }
    }

    public class ClientHandler implements Runnable {
        Socket socket;
        VectorClockLogger vlogger;
        public ClientHandler(Socket socket, VectorClockLogger vlogger){
            this.socket = socket;
            this.vlogger = vlogger;
            vlogger.log("Accepted Connection");
        }
        @Override
        public void run(){
            String msg = "";
            try{
                while(!msg.equals("Q")){
                    ObjectInputStream in = new ObjectInputStream(this.socket.getInputStream());
                    msg = (String)in.readObject();
                    if (msg.equals("Q")) break;
                    vlogger.deserialize(in, ": " + msg);

                    ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
                    msg = msg.toUpperCase();
                    out.writeObject(msg);
                    out.writeObject(vlogger.serialize());
                }
                vlogger.log("disconnected.");
            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Server s1 = new Server();
    }
}
