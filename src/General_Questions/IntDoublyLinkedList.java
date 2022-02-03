// LinkedIntList provides an implementation of the IntList interface backed by
// a doubly-linked list with dummy header nodes at the front and back.  This
// will provide O(1) performance for adding at the front or back of the list
// and removing values while iterating over the list.  All of the iterator's
// methods are O(1).  Methods like get and set will be O(n) for values in the
// middle of the list.

package General_Questions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntDoublyLinkedList implements Iterable<Integer> {
	private ListNode front;  // first value in the list
	private ListNode back;   // last value in the list
	private int size;        // current number of elements
	
	public IntDoublyLinkedList() {
		front = new ListNode(0);
		back = new ListNode(0);
		clear();
	}
	
	/******** My Additional Methods ***********************/
	
//	public void reverse() {
//		ListNode prev = null;
//		ListNode next = null;
//		ListNode cur = front;
//		
//		while(cur != null) {
//			next = cur.next;
//			
//			cur.next = prev;
//			cur.prev = next;
//			
//			prev = cur;
//			cur = next;
//		}
//		front = prev;
//	}
	
	/******** Practice-It Methods ***********************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	/******** Default Methods ***********************/
	
	public int size() {return size;}
	
	// pre : 0 <= index < size()
	// post: returns the integer at the given index in the list
	public int get(int index) {
		checkIndex(index);
		ListNode current = nodeAt(index);
		return current.data;
	}
	
	// post: appends the given value to the end of the list
	public void add(int value) {
		add(size, value);
	}
	
	// pre: 0 <= index <= size()
	// post: inserts the given value at the given index, shifting subsequent
	//       values right
	public void add(int index, int value) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("index: " + index);
		ListNode current = nodeAt(index - 1);
		ListNode newNode = new ListNode(value, current.next, current);
		current.next = newNode;
		newNode.next.prev = newNode;
		size++;
	}
	
	// pre : 0 <= index < size()
	// post: replaces the integer at the given index with the given value
	public void set(int index, int value) {
		checkIndex(index);
		ListNode current = nodeAt(index);
		current.data = value;
	}
	
	// pre : 0 <= index < size()
	// post: removes value at the given index, shifting subsequent values left
	public void remove(int index) {
		checkIndex(index);
		ListNode current = nodeAt(index - 1);
		current.next = current.next.next;
		current.next.prev = current;
		size--;
	}
	
	// post: list is empty
	public void clear() {
		front.next = back;
		back.prev = front;
		size = 0;
	}
	
	// post: returns an iterator for this list
	public Iterator<Integer> iterator() {
		return new LinkedIterator();
	}
	
	// pre : 0 <= index < size()
	// post: returns the node at a specific index.  Uses the fact that the list
	//       is doubly-linked to start from the front or the back, whichever
	//       is closer.
	private ListNode nodeAt(int index) {
		ListNode current;
		if (index < size / 2) {
			current = front;
			for (int i = 0; i < index + 1; i++)
				current = current.next;
		} else {
			current = back;
			for (int i = size; i >= index + 1; i--)
				current = current.prev;
		}
		return current;
	}
	
	private static class ListNode {
		public int data;       // data stored in this node
		public ListNode next;  // link to next node in the list
		public ListNode prev;  // link to previous node in the list
		
		// post: constructs a node with given data and null links
		public ListNode(int data) {
			this(data, null, null);
		}
		
		// post: constructs a node with given data and given links
		public ListNode(int data, ListNode next, ListNode prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	
	private class LinkedIterator implements Iterator<Integer> {
		private ListNode current;  // location of next value to return
		private boolean removeOK;  // whether it's okay to remove now
		
		// post: constructs an iterator for the given list
		public LinkedIterator() {
			current = front.next;
			removeOK = false;
		}
		
		// post: returns true if there are more elements left, false otherwise
		public boolean hasNext() {
			return current != back;
		}
		
		// pre : hasNext() (throws NoSuchElementException if not)
		// post: returns the next element in the iteration
		public Integer next() {
			if (!hasNext())
				throw new NoSuchElementException();
			int result = current.data;
			current = current.next;
			removeOK = true;
			return result;
		}
		
		// pre : next() has been called without a call on remove (i.e., at most
		//       one call per call on next; throws IllegalStateException if
		//       not)
		// post: removes the last element returned by the iterator
		public void remove() {
			if (!removeOK)
				throw new IllegalStateException();
			
			ListNode prev2 = current.prev.prev;
			prev2.next = current;
			current.prev = prev2;
			size--;
			removeOK = false;
		}
	}
	
	// post: returns a comma-separated, bracketed version of the list
	public String toString() {
		Iterator<Integer> i = iterator();
		String result = "[";
		if (i.hasNext()) {
			result += i.next();
			while (i.hasNext())
				result += ", " + i.next();
		}
		result += "]";
		return result;
	}
	
	// post : returns the position of the first occurence of the given
	//        value (-1 if not found)
	public int indexOf(int value) {
		int index = 0;
		for (int i: this) {
			if (i == value)
				return index;
			index++;
		}
		return -1;
	}
	
	// post: returns true if list is empty, false otherwise
	public boolean isEmpty() {
		return !iterator().hasNext();
	}
	
	// post: returns true if the given value is contained in the list,
	//       false otherwise
	public boolean contains(int value) {
		return indexOf(value) != -1;
	}
	
	// post: appends all values in the given list to the end of this list
	public void addAll(IntDoublyLinkedList other) {
		for (int i: other)
			add(i);
	}
	
	// post: removes all occurrences of the values in the given list from
	//       this list
	public void removeAll(IntDoublyLinkedList other) {
		Iterator<Integer> i = iterator();
		while (i.hasNext()) {
			if (other.contains(i.next()))
				i.remove();
		}
	}
		
	// post: throws an exception if the given index is out of range
	protected void checkIndex(int index) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException("index: " + index);
	}
}