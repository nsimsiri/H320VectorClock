package Sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        HashMap<String, Integer> clock = new HashMap<String, Integer>();
        clock.put("Server", 1);
        System.out.println("[SERVER]Server started at 3000 " + clock.toString());
        try{
            while(true){
                Socket client = serverSocket.accept();
                System.out.println("[SERVER] client received at " + client.getInetAddress());
                InputStream clientStream = client.getInputStream();
                VectorClockInputStream vciStream = new VectorClockInputStream(clientStream, clock);
                String msg = (String)vciStream.readObjectOverride();
                System.out.println("MSG=== " + msg);
//                vciStream.close();

                clock = vciStream.getClock();
                System.out.format("[SERVER] NewClock= %s\n", clock.toString());

                OutputStream clientOStream = client.getOutputStream();
                VectorClockOutputStream vcoStream = new VectorClockOutputStream(clientOStream, clock);
                vcoStream.writeObjectOverride("Hi back from Server!");
//                vcoStream.close();
            }
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Server s1 = new Server();
    }
}
