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
    public VectorClockInputStream(InputStream stream, HashMap<String, Integer> clock) throws IOException {
        super(stream);
        this.clock = clock;
    }

    @Override
    public int read(byte[] bytes)throws IOException{
        try{
            HashMap<String, Integer> rcvMap = (HashMap<String, Integer>)super.readObject();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } finally{
            super.read(bytes);
        }
        return 1;
    }


}
