package Tests;

import static org.junit.Assert.*;
import General_Questions.*;
import org.junit.Test;
import java.util.*;

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
	public void testLevelOrder() {
		IntTree tree = new IntTree();
		tree.add(1);
		tree.add(2);
		tree.add(5);
		tree.add(3);
		tree.add(4);
		tree.add(6);
		System.out.println(tree);
		tree.flatten();
//		tree.printInorder();
		System.out.println(tree);
	}

}
