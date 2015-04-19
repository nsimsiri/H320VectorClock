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
    public VectorClockOutputStream(OutputStream stream, HashMap<String, Integer> clock) throws IOException {
        super(stream);
        this.clock = clock;
    }

    @Override
    public void write(byte[] buf) throws IOException{
        super.writeObject(this.clock);
        super.write(buf);
    }
}
