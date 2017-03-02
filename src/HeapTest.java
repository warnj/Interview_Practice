import static org.junit.Assert.*;

import org.junit.Test;

public class HeapTest {

	@Test
	public void test() {
		Integer[] a = {8,9,7,2,11,1,3,6,10};
		Heap h = new Heap(a);
		for(int i = -1; i < 13; i++) {
			System.out.println("Smaller than " + i + " :");
			h.printSmaller(i);
		}
	}

}
