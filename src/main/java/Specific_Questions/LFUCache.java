package Specific_Questions;

import java.util.*;
import static org.junit.Assert.assertEquals;

// contents need to stay sorted by their freq and freq update should happen quickly
// consider binary search tree with freq used as the "sorting" factor
// map key -> node allows quick find within the tree and subsequent update
// map freq -> List of nodes with the freq allows ties in freq to be broken with LRU

/* Requirements:
	fast access to least freq accessed & can break ties with LRU
	fast update to freq
	fast get given key

	Map1: HashMap<key -> Node(val, freq)>
	Map2: SortedMap<freq -> OrderedMap<key, Node>>
 */
public class LFUCache {

	public static void main(String[] args) {
		LFUCache lfu = new LFUCache(2);
		lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
		lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
		assertEquals(1, lfu.get(1));
		// cache=[1,2], cnt(2)=1, cnt(1)=2
		lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
		// cache=[3,1], cnt(3)=1, cnt(1)=2
		assertEquals(-1, lfu.get(2));
		assertEquals(3, lfu.get(3));
		// cache=[3,1], cnt(3)=2, cnt(1)=2
		lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
		// cache=[4,3], cnt(4)=1, cnt(3)=2
		assertEquals(-1, lfu.get(1));
		assertEquals(3, lfu.get(3));
		// cache=[3,4], cnt(4)=1, cnt(3)=3
		assertEquals(4, lfu.get(4));
		// cache=[4,3], cnt(4)=2, cnt(3)=3
	}

	int size;
	int capacity;
	Map<Integer, Node> contents; // key -> Node(val, freq)
	SortedMap<Integer, LinkedHashMap<Integer,Node>> freqs; // freq -> OrderedMap<key,Node>

	public LFUCache(int c) {
		capacity = c;
		size = 0;
		contents = new HashMap<>();
		freqs = new TreeMap<>();
	}

	public int get(int key) {
		Node n = contents.get(key);
		if (n == null) return -1;
		incrementFreq(n);
		return n.value;
	}

	public void put(int key, int value) {
		if (capacity == 0) return;
		Node n = contents.get(key);
		if (n == null) {
			if (size == capacity) {
				// remove LFU
				int lowestFreq = freqs.firstKey();
				LinkedHashMap<Integer,Node> leastFreqNodes = freqs.get(lowestFreq);
//				System.out.println(freqs);
				// get the first element of the ordered map (this is the LRU if there are multiple)
				int keyToRemove = leastFreqNodes.keySet().iterator().next();
//				System.out.println("removing " + keyToRemove);
				leastFreqNodes.remove(keyToRemove);
				if (leastFreqNodes.isEmpty()) freqs.remove(lowestFreq);
				contents.remove(keyToRemove);
				size--;
			}
			// add
			n = new Node(key, value, 0);
			contents.put(key, n);
			size++;
		} else {
			// update
			n.value = value;
		}
		incrementFreq(n);
	}

	// increment the frequency count for the given node
	private void incrementFreq(Node n) {
		// remove from old freq
		Map<Integer,Node> linkedMap = freqs.get(n.freq);
		if (linkedMap != null) {
			linkedMap.remove(n.key); // if adding new key, there won't be an old
			if (linkedMap.isEmpty()) freqs.remove(n.freq); // remove empty values so firstKey always returns LFU
		}
		n.freq++;
		// add to new freq
		linkedMap = freqs.get(n.freq);
		if (linkedMap == null) {
			LinkedHashMap<Integer,Node> newMap = new LinkedHashMap<>();
			newMap.put(n.key, n);
			freqs.put(n.freq, newMap);
		} else {
			linkedMap.put(n.key, n); // most recently used will be at end of the linkedmap
		}
	}

	@Override
	public String toString() {
		return contents.values().toString();
	}

	static class Node {
		int value;
		int key;
		int freq;

		public Node(int k, int v, int f) {
			key = k;
			value = v;
			freq = f;
		}

		public String toString() {
			return String.format("%d->%d(freq=%d)", key, value, freq);
		}
	}
}
