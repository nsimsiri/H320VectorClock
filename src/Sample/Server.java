package Sample;

import VectorClockLogger.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by NatchaS on 4/19/15.
 */
public class Server {
    ServerSocket serverSocket;
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(3000);
        VectorClockLogger vlogger = new VectorClockLogger("Server");
        System.out.println("[SERVER]Server started at 3000 " + vlogger.toString());
        try{
            while(true){
                Socket client = serverSocket.accept();
                InputStream clientStream = client.getInputStream();
                ObjectInputStream in = new ObjectInputStream(clientStream);
                vlogger.deserialize(in, "Client received at " + client.getInetAddress());
                String msg = (String)in.readObject();
                vlogger.log(msg);

                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                Serializable serializedClock = vlogger.serialize("Sending to Client");
                msg = msg.toUpperCase();
                out.writeObject(msg);
                out.writeObject(serializedClock);
            }
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Server s1 = new Server();
    }
}
