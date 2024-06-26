package Specific_Questions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Cse143_Merge_Sort {

	// post: list is in sorted (nondecreasing) order
    public static void sort(Queue<String> list) {
        if (list.size() > 1) {
            Queue<String> half1 = new LinkedList<String>();
            Queue<String> half2 = new LinkedList<String>();
            int size1 = list.size() / 2;
            int size2 = list.size() - size1;
            for (int i = 0; i < size1; i++)
                half1.add(list.remove());
            for (int i = 0; i < size2; i++)
                half2.add(list.remove());
            sort(half1);
            sort(half2);
            mergeInto(list, half1, half2);
        }
    }

    // pre : result is empty; list1 is sorted; list2 is sorted
    // post: result contains the result of merging the sorted lists;
    //       list1 is empty; list2 is empty
    public static void mergeInto(Queue<String> result,
                                 Queue<String> list1,
                                 Queue<String> list2) {
        while (!list1.isEmpty() && !list2.isEmpty()) {
            if (list1.peek().compareTo(list2.peek()) <= 0)
                result.add(list1.remove());
            else
                result.add(list2.remove());
        }
        while (!list1.isEmpty())
            result.add(list1.remove());
        while (!list2.isEmpty())
            result.add(list2.remove());
    }	
}



public class Mergesort {
	public static void main(String[] args)	{
		Integer[] a = {2, 6, 3, 5, 1};
		mergeSort(a);
		System.out.println(Arrays.toString(a));
	}

	public static void mergeSort(Comparable [ ] a) {
		Comparable[] tmp = new Comparable[a.length];
		mergeSort(a, tmp,  0,  a.length - 1);
	}


	private static void mergeSort(Comparable [ ] a, Comparable [ ] tmp, int left, int right){
		if( left < right )	{
			int center = (left + right) / 2;
			mergeSort(a, tmp, left, center);
			mergeSort(a, tmp, center + 1, right);
			merge(a, tmp, left, center + 1, right);
		}
	}

	private static void merge(Comparable[ ] a, Comparable[ ] tmp, int left, int right, int rightEnd )  {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
            if(a[left].compareTo(a[right]) <= 0)
                tmp[k++] = a[left++];
            else
                tmp[k++] = a[right++];

        while(left <= leftEnd)    // Copy rest of first half
            tmp[k++] = a[left++];

        while(right <= rightEnd)  // Copy rest of right half
            tmp[k++] = a[right++];

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--)
            a[rightEnd] = tmp[rightEnd];
    }
 }