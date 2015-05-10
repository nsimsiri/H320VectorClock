package Sample;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import VectorClockLogger.*;

/**
 * Created by NatchaS on 4/19/15.
 */
public class Client {
    public Client(String name) throws IOException {
        Socket socket = new Socket("localhost", 3000);
        VectorClockLogger vlogger = new VectorClockLogger("Client");
        try{
            String msg = "Hi From Client";
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            Serializable serializedVClock = vlogger.serialize("Sending to localhost:3000");
            out.writeObject(serializedVClock);
            out.writeObject(msg);

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            msg = (String)in.readObject();
            vlogger.deserialize(in, String.format("msg from Server: %s", msg));

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Client c1 = new Client("Natcha");
    }
}

