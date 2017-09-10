package Tests;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.Test;
import General_Questions.*;

public class IntLinkedListTests {

	@Test
	public void testSumBackwardsNumbers() {
		IntLinkedList a = new IntLinkedList();
		a.add(7);
		a.add(1);
		a.add(6);
		IntLinkedList b = new IntLinkedList();
		b.add(5);
		b.add(9);
		b.add(2);
		IntLinkedList c = IntLinkedList.sumBackwardsNumbers(a, b);
		assertEquals(c.toString(), "[2, 1, 9]");
		
		a = new IntLinkedList();
		a.add(9);
		a.add(8);
		b = new IntLinkedList();
		b.add(1);
		c = IntLinkedList.sumBackwardsNumbers(a, b);
		assertEquals(c.toString(), "[0, 9]");
	}
	
	@Test
	public void testMergeSortedLists() {
		IntLinkedList a = new IntLinkedList();
		a.add(1); a.add(6);	a.add(6);
		IntLinkedList b = new IntLinkedList();
		b.add(3); b.add(8);	b.add(9);
		IntLinkedList c = IntLinkedList.mergeSortedLists(a,b);
		assertTrue(isSorted(c));
		assertTrue(c.size() == 6);
		
		a = new IntLinkedList();
		a.add(1); a.add(6);	a.add(6); a.add(7);	a.add(88);
		b = new IntLinkedList();
		b.add(3); b.add(8);	b.add(9);
		c = new IntLinkedList();
		c.add(-6); c.add(8); c.add(75);
		List<IntLinkedList> d = new ArrayList<>();
		d.add(a); d.add(b); d.add(c);
		IntLinkedList s = IntLinkedList.mergeSortedLists(d);
		assertTrue(isSorted(s));
		assertTrue(s.size() == 11);
		
		a = new IntLinkedList();
		a.add(1); a.add(6);	a.add(6); a.add(7);	a.add(88);
		b = new IntLinkedList();
		b.add(3); b.add(8);	b.add(9);
		c = new IntLinkedList();
		c.add(-6); c.add(8); c.add(75);
		d = new ArrayList<>();
		d.add(a); d.add(b); d.add(c);
		s = IntLinkedList.mergeSortedListsRecursive(d);
		assertTrue(isSorted(s));
		assertTrue(s.size() == 11);
	}
	private boolean isSorted(IntLinkedList l) {
		for (int i = 0; i < l.size() - 1; i++) {
	        if (l.get(i) > l.get(i+1)) {
	            return false;
	        }
	    }
	    return true;
	}

}
