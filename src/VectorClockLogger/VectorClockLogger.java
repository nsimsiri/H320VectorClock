package VectorClockLogger;

import java.util.*;
import java.io.*;

/**
 * Created by NatchaS on 5/10/15.
 */
public class VectorClockLogger {
    private VectorClock vclock;
    public VectorClockLogger(String hostname){
        this.vclock = new VectorClock(hostname);
    }

    public Serializable serialize(){
        return this.serialize("");
    }
    public Serializable serialize(String msg){
        this.vclock.increment();
        if (!msg.isEmpty()) System.out.format("%s: %s\n", this.vclock.toString(), msg);
        return this.vclock.serialize();
    }

    public void deserialize(ObjectInputStream in, String msg) throws IOException, ClassNotFoundException{
        this.deserialize((VectorClock) in.readObject(), msg);

    }
    public void deserialize(Serializable vc, String msg) throws IOException{
        if (!(vc instanceof VectorClock)) throw new IOException("Vector Clock Not Serializable");
        VectorClock otherClock = (VectorClock) vc;
        this.vclock.merge(otherClock);
        if (!msg.isEmpty())System.out.format("%s: %s\n", this.vclock.toString(), msg);
    }

    public void log(String s){
        System.out.format("[%s]: %s\n", this.vclock.hostname(), s);
    }

    public String hostname(){
        return vclock.hostname();
    }

    @Override
    public String toString(){
        return this.vclock.toString();
    }

}
