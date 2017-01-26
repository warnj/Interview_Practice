import java.util.*;


public class Hw312 {

	public static void main(String[] args) {
//		List<String> r = permutation("WWWBB");
//		Set<String> set = new LinkedHashSet<String>();
//		for(String s : r) {
//			set.add(s);
//		}
//		System.out.println(set);
//		System.out.println(set.size());
		
		
		
//		String[] arr = {"1","1","1","1","1","2","2","2","2","2","2","2","2","2","2"};
//		List<String[]> r = combinations(arr, 5);
//        System.out.println(r.size());
//        int count = 0;
//        for(int i = 0; i < r.size(); i++) {
//        	String[] comb = r.get(i);
//        	System.out.print(Arrays.toString(comb));
//        	int c = 0;
//        	for(int j = 0; j < comb.length; j++) {
//        		if(comb[j].equals("1")) {
//        			c++;
//        			if(c >= 3) {
//        				if(comb[0].equals("1") && comb[2].equals("1")) {
//        					count++;
//        					System.out.print("\tcontains 3 or more whites and 1st and 3rd are white");
//        				}
//        				
//        				c = 0;
//        			}
//        		}
//        	}
//        	System.out.println();
//        }
//        System.out.println(count);

		
		//1680
//		List<String> r = nChooseR("12345678", 4);
//		System.out.println(r);
//		System.out.println(r.size());
		
		
//		int n = 8;
//		int k = 4;
//		System.out.println(nChooseR(n,k));
		
		//112/495
//		
//		
////		for(int i = 0; i <40; i++) {
////			for(int j = 0 ; j < 40; j++) {
////				if(i >= j) {
////					long w = nChooseR_Worse(i,j);
////					long b = nChooseR(i,j);
////					if(w != b) {
////						System.out.println("w = " + w + "\tb = " + b + "\ti = " + i + "\tj = " + j);
////					}
////				}
////			}
////		}
//		
//		long l = funcSum(3,1);
//		System.out.println(l);
//		System.out.println(l % 13);
		
		hw3q7_2();
	}
	
	private static void hw3q7_2() {
		Integer[] seq = {0,0,0,0,0,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
		ArrayList<Integer> twenty = new ArrayList<Integer>();
		twenty.addAll(Arrays.asList(seq));
		Random r = new Random();
		
		int count = 0;
		for(int i = 0; i < 100000; i++) {
			List<Integer> l = new ArrayList<Integer>(20);
			for(int j = 0; j < 20; j++) {
				l.add(twenty.remove(r.nextInt(twenty.size())));
			}
			List<Integer> l1 = l.subList(0, 4);
			List<Integer> l2 = l.subList(4, 8);
			List<Integer> l3 = l.subList(8, 12);
			List<Integer> l4 = l.subList(12, 16);
			List<Integer> l5 = l.subList(16, 20);
			if(containsInt(l1, 0)==1 && containsInt(l2, 0)==1 &&containsInt(l3, 0)==1&&containsInt(l4, 0)==1&&containsInt(l5, 0)==1) {
				//System.out.println(l);
				count++;
			}
			twenty.addAll(Arrays.asList(seq));
		}
		System.out.println(count);
	}
	private static int containsInt(List<Integer> arr, int n){
		int num = 0;
		for(int i=0;i<arr.size();i++) {
			if(arr.get(i)==n) num++;
		}
		return num;
	}
	
	// hashing/birthdays problem
	private static void hw2q8() {
		int b = 10000;
		
		for(int n = 115; n < 126; n++) {
			double d = 1.0;
			for(int i = b; i >= b-n+1; i--) {
				d *= (double)i;
				d /= b;
			}
			System.out.println(n + ":\t" + d);
		}
	}
	
	private static long funcSum(int x, int y) { // hw1 question 5
		int a = 13;
		long result = 0;
//		for(int i = 0; i <= a; i++) {
//			result += nChooseR(a, i) * (long) Math.pow(2*x, a-i) * (long) Math.pow(3*y, i);
//		}
		for(int i = 1; i < a; i++) {
			result += nChooseR(a, i) * (long) Math.pow(x, a-i) * (long) Math.pow(12*y, i);
		}
		return result;
	}
	
	private static long nChooseR_Worse(int n, int r) {
		return fact(n)/(fact(r)*fact(n-r));
	}
	
	// much better at avoiding overflow than nChooseR_Worse and more efficient
	private static long nChooseR(int n, int r) {
		if(r == 0) return 1;
		long result = n;
		for(int i = 1; i < r; i++) result *= n - i;
		return result / fact(r);
	}
	
	private static long fact(int n)	{ // returns n!
	  if(n==0) return 1;
	  else {
	        long a=1;
	        for(int i=n; i>0 ;i--) a=a*i;
	        return a;
	  }
	}
	
//	private static void choose(String chosen, String possible, int r, List<String> l) {
//		int n = chosen.length();
//		if(n==r) {
//			l.add(chosen);
//		} else {
//			for(int i = 0; i < possible.length(); i++) {
//				if(chosen.indexOf(possible.charAt(i)) == -1) {
//					choose(chosen + )
//				}
//			}
//		}
//	}
	
	// Returns all distinct results of choosing the given number of items from the given array. Order does not matter.
	// example of input: combinations(new String[]{"1","2","3","4"}, 2);
	// example of output: [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
	public static List<String[]> combinations(String[] array, int choose) {
		if(choose > array.length || choose < 0) throw new IllegalArgumentException();
		List<String[]> list = new ArrayList<String[]>();
		combinations(array, choose, 0, new String[choose], list);
		return list;
	}	
	private static void combinations(String[] arr, int len, int startPosition, String[] result, List<String[]> resultList){
        if (len == 0){
        	resultList.add(Arrays.copyOf(result, result.length));
        } else {
        	for (int i = startPosition; i <= arr.length-len; i++){
                result[result.length - len] = arr[i];
                combinations(arr, len-1, i+1, result, resultList);
            }
        }
    }  
	
	
	public static List<String> permutation(String str) {
		List<String> r = new ArrayList<String>();
		permutation("", str, r);
		return r;
	}
	
	private static void permutation(String prefix, String str, List<String> r) {
		int n = str.length();
		if (n == 0) r.add(prefix);
		else {
			for (int i = 0; i < n; i++)
				permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), r);
		}
	}
}
