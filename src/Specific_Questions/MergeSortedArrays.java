package Specific_Questions;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class MergeSortedArrays {



	// requires all elements of a are non-null, not empty, and sorted in non-decreasing order
	// O(nkLogk) time where k is the # of sorted arrays and n is the average length of the given arrays
	public static int[] merge(int[][] a) {
		int totalSize = 0;
		Queue<HeapNode> firstElements = new PriorityQueue<HeapNode>();
		for(int i = 0; i < a.length; i++) {
			firstElements.add(new HeapNode(a[i][0], i, 0));
			totalSize += a[i].length;
		}
		int[] result = new int[totalSize];
		
		for(int i = 0; i < totalSize; i++) {
			HeapNode lowest = firstElements.remove();
			result[i] = lowest.data;
			
			if (lowest.listIndex < a[lowest.listNo].length - 1) {
				int newIndex = lowest.listIndex + 1;
				firstElements.add(new HeapNode(a[lowest.listNo][newIndex], lowest.listNo, newIndex));
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int[][] A = new int[5][];
		A[0] = new int[] { 1, 5, 8, 9 };
		A[1] = new int[] { 2, 3, 7, 10 };
		A[2] = new int[] { 4, 6, 11, 15 };
		A[3] = new int[] { 9, 14, 16, 19 };
		A[4] = new int[] { 2, 4, 6, 9 };
		int[] op = MergeSortedArrays.merge(A);
		System.out.println(Arrays.toString(op));
	}
	
	// Every Node will store the data and the list number and the index from which it belongs
	private static class HeapNode implements Comparable<HeapNode> {
		public int data;
		public int listNo;
		public int listIndex;

		public HeapNode(int data, int listNo, int listIndex) {
			this.data = data;
			this.listNo = listNo;
			this.listIndex = listIndex;
		}
		
		public int compareTo(HeapNode other) {
			return Integer.compare(this.data, other.data);
		}
		
		public String toString() {
			return Integer.toString(data);
		}
	}
}