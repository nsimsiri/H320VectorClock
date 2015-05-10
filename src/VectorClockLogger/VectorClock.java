package VectorClockLogger;
import java.io.*;
import java.util.*;
/**
 * Created by NatchaS on 4/22/15.
 */
public class VectorClock implements Serializable, Comparable<VectorClock>{
    private HashMap<String, Integer> clock;
    private String hostname;
    public VectorClock(String hostname){
        this.hostname = hostname;
        this.clock = new HashMap<String, Integer>();
        this.clock.put(hostname, 0);
    }

    public void increment(){
        this.clock.put(this.hostname, this.clock.get(this.hostname)+1);
    }

    public void merge(VectorClock that){
        for (Map.Entry<String, Integer> e : that.getClockMap().entrySet()){
            if (this.clock.containsKey(e.getKey())){
                this.clock.put(e.getKey(), e.getValue());
            } else {
                this.clock.put(e.getKey(), Math.max(e.getValue(), this.clock.get(e.getKey())));
            }
        }
    }

    public HashMap<String, Integer> getClockMap(){
        return this.clock;
    }

    public void writeObject(ObjectOutputStream out) throws IOException{
        out.writeObject(this);
    }

    public void readObject(ObjectInputStream in){
//        Vector
    }

    public static VectorClock deserialize(Serializable vectorClock){
        if (vectorClock instanceof VectorClock){
            return (VectorClock) vectorClock;
        }
        return null;
    }

    public Serializable serialize(){
        return (Serializable)this;
    }


    public int compareTo(VectorClock clock){
        return 0;
    }
    
    public static void main(String[] args){
    	System.out.println("Hello World");
    }


}
