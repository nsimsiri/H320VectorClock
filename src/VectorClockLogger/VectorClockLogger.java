package VectorClockLogger;

import java.util.*;
import java.io.*;

/**
 * Created by NatchaS on 5/10/15.
 */
public class VectorClockLogger {
    VectorClock vclock;
    public VectorClockLogger(String hostname){
        this.vclock = new VectorClock(hostname);
    }

    public Serializable serialize(){
        return this.serialize("");
    }
    public Serializable serialize(String msg){
        this.vclock.increment();
        System.out.format("%s: %s\n", this.vclock.toString(), msg);
        return this.vclock.serialize();
    }

    public void deserialize(ObjectInputStream in, String msg) throws IOException, ClassNotFoundException{
        this.deserialize((VectorClock) in.readObject(), msg);

    }
    public void deserialize(Serializable vc, String msg) throws IOException{
        if (!(vc instanceof VectorClock)) throw new IOException("VectoClock Not Serializable");
        VectorClock otherClock = (VectorClock) vc;
        this.vclock.merge(otherClock);
        System.out.format("%s: %s\n", this.vclock.toString(), msg);
    }

    public void log(String s){
        System.out.format("[%s]: %s\n", this.vclock.hostname(), s);
    }

    @Override
    public String toString(){
        return this.vclock.toString();
    }

}
