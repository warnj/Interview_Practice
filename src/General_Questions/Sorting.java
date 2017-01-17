package General_Questions;

public class Sorting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// sort the given array so even are at front and odd are at back
	public static void sortEven(int[] arr) {
		int front = 0;
		int back = arr.length - 1;
		while(back > front) {
			if(arr[front] % 2 == 1) { // move odd to back
				while(arr[back] % 2 == 1) { // find even at the end
					back--;
				}
				if(back > front) {
					int temp = arr[front];
					arr[front] = arr[back];
					arr[back] = temp;
				}
			}
			front++;
		}
	}
	
	// sorts the given int[] b by rearranging the negative elements to the front of the array, the elements
	// that equal zero to the middle of the array, and the elements that are positive to the end of the array
	// Dutch National Flag Algo
	public static void sortArray(int[] b) {
		int frontIndex = 0; // index to hold next negative value found
		int middleIndex = 0; // index of array to check
		int endIndex = b.length - 1; // index to hold next positive value found
		
		while(middleIndex <= endIndex) {
			if (b[middleIndex] < 0) { // move to the front
				swap(frontIndex, middleIndex, b);
				frontIndex++;
				middleIndex++;
			} else if (b[middleIndex] == 0) {
				middleIndex++;
			} else { // middleIndex > 0, move to the end
				swap(middleIndex, endIndex, b);
				endIndex--;
				// don't increment middleIndex since we need to check
				// the value just swapped to middleIndex from the end of the array
			}
		}
	}
	
	// selection sort (n^2 bad, but simple algo) for 331 hw2 question 4
	// find the min, put in front, find min in remaining array, repeat
	public static void selectionSort(int[] a){
		int sortedEndIndex = 0;
		while(sortedEndIndex != a.length - 1) {
			int minIndex = sortedEndIndex;
			int searchIndex = sortedEndIndex + 1;
			while(searchIndex != a.length) {
				if(a[searchIndex] < a[minIndex]) {
					minIndex = searchIndex;
				}
				searchIndex = searchIndex + 1;
			}
			swap(sortedEndIndex, minIndex, a);
			sortedEndIndex = sortedEndIndex + 1;
		}
	}
	
	// swap the given a and b indexes of the given array
	private static void swap(int a, int b, int[] arr) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
}
