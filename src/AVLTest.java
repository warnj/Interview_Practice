import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

public class AVLTest {

	@Test
	public void test() {
		AvlTree t = new AvlTree();
		Random r = new Random();
		for(int i = 0; i < 20000; i++) {
			t.insert(r.nextInt(i+500));
			assertTrue(Ex09_332.verifyAVL(t.root));
		}
	}
	
//	@Test
//	public void test2() {
//		AVLNode a = new AVLNode(5);
//		a.left = new AVLNode(4);
//		a.right = new AVLNode(3);
//		assertFalse(Ex09_332.verifyAVL(a));
//	}
	
//	@Test
//	public void test3() {
//		AVLNode a = new AVLNode(5);
//		a.left = new AVLNode(4);
//		a.right = new AVLNode(6);
//		assertTrue(Ex09_332.verifyAVL(a));
//	}
	
//	@Test
//	public void test4() {
//		AVLNode a = new AVLNode(5);
//		a.left = new AVLNode(4);
//		a.left.left = new AVLNode(3);
//		a.right = new AVLNode(6);
//		a.right.right = new AVLNode(7);
//		assertTrue(Ex09_332.verifyAVL(a));
//	}

}
