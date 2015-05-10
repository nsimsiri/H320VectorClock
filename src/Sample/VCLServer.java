package Sample;
import java.io.*;
import java.net.*;

/**
 * Created by NatchaS on 5/10/15.
 */
public class VCLServer {
    public VCLServer() throws IOException{
        ServerSocket server = new ServerSocket(3000);
        while(true){
            Socket socket = server.accept();

        }
    }
}
