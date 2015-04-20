package Sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by NatchaS on 4/19/15.
 */
public class Client {
    public Client(String name) throws IOException {
        Socket socket = new Socket("localhost", 3000);
        OutputStream outputStream = socket.getOutputStream();
        HashMap<String, Integer> clock = new HashMap<String, Integer>();
        clock.put(name, 1);
        VectorClockOutputStream vcoStream = new VectorClockOutputStream(outputStream, clock);
        vcoStream.writeObjectOverride(String.format("Hi from %s", name));

        try{
            InputStream inputStream = socket.getInputStream();
            VectorClockInputStream vciStream = new VectorClockInputStream(inputStream, clock);
            String msg = (String)vciStream.readObjectOverride();
            System.out.format("[%s] clock= %s\n", msg, vciStream.getClock().toString());

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws IOException{
        Client c1 = new Client("Natcha");
    }
}

