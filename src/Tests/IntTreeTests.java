package Tests;

import General_Questions.IntTree;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

	@Test
	public void testAllPathSums() {
		int[] pre = new int[] {1,2,9,4,3};
		int[] in = new int[] {9,2,4,1,3};
		IntTree tree = new IntTree(pre, in);
		tree.add(8);
		System.out.println(tree);
		List<List<Integer>> lists = tree.pathSum(12);
		System.out.println(lists);
		assertEquals(2, lists.size());
	}

	@Test
	public void testWidthOfBinaryTree() {
		int[] pre = new int[] {1,2,9,4,3};
		int[] in = new int[] {9,2,4,1,3};
		IntTree tree = new IntTree(pre, in);
		assertEquals(2, tree.widthOfBinaryTree());
		tree.add(5);
		assertEquals(4, tree.widthOfBinaryTree());
		tree.add(-5);
		tree.add(11);
		assertEquals(8, tree.widthOfBinaryTree());
		tree.add(-6);
		tree.add(12);
		assertEquals(16, tree.widthOfBinaryTree());
	}

	@Test
	public void testSymmetric() {
		int[] pre = new int[] {3,2,1,2,1};
		int[] in = new int[] {1,2,3,2,1};
		IntTree tree = new IntTree(pre, in);
		System.out.println(tree);
		assertTrue(tree.isSymmetric());
		tree.add(5);
		assertFalse(tree.isSymmetric());
	}

	@Test
	public void testSerializeDeserialize() {
		int[] pre = new int[] {3,2,1,2,1};
		int[] in = new int[] {1,2,3,2,1};
		IntTree tree = new IntTree(pre, in);
		System.out.println(tree);

		String s = tree.serialize();
		System.out.println(s);

		IntTree t = new IntTree(s);
		System.out.println(t);

		IntTree t2 = new IntTree("null, 3, 2, 2, 1, null, null, 1");
		System.out.println(t2);
	}

	@Test
	public void testTraversals() {
		IntTree t2 = new IntTree("null, 1, 2, 3, 4, 5, 6, 7");
		System.out.println(t2);
		t2.printPreorder();
		t2.printInorder();
		t2.printPostorder();
	}

	@Test
	public void testRightSideView() {
		IntTree t2 = new IntTree("null, 1, 2, 3, 4, 5, 6, 7");
		assertEquals(Arrays.asList(1,3,7), t2.rightSideView());
		t2 = new IntTree("null, 1, 2, 3, 4, 5, 6, null");
		assertEquals(Arrays.asList(1,3,6), t2.rightSideView());
		t2 = new IntTree("null, 1, 2, null, 4, 5, null, null");
		assertEquals(Arrays.asList(1,2,5), t2.rightSideView());
	}

	@Test
	public void testCountNodes() {
		IntTree t2 = new IntTree("null, 1, 2, 3, 4, 5, 6, 7");
		assertEquals(7, t2.countNodes());
		t2 = new IntTree("null, 1, 2, 3, 4, 5, 6, null");
		assertEquals(6, t2.countNodes());
	}
}
