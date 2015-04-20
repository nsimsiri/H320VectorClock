package Sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 * Created by NatchaS on 4/19/15.
 */
public class VectorClockInputStream extends ObjectInputStream {
    HashMap<String, Integer> clock;
    private boolean readClockEnable;
    public VectorClockInputStream(InputStream stream, HashMap<String, Integer> clock) throws IOException {
        super(stream);
        this.clock = clock;
        this.readClockEnable = true;
    }

    @Override
    public Object readObjectOverride()throws IOException, ClassNotFoundException{
        if (this.readClockEnable){
            HashMap<String, Integer> recvClock = (HashMap<String, Integer>)super.readObject();
            this.clock.putAll(recvClock);
            System.out.println("Clock Received");
            this.readClockEnable = false;
        }
        return super.readObject();
    }

    @Override public void close() throws IOException{
        super.close();
        this.readClockEnable = true;
    }

    public HashMap<String, Integer> getClock(){
        return this.clock;
    }

}
