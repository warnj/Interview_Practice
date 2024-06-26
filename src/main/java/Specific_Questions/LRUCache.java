package Specific_Questions;

import java.util.HashMap;
import java.util.Map;

// could this be done with singly linked list? - no, would lose O(1) for removing from end
// may be able to change what order means and use singly linked list:
// 		if tail was most recent and inserts happened at tail and removing happened at head
public class LRUCache {
	int size;
	int capacity;

	// Most recently used at front of linked list, need to implement list to be able to re-wire nodes
	// from middle to front on retrieval
	Node head;
	// need tail/end pointer to delete least recently used once capacity reached
	Node tail;

	Map<Integer, Node> cache = new HashMap<>(); // key to value mappings

	public LRUCache(int capacity) {
		this.capacity = capacity;
	}

	public int get(int key) {
		Node n = cache.get(key);
		if (n == null) return -1;
		moveToFront(n);
		return n.value;
	}

	public void put(int key, int value) {
		Node n = cache.get(key);
		if (n != null) { // update
			n.value = value;
			moveToFront(n);
			// don't update cache since it contains a reference to Node and Node updated already
		} else { // add
			if (size >= capacity) {
				// evict least recently used (end of linked list)
				cache.remove(tail.key); // capacity >= 1, so tail always non-null
				if (tail.prev != null) tail = tail.prev; // size == 1, tail.prev will be null
				tail.next = null;
			} else {
				size++;
			}

			// insert at front of list
//			System.out.println("Before inserting (" + key + "," + value + ") at head: " + this);
			head = new Node(key, value, head);
			if (head.next != null) {
				head.next.prev = head;
			} else {
				tail = head; // size == 1
			}
//			System.out.println("After inserting at head: " + this);

			cache.put(key, head);
		}
	}

	private void moveToFront(Node n) {
		if (n != head) {
//			System.out.println("Before move " + n.value + " to front: " + this);
			n.prev.next = n.next; // n.prev will not be null since n is not the head
			if (n.next != null) n.next.prev = n.prev;
			else tail = tail.prev; // n is end of list
			n.next = head;
			n.prev = null;
			if (n.next != null) n.next.prev = n;
			head = n;
//			System.out.println("After move to front: " + this);
		}
	}

	@Override
	public String toString() {
		if (head == null) return "[]";
		StringBuilder b = new StringBuilder();
		for (Node cur = head; cur != null; cur = cur.next) {
			b.append(" -> ").append(cur.key + "," + cur.value);
		}
//		b.append("  backward: ");
//		for (Node cur = tail; cur != null; cur = cur.prev) {
//			b.append(" -> ").append(cur.value);
//		}
		return b.toString();
	}

	static class Node {
		int value;
		int key; // need key for eviction from tail
		Node next;
		Node prev;

		public Node(int k, int d, Node n) {
			key = k;
			value = d;
			next = n;
		}
	}
}
