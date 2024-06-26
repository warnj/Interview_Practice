package Tests;

import Specific_Questions.LRUCache;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LRUCacheTest {

	@Test
	public void test1() {
        LRUCache c = new LRUCache(2);
        c.put(1, 1);
        c.put(2, 2);
        c.put(3, 3);
        assertEquals(3, c.get(3));
        assertEquals(2, c.get(2));
        assertEquals(-1, c.get(1)); // test eviction
	}

    @Test
    public void test2() {
        LRUCache c = new LRUCache(2);
        c.put(1, 1);
        c.put(2, 2);
        assertEquals(2, c.get(2));
        assertEquals(1, c.get(1)); // test that 'get' updates least recently used
        c.put(3, 3);
        assertEquals(-1, c.get(2));
        assertEquals(3, c.get(3));
    }

    @Test
    public void test3() {
        LRUCache c = new LRUCache(1); // test capacity = 1
        c.put(1, 1);
        assertEquals(1, c.get(1));
        c.put(2, 2);
        assertEquals(2, c.get(2));
        assertEquals(-1, c.get(1));
        c.put(77, 9);
        assertEquals(9, c.get(77));
    }

    @Test
    public void test4() {
        LRUCache c = new LRUCache(2);
        c.put(1, 2);
        c.put(2, 3);
        assertEquals(3, c.get(2));
        assertEquals(2, c.get(1));
        c.put(1, 5); // test update
        assertEquals(5, c.get(1));
        assertEquals(3, c.get(2));
    }

    @Test
    public void test5() {
        LRUCache c = new LRUCache(2);
        c.put(1, 0);
        c.put(2, 2);
        assertEquals(0, c.get(1));
        c.put(3,3);
        assertEquals(-1, c.get(2));
        c.put(4,4);
        assertEquals(-1, c.get(1));
        assertEquals(3, c.get(3));
        assertEquals(4, c.get(4));
    }
}
