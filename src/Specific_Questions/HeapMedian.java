package Specific_Questions;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class HeapMedian {
	private static Queue<Integer> lower = new PriorityQueue<Integer>(10, Collections.reverseOrder());
	private static Queue<Integer> upper = new PriorityQueue<Integer>(); 
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int lines = s.nextInt();
		
		for(int i = 0; i < lines; i++) {
			int n = s.nextInt();
			if(i == 0) {
				lower.add(n);
			} else {
				int median = (int)median();
				int lsize = lower.size();
				int usize = upper.size();
				if(median > n && lsize < usize) {
					lower.add(n);
				} else if(median < n && lsize > usize) {
					upper.add(n);
				} else if(median > n && lsize > usize) {
					upper.add(n);
				}
			}
		}
		
		s.close();
		
	}
	
	public static double median() {
		if(lower.size() > 0 && upper.size() > 0) {
			if(lower.size() == upper.size()) {
				return ((double)lower.peek() + (double)upper.peek()) / 2;
			} else if (lower.size() > upper.size()) {
				return (double) lower.peek();
			} else {
				return (double) upper.peek();
			}
		} else if(lower.size() > 0) {
			return (double)lower.peek();
		} else if(upper.size() > 0) {
			return (double)upper.peek();
		}
		return Double.MIN_VALUE;
	}
	
}
