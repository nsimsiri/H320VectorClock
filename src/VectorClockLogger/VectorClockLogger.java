package VectorClockLogger;

import java.util.*;
import java.io.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Created by NatchaS on 5/10/15.
 */
public class VectorClockLogger {
    private VectorClock vclock;
    private Log log;
    public VectorClockLogger(String hostname){
        this.log = LogFactory.getLog(VectorClockLogger.class);

        this.vclock = new VectorClock(hostname);
    }

    public Serializable serialize(){
        return this.serialize("");
    }
    public Serializable serialize(String msg){
        this.vclock.increment();
        log(msg);
        return this.vclock.serialize();
    }

    public void deserialize(ObjectInputStream in, String msg) throws IOException, ClassNotFoundException{
        this.deserialize((VectorClock) in.readObject(), msg);

    }
    public void deserialize(Serializable vc, String msg) throws IOException{
        if (!(vc instanceof VectorClock)) throw new IOException("Vector Clock Not Serializable");
        VectorClock otherClock = (VectorClock) vc;
        this.vclock.merge(otherClock);
        log(msg);
    }

    public void log(String msg){
        if (!msg.isEmpty()) log.info(String.format("%s: %s\n", this.vclock.toString(), msg));
    }

    public String hostname(){
        return vclock.hostname();
    }

    @Override
    public String toString(){
        return this.vclock.toString();
    }

}
