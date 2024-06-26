package Specific_Questions;

public class Subsets {
	
	public static void allSubsets(int[] arr) {
		for(int i = 0; i <= arr.length; i++) {
			boolean[] B = new boolean[arr.length];
			subset(arr, i, 0, 0, B);
		}
	}
	
	public static void subsets(int[] arr, int length) {
		boolean[] B = new boolean[arr.length];
		subset(arr, 3, 0, 0, B);
	}
	
	// prints all the subsets of A of size k
	private static void subset(int[] A, int k, int start, int currLen, boolean[] used) {
		if (currLen == k) {
			for (int i = 0; i < A.length; i++) {
				if (used[i] == true) {
					System.out.print(A[i] + " ");
				}
			}
			System.out.println();
			return;
		}
		if (start == A.length) {
			return;
		}
		// For every index we have two options,
		// 1.. Either we select it, means put true in used[] and make currLen+1
		used[start] = true;
		subset(A, k, start + 1, currLen + 1, used);
		// 2.. OR we dont select it, means put false in used[] and dont increase
		// currLen
		used[start] = false;
		subset(A, k, start + 1, currLen, used);
	}

	public static void main(String[] args) {
		allSubsets(new int[] { 1, 2, 3});
	}
}
