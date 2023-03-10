package Tests;

import General_Questions.StacksAndQueues.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class StacksAndQueuesTest {

    @Test
    public void testStackWithQueuePop() {
        StackWithQueuePop s = new StackWithQueuePop();
        for (int i = -5; i < 5; i++) s.push(i);
        assertFalse(s.isEmpty());
        for (int i = 4; i >= -5; i--) assertEquals(i, s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    public void testStackWithQueuePush() {
        StackWithQueuePush s = new StackWithQueuePush();
        for (int i = -5; i < 5; i++) s.push(i);
        assertFalse(s.isEmpty());
        for (int i = 4; i >= -5; i--) assertEquals(i, s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    public void testStackWithQueuesPop() {
        StackWithQueuesPop s = new StackWithQueuesPop();
        for (int i = -5; i < 5; i++) s.push(i);
        assertFalse(s.isEmpty());
        for (int i = 4; i >= -5; i--) assertEquals(i, s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    public void testStackWithQueuesPush() {
        StackWithQueuesPush s = new StackWithQueuesPush();
        for (int i = -5; i < 5; i++) s.push(i);
        assertFalse(s.isEmpty());
        for (int i = 4; i >= -5; i--) assertEquals(i, s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    public void testMaxStack1() {
        MaxStack m = new MaxStack();
        m.push(1);
        m.push(2);
        m.push(3);
        m.push(2);
        m.push(3);
        m.pop();
        assertEquals(3, m.getMax());
        m.pop();
        assertEquals(3, m.getMax());
        m.pop();
        assertEquals(2, m.getMax());
        m.pop();
        assertEquals(1, m.getMax());
        m.pop();
        assertTrue(m.isEmpty());
    }
    @Test
    public void testMaxStack2() {
        MaxStack m = new MaxStack();
        for (int i = 10; i >= 0; i--) {
            m.push(9);
        }
        System.out.println(m);
        for (int i = 10; i >= 1; i--) {
            m.pop();
        }
        assertFalse(m.isEmpty());
        assertEquals(9, m.getMax());
        assertEquals(9, m.pop());
        assertTrue(m.isEmpty());
    }

    @Test
    public void testMaxStack3() {
        MaxStack m = new MaxStack();
        for (int i = 5; i >= 0; i--) {
            m.push(i);
            m.push(i);
        }
        assertEquals(0, m.pop());
        assertEquals(5, m.getMax());
    }
}
