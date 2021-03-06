package Sample;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import VectorClockLogger.*;

/**
 * Created by NatchaS on 4/19/15.
 */
public class Client {
    public Client(String name) throws IOException {
        Socket socket = new Socket("localhost", 3000);
        VectorClockLogger vlogger = new VectorClockLogger(new Integer(socket.getLocalPort()).toString());
        System.out.format("Client @%s | press Q to disconnect | Type message you want to send to the echo server\n", vlogger.hostname());
        try{
            Scanner cin = new Scanner(System.in);
            String msg = "";
            while(!msg.equals("Q")){
                msg = cin.nextLine();
                if (msg.equals("Q")) break;
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                Serializable serializedVClock = vlogger.serialize("Sending to localhost:3000");
                out.writeObject(msg);
                out.writeObject(serializedVClock);

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                msg = (String)in.readObject();
                vlogger.deserialize(in, String.format("msg from Server: %s", msg));
            }

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Client c1 = new Client("Natcha");
    }
}

