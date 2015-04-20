package Sample;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by NatchaS on 4/19/15.
 */
public class VectorClockOutputStream extends ObjectOutputStream {
    HashMap<String, Integer> clock;
    private boolean writeClockEnable;
    public VectorClockOutputStream(OutputStream stream, HashMap<String, Integer> clock) throws IOException {
        super(stream);
        this.clock = clock;
        this.writeClockEnable = true;
    }

    @Override
    public void writeObjectOverride(Object obj) throws IOException{
        if (writeClockEnable) {
            System.out.println("writing clock");
            super.writeObject(this.clock);
            this.writeClockEnable = false;
        }
        super.writeObject(obj);
    }

    @Override
    public void close() throws IOException{
        super.close();
        this.writeClockEnable = false;
    }

}
