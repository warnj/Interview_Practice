package Tests;

import General_Questions.IntTree;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class IntTreeTests {
	Random r = new Random(); 
	
	@Test (expected = IllegalArgumentException.class)
	public void testSecSmallest1() {
		IntTree tree = new IntTree();
		int n = tree.secondSmallest();
	}
	
	@Test
	public void testSecSmallest2() {
		IntTree tree = new IntTree();
		tree.add(1);
		tree.add(2);
		int n = tree.secondSmallest();
		assertEquals(n, 2);
	}
	
	@Test
	public void testSecSmallest3() {
		IntTree tree = new IntTree();
		for(int i = 0; i < 100; i++) {
			tree.add(i);
		}
		int n = tree.secondSmallest();
		assertEquals(n, 1);
	}
	
	@Test
	public void testSecSmallest4() {
		IntTree tree = new IntTree();
		for(int i = 0; i < 100; i++) {
			tree.add(r.nextInt(3000));
		}
		tree.add(-2);
		tree.add(-5);
		int n = tree.secondSmallest();
		assertEquals(n, -2);
	}
	
	@Test
	public void testBuild() {
		int[] pre = new int[] {1,2,9,4,3};
		int[] in = new int[] {9,2,4,1,3};
		IntTree tree = new IntTree(pre, in);
	}

}
