package Specific_Questions;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

public class MedianFinder {

    private PriorityQueue<Integer> smallHalf;
    private PriorityQueue<Integer> bigHalf;

    public MedianFinder() {
        smallHalf = new PriorityQueue<>(Collections.reverseOrder()); // max heap of the smaller numbers
        bigHalf = new PriorityQueue<>(); // min heap of the larger numbers
    }

    public void addNum(int num) {
        if (smallHalf.isEmpty() && bigHalf.isEmpty()) {
            smallHalf.add(num);
            return;
        }

        if (smallHalf.size() > bigHalf.size()) {
            int smaller = smallHalf.peek();
            if (num < smaller) {
                // keep the heap sizes within 1
                bigHalf.add(smallHalf.remove());
                smallHalf.add(num);
            } else {
                bigHalf.add(num);
            }
        } else {
            int bigger = bigHalf.peek();
            if (num > bigger) {
                smallHalf.add(bigHalf.remove());
                bigHalf.add(num);
            } else {
                smallHalf.add(num);
            }
        }
//        System.out.println("lower half: " + smallHalf);
//        System.out.println("upper half: " + bigHalf);
    }

    public double findMedian() {
        if (smallHalf.isEmpty() && bigHalf.isEmpty()) throw new EmptyStackException();

        if (!smallHalf.isEmpty() && smallHalf.size() == bigHalf.size()) {
            return (smallHalf.peek() + bigHalf.peek()) / 2.0;
        } else if (smallHalf.size() > bigHalf.size()) {
            return smallHalf.peek();
        } else {
            return bigHalf.peek();
        }
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);    // arr = [1]
        mf.addNum(2);    // arr = [1, 2]
        assertEquals(1.5, mf.findMedian(), 0.00001);
        mf.addNum(3);    // arr[1, 2, 3]
        assertEquals(2.0, mf.findMedian(), 0.00001);
    }
}
