// This demonstrates binary heap operations along with the heapSort.
// Heaps must be complete tree so the runtime is always O(log(N)).

// Binary heap (this) with first element at index 1: 
//		for node at index i: parent = i/2, leftChild = 2i, rightChild = 2i + 1
// k-ary heap: 
//		for node at index i: parent = (k + i � 2) / k, jth child = ki � (k�2) + j
import java.util.*;

public class Heap {
   private static final int CAPACITY = 2;

   private int size;            // Number of elements in heap
   private Integer[] heap;     // The heap array

   public Heap() {
      size = 0;
      heap = new Integer[CAPACITY];
   }

   // Construct the binary heap given an array of items.
   // O(n) where n is array.length this way compared to O(nlogn) by adding individual elements
   public Heap(Integer[] array) {
      size = array.length;
      heap = new Integer[array.length+1];

      System.arraycopy(array, 0, heap, 1, array.length);//we do not use 0 index

      buildHeap();
   }
	// runs at O(size)
   private void buildHeap() {
	  // to efficiently build, only need to percolate the nodes above the base level
      for (int k = size/2; k > 0; k--) {
         percolatingDown(k);
      }
   }
   private void percolatingDown(int hole) {
	   Integer tmp = heap[hole];
      int child;

      for(; 2*hole <= size; hole = child) {
         child = 2*hole;

         // find the smallest child
         if(child != size // there will not be a right child if this is the end of the heap 
        		 && heap[child].compareTo(heap[child + 1]) > 0) 
        	 child++;

         if(tmp.compareTo(heap[child]) > 0)
        	// element to percolate down > its smallest child, so shift the smallest child up (moves the hole down)
        	 heap[hole] = heap[child];
         else
        	 break;
      }
      heap[hole] = tmp; // fill the hole
   }

	// Sorts a given array of items.
   public void heapSort(Integer[] array) {
      size = array.length;
      heap = new Integer[size+1];
      System.arraycopy(array, 0, heap, 1, size);
      buildHeap();

      for (int i = size; i > 0; i--) {
    	  Integer tmp = heap[i]; //move top item to the end of the heap array
         heap[i] = heap[1];
         heap[1] = tmp;
         size--;
         percolatingDown(1);
      }
      for(int k = 0; k < heap.length-1; k++)
         array[k] = heap[heap.length - 1 - k];
   }

	// Deletes the top item
   public Integer deleteMin() throws RuntimeException {
      if (size == 0) throw new RuntimeException();
      Integer min = heap[1];
      heap[1] = heap[size--];
      percolatingDown(1);
      return min;
	}

	// Inserts a new item
   public void insert(Integer x) {
      if(size == heap.length - 1) doubleSize();

      //Insert a new item to the end of the array
      int pos = ++size;

      //Percolate up; while the new item is less than its parent, move parent down
      for(; pos > 1 && x.compareTo(heap[pos/2]) < 0; pos = pos/2 )
         heap[pos] = heap[pos/2];

      heap[pos] = x;
   }
   private void doubleSize() {
	   Integer [] old = heap;
      heap = new Integer[heap.length * 2];
      System.arraycopy(old, 1, heap, 1, size);
   }

   public String toString() {
      String out = "";
      for(int k = 1; k <= size; k++) out += heap[k]+" ";
      return out;
   }
   
   
   
   
   
   
   
   
   
   void printSmaller(int val) {
		if(heap != null && heap[1] != null) {
			printSmaller(val, 1); // start at root node
		}
		System.out.println();
	}

	void printSmaller(Integer val, int index) {
		if (heap[index].compareTo(val) < 0) {
			System.out.print(heap[index] + " ");
			// recurse to child nodes
			if(inHeap(2*index)) {
				printSmaller(val, 2*index);
			}
			if(inHeap(2*index+1)) {
				printSmaller(val, 2*index + 1);
			}
		}
	}
	
	boolean inHeap(int n) {
		return n < heap.length && heap[n] != null;
	}

   public static void main(String[] args) {
	   Integer[] a = {8,9,7,5,2,4,11,1,3,6,10};
	   Heap h = new Heap(a);

      System.out.println(h);
      h.deleteMin();
      System.out.println(h);
      h.deleteMin();
      System.out.println(h);
      h.printSmaller(4);
   }
}