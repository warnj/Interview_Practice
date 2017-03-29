package Tests;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import General_Questions.Heap;

public class HeapTests {
	private Heap<Integer> h;
	private static Random r = new Random();

	@Test
	public void test1() {
		Integer[] arr = randomIntArray(0);
		h = new Heap<>(arr);
		assertTrue(h.size() == 0);
		for(int i = 1; i < 20; i++) {
			h.insert(i);
			assertTrue(h.size() == i);
		}
		h = new Heap<>();
		assertTrue(h.size() == 0);
		for(int i = 1; i < 20; i++) {
			h.insert(i);
			assertTrue(h.size() == i);
		}
	}
	
	@Test
	public void testHeapWith5Items() {
        Heap<String> heap = new Heap<>();
        String[] tests = { "a", "b", "c", "d", "e" };
        for (int i = 0; i < 5; i++) {
            String str = tests[i] + "a";
            heap.insert(str);
        }

        boolean passed = true;
        for (int i = 0; i < 5; i++) {
            String str_heap = heap.deleteMin();
            String str = (char) ('a' + i) + "a";
            passed &= str.equals(str_heap);
        }

        assertTrue(passed);
    }
	@Test
    public void testOrderingDoesNotMatter() {
    	Heap<String> ordered = new Heap<>(new String[]{"a", "b", "c", "d", "e"});
    	Heap<String> reversed = new Heap<>(new String[]{"e", "d", "c", "b", "a"});
    	Heap<String> random = new Heap<>(new String[]{"d", "b", "c", "e", "a"});

        if (!isSame("a", ordered.getMin(), reversed.getMin(), random.getMin()) ||
                !isSame("a", ordered.deleteMin(), reversed.deleteMin(), random.deleteMin()) ||
                !isSame("b", ordered.deleteMin(), reversed.deleteMin(), random.deleteMin())) {
        	assertTrue(false);
        }

        addAll(ordered, new String[] {"a", "a", "b", "c", "z"});
        addAll(reversed, new String[] {"z", "c", "b", "a", "a"});
        addAll(random, new String[] {"c", "z", "a", "b", "a"});

        String[] expected = new String[] {"a", "a", "b", "c", "c", "d", "e", "z"};
        for (String e : expected) {
            if (!isSame(e, ordered.getMin(), reversed.getMin(), random.getMin()) ||
                    !isSame(e, ordered.deleteMin(), reversed.deleteMin(), random.deleteMin())) {
            	assertTrue(false);
            }
        }
    }

    private static boolean isSame(String... args) {
        String first = args[0];
        for (String arg : args) {
            if (!first.equals(arg)) {
                return false;
            }
        }
        return true;
    }
    
    private static void addAll(Heap<String> h, String[] arr) {
        for (String arg : arr) {
        	h.insert(arg);
        }
    }
    
    @Test
    public void testHugeHeap() {
    	Heap<String> heap = new Heap<>();
        int n = 10000;

        // Add them
        for (int i = 0; i < n; i++) {
            String str = String.format("%05d", i * 37 % n);
            heap.insert(str);
        }

        // Delete them all
        boolean passed = true;
        for (int i = 0; i < n; i++) {
            String s = heap.deleteMin();
            passed &= i == Integer.parseInt(s);
        }
    }
    @Test
    public void testWithCustomComparable() {
    	Heap<Coordinate> student = new Heap<>();
        Queue<Coordinate> reference = new PriorityQueue<>();
        for (int i = 0; i < 10000; i++) {
            Coordinate coord = new Coordinate(r.nextInt(10000) - 5000, r.nextInt(10000) - 5000);
            student.insert(coord);
            reference.add(coord);
        }
        if (student.size() != reference.size()) {
        	assertTrue(false);
        }
        while (!reference.isEmpty()) {
            if (reference.peek() != student.getMin() || reference.remove() != student.deleteMin()) {
            	assertTrue(false);
            }
        }
        assertTrue(true);
    }

    public static class Coordinate implements Comparable<Coordinate> {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // What exactly this comparable method is doing is somewhat arbitrary.
        public int compareTo(Coordinate other) {
            if (this.x != other.x) {
                return this.x - other.x;
            } else {
                return this.y - other.y;
            }
        }
    }

	private static Integer[] randomIntArray(int size) {
		Integer[] arr = new Integer[size];
		for(int i=0; i < size; i++) {
			arr[i] = r.nextInt();
		}
		return arr;
	}
}
