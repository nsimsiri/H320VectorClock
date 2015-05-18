package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import VectorClockLogger.VectorClock;

import java.util.HashMap;

/**
 * Created by NatchaS on 5/18/15.
 */

public class VectorClockTest {
    @Test
    public void vectorClockContainsHostname(){
        VectorClock vclock = new VectorClock("Test");
        assertEquals(vclock.hostname(), "Test");
    }

    @Test
    public void vectorClockTestIncrement(){
        VectorClock vc = new VectorClock("Test");
        HashMap<String, Integer> clock = vc.getClock();
        assertEquals(clock.containsKey("Test"), true);
        assertEquals(clock.get("Test"), new Integer(0));
        vc.increment();
        clock = vc.getClock();
        assertEquals(clock.get("Test"), new Integer(1));
    }

    @Test
    public void vectorCLockTestMerge(){
        VectorClock vc1 = new VectorClock("Test1");
        VectorClock vc2 = new VectorClock("Test2");
        vc1.increment();
        vc2.increment();
        vc2.increment();
        HashMap<String, Integer> c1 = vc1.getClock();
        HashMap<String, Integer> c2 = vc2.getClock();
        assertEquals(c1.get(vc1.hostname()), new Integer(1));
        assertEquals(c2.get(vc2.hostname()), new Integer(2));
        vc1.merge(vc2);
        assertEquals(c1.containsKey(vc2.hostname()), true);
        assertEquals(c1.get(vc2.hostname()), c2.get(vc2.hostname()));

    }
}
