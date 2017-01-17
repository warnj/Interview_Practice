package Specific_Questions;
import java.util.*;

public class Quicksort {
	
	public static void main(String[] args) {
		int[] list = {4,6,7,2,3,4,5,2,1,1,1,3,4,5,6,7,8,9,99,9,999,333,222,555,666};
		quickSort2(list, 0, list.length - 1);
		System.out.println(Arrays.toString(list));
	}
	
	public static void quickSort2(int[] arr, int low, int high) {
		if(low >= high) return;

		int pivot = arr[(low + high ) / 2];

		int i = low;
		int j = high;

		while(i <= j) {
			while(arr[i] < pivot) i++;
			while(arr[j] > pivot) j--;

			if(i < j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j++;
			}
		}

		if(i < high) quickSort(arr, i, high);
		if(j > low) quickSort(arr, low, j);

	}
	
	// avg O(n log(n)) time - each call is O(n) and each call divides the remaining work in half, constant space
	public static void quickSort(int[] arr, int low, int high) {
		if (low >= high || arr == null || arr.length == 0) return;
		// pick the pivot as the middle
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}
			while (arr[j] > pivot) {
				j--;
			}
			if (i <= j) {
				// swap higher and lower from different sides of the pivot
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
		// recursively sort two sub parts, j > i at this point
		if (low < j)
			quickSort(arr, low, j);
		if (high > i)
			quickSort(arr, i, high);
	}

//    // post: picks a random spot of the list as a "pivot" to partition the list
//    //       into two sublists: all those values less than or equal to the
//    //       pivot appearing first followed by the pivot followed by all those
//    //       values greater than the pivot; places the pivot value between the
//    //       two sublists and returns the index of the pivot value
//    private static int partition(int[] list, int low, int high) {
//      // pick a random spot for the pivot
//      int spot = low + (int) ((high - low + 1) * Math.random());
//
//      // put the pivot at the front
//      swap(list, low, spot);
//
//      // remember pivot
//      int pivot = list[low];
//
//      // loop invariant: list divided into 3 parts: values <= pivot followed
//      //                 by unknown values followed by values > pivot
//      int index1 = low + 1;  // index of first unknown value
//      int index2 = high;  // index of last unknown value
//
//      while(index1 <= index2) {  // while some values still unknown
//         if(list[index1] <= pivot) {
//            index1++;
//         } else if(list[index2] >= pivot) {
//            index2--;
//         } else {
//            swap(list, index1, index2);
//            index1++;
//            index2--;
//         }
//      }
//
//      // put the pivot value between the two sublists and return its index
//      swap(list, low, index2);
//      return index2;
//    }
//
//   // post: elements low through high of given list are in sorted
//   //       (nondecreasing) order
//   private static void sort(int[] list, int low, int high) {
//      // uses recursive quicksort to sort elements low through high; base
//      // case is a list of 0 or 1 elements, which requires no sorting; in
//      // recursive case, the list is partitioned using a pivot value and the
//      // two resulting lists are recursively sorted
//      if(low < high) {
//         int pivotIndex = partition(list, low, high);
//         sort(list, low, pivotIndex - 1);
//         sort(list, pivotIndex + 1, high);
//      }
//   }
//
// // pre : 0 <= index1, index2 < list.length
//    // post: Values of list[x] and list[y] are exchanged.
//    private static void swap(int[] list, int index1, int index2) {
//        int temp = list[index1];
//        list[index1] = list[index2];
//        list[index2] = temp;
//    }
}