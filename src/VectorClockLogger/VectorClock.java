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

    public VectorClock(String hostname, HashMap<String, Integer> clock){
        this.clock = clock;
        this.hostname = hostname;
    }

    public void increment(){
        this.clock.put(this.hostname, this.clock.get(this.hostname)+1);
    }

    public void merge(VectorClock that){
        for (Map.Entry<String, Integer> e : that.getClock().entrySet()){
            if (!this.clock.containsKey(e.getKey())){
                this.clock.put(e.getKey(), e.getValue());
            } else {
                this.clock.put(e.getKey(), Math.max(e.getValue(), this.clock.get(e.getKey())));
            }
        }
    }

    public void writeObject(ObjectOutputStream out) throws IOException{
        out.writeObject(this);
    }

//    public void readObject(ObjectInputStream in) throws IOException{
//    }

    public static VectorClock deserialize(Serializable vectorClock){
        if (vectorClock instanceof VectorClock){
            return (VectorClock) vectorClock;
        }
        return null;
    }

    public Serializable serialize(){
        return (Serializable)this;
    }

/**
 * Vector Timestamp comparison: http://www.cs.rutgers.edu/~pxk/417/notes/clocks/
 * */
    public int compareTo(VectorClock other){
        boolean thisBigger = false;
        boolean otherBigger = false;
        ArrayList<String> commonKeys = new ArrayList<String>();
        HashMap<String, Integer> otherClock = other.getClock();
        for(HashMap.Entry<String, Integer> e1 : this.clock.entrySet()) {
            if (otherClock.containsKey(e1.getKey())) {
                if (e1.getValue() < otherClock.get(e1.getKey())) otherBigger = true;
                else if (e1.getValue() > otherClock.get(e1.getKey())) thisBigger = true;
            } else {
                break;
            }
        }
        if (thisBigger && !otherBigger) return 1;
        else if (!thisBigger && otherBigger) return -1;
        else return 1;

    }

    public HashMap<String, Integer> getClock(){
        return this.clock;
    }

    @Override
    public String toString(){
        return String.format("VectorClock([%s] %s", this.hostname, this.clock);
    }

    public String hostname(){
        return this.hostname;
    }
    public static void main(String[] args){
        VectorClock v1 = new VectorClock("v1");
        v1.increment();
        VectorClock v2 = new VectorClock("v2");
        v2.increment();
        v2.increment();
        System.out.println(v1);
        System.out.println(v2);
        v1.merge(v2);
        System.out.println(v1);
    }


}
