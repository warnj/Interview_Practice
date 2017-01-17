package General_Questions;
import java.util.*;
import java.math.*;

public class Algos {
	public static void main(String[] args) {

	
	}
	
	// TEST THIS METHOD FOR CORRECTNESS
	public int longestPalindromicSubstring(String s, int start, int end) {
		if(end-start == 1) {
			if(s.charAt(start)==s.charAt(end)) return 2;
			else return 1;
		} else if(s.charAt(start)==s.charAt(end)) {
			return 2 + longestPalindromicSubstring(s, start+1, end-1);
		} else {
			return 1 + Math.max(longestPalindromicSubstring(s, start+1, end), longestPalindromicSubstring(s, start, end-1));
		}
	}
	
	public static void printDescendingFreq(String words) {
		String[] wds = words.split("\\s+");
		Map<String, Integer> freqs = new HashMap<String, Integer>();
		for(String s : wds) {
			Integer n = freqs.get(s);
			if(n == null) {
				freqs.put(s, 1);
			} else {
				freqs.put(s, ++n);
			}
		}
		List<Map.Entry<String, Integer>> list = new LinkedList<>( freqs.entrySet() );
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ) {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        });
		for(Map.Entry<String, Integer> e : list) {
			System.out.println("Word: " + e.getKey() + "\t\tFreq: " + e.getValue());
		}
	}
	
	public static int[] shuffle(int[] arr) {
		Random r = new Random();
		for(int i = arr.length-1; i > 0; i--) {
			int rand = r.nextInt(i+1);
			int temp = arr[i];
			arr[i] = arr[rand];
			arr[rand] = temp;
		}
		return arr;
	}
	
	// hash a range of values together, want all the values within the range to have same hashcode
	//Simply populate a Map for all valid key values, with multiple keys mapping to the same value.
	//Assuming that you use HashMaps, this should be the most time efficient (O(1) lookups),
	//though you have more work at setup time and you use more space (when compared to a NavigableMap and use floorEntry(key)
	// to do the lookups. This should be less time efficient (O(log(N) lookups) but more space efficient.)
	//http://stackoverflow.com/questions/1314650/using-java-map-for-range-searches
	
	
	// find the missing k numbers in sequence: http://stackoverflow.com/questions/3492302/easy-interview-question-got-harder-given-numbers-1-100-find-the-missing-numbe
	// - sort the sequence and iterate through identifying differences between consecutive numbers that are greater than 1
	// - calculate the sum of the kth powers of all the numbers that should be in the sequence and compare to the sum of the kth powers of the actual numbers in the 
	//    sequence, solve a system of k equations with k unknowns for the values.
	
	// pre: sequence contains a single missing number and contains the unordered sequence of numbers from 1 to sequence.length
	// returns the missing number in sequence - O(n) time, O(1) space, works even with integer overflow
	public static int missingNumber(Object[] sequence) {
		int completeSeqLength = sequence.length + 1;
		int completeSeqSum = completeSeqLength*(completeSeqLength+1)/2; // math identity: sum of 1...n = n*(n+1)/2
		int sum = 0;
		for(int i = 0; i < sequence.length; i++) sum += (int)sequence[i];
		return completeSeqSum - sum;
	}
	
	// sequence contains two missing numbers and contains the unordered sequence of numbers from 1 to sequence.length
	public static int[] missingNumbers(Object[] sequence) {
//		int completeSeqLength = sequence.length + 2;
//		int completeSeqSum = completeSeqLength*(completeSeqLength+1)/2; // math identity: sum of 1...n = n*(n+1)/2
//		int completeSeqSquareSum = completeSeqLength*(completeSeqLength+1)*(2*completeSeqLength + 1)/6; // math identity: sum of 1^2...n^2 = n*(n+1)*(2n+1)/6
//		int sum = 0;
//		int squareSum = 0;
//		int i;
//		for(i = 0; i < sequence.length; i++) {
//			int n = (int)sequence[i];
//			sum += n;
//			squareSum += n*n;
//			//completeSeqSquareSum += (i+1) * (i+1); // do this if do not know the formula to directly calculate sum of n squares
//		}
//		//completeSeqSquareSum += (i+1) * (i+1);
//		
//		// solve 2 eqns w/ 2 unknowns
//		int missingNumSum = completeSeqSum - sum; // = x1 + x2
//		int missingNumProduct = completeSeqSquareSum - squareSum; // = x1^2 + x2^2
//		
//		// PROBLEM
//		int x1 = (int) Math.sqrt((missingNumProduct + 2*missingNumSum - missingNumSum * missingNumSum) / 2.0);
//		
//		return new int[] {missingNumSum, missingNumProduct, x1};
		
		
		int Sum = 0;
		int SumN = 0;
		BigInteger P = new BigInteger("1");
		BigInteger Np = new BigInteger("1");
		int a,b;
		int range = sequence.length+2;
		SumN = range*(range+1)/2;
		for(int i=0;i<sequence.length;i++){
			Sum += (int)sequence[i];
		}
		int s= SumN-Sum;
		int i = 0;
		for(; i<sequence.length; i++){
			P = P.multiply(new BigInteger(sequence[i].toString()));
			Np = Np.multiply(new BigInteger(Integer.toString(i+1)));
		}
		Np = Np.multiply(new BigInteger(Integer.toString(i++)));
		Np = Np.multiply(new BigInteger(Integer.toString(i++)));
		int product = Np.divide(P).intValue();
		int diffSqr = (int)Math.sqrt(s*s-4*product); // (a-b)^2 = (a+b)^2-4ab
		a = (s+diffSqr)/2;
		b= s-a;
		int [] result = {a,b};
		return result;
	}
	
	
	// returns the first two indexes in the given array that add up to the given target - O(n) time and space
	public static int[] twoSum(int[] nums, int target) {
		// https://leetcode.com/articles/two-sum/
		// only needs one pass since upon finding the second value that forms the complement, we return
	    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	    for (int i = 0; i < nums.length; i++) {
	        int complement = target - nums[i];
	        if (map.containsKey(complement)) {
	            return new int[] { map.get(complement), i };
	        }
	        map.put(nums[i], i); // map values to indexes
	    }
	    return null;
	}
	
	// returns first triplet of indexes in the given array that add up to the given target - O(n^3) time O(1) space
	public static int[] threeSumN3(int[] nums, int target) {
		for(int i = 0; i < nums.length; i++) {
			for(int j = i + 1; j < nums.length; j++) {
				for(int k = j + 1; k < nums.length; k++) {
					if(nums[i] + nums[j] + nums[k] == target) {
						return new int[] {i, j, k};
					}
				}
			}
		}
		return null;
	}
	
	public static int[] threeSumN2(int[] nums, int target) { // requires nums to be sorted
		for (int i = 0; i < nums.length - 2; i++) {
            int l = i + 1; // index of the first element in the remaining elements
            int r = nums.length - 1; // index of the last element
            while (l < nums.length - 1) {
                if (nums[i] + nums[l] + nums[r] == target)
                    return new int[] {i, l, r};
                else if (nums[i] + nums[l] + nums[r] < target)
                    l++;
                else // A[i] + A[l] + A[r] > sum
                    r--;
            }
        }
        return null;
	}
	
	public static List<Integer> primeFactors(int n) {
	    List<Integer> factors = new ArrayList<Integer>();
	    int d = 2;
	    while (n > 1) {
	        while (n % d == 0) {
	            factors.add(d);
	            n /= d;
	        }
	        d = d + 1;
	        if (d*d > n) {
	            if (n > 1) factors.add(n);
	            break;
	        }
	    }
	    return factors;
	}
	
	// Returns the count of ways we can sum  S[0...m-1] coins to get sum n
	public static int countWaysRecursive( int S[], int m, int n ) {
	    if (n == 0) return 1;// 1 solution
	    if (n < 0) return 0; //no solution exists
	    if (m <= 0 && n >= 1) return 0; //no coins and n is greater than 0, no solution
	 
	    // count is sum of solutions (i) including S[m-1] (ii) excluding S[m-1]
	    return countWaysRecursive( S, m - 1, n ) + countWaysRecursive( S, m, n-S[m-1] );
	}
	// uses dynamic programming to avoid redundant coin combinations and be more efficient
	public static long countWaysDynamic(int S[], int m, int n) {
        //Time complexity of this function: O(mn)
        //Space Complexity of this function: O(n)
 
        // table[i] will be storing the number of solutions
        // for value i. We need n+1 rows as the table is
        // constructed in bottom up manner using the base
        // case (n = 0)
        long[] table = new long[n+1];
 
        // Initialize all table values as 0
        Arrays.fill(table, 0);   //O(n)
 
        // Base case (If given value is 0)
        table[0] = 1;
 
        // Pick all coins one by one and update the table[]
        // values after the index greater than or equal to
        // the value of the picked coin
        for (int i=0; i<m; i++)
            for (int j=S[i]; j<=n; j++)
                table[j] += table[j-S[i]];
 
        return table[n];
    }
	
	public static int indexOfBinarySearch(int[] arr, int target) {
		int low = 0;
		int high = arr.length - 1;
		while(high >= low) {
			int mid = (low + high) / 2;
			if(arr[mid] == target) {
				return mid;
			} else if (arr[mid] < target) {
				low = mid + 1;
			} else { // arr[mid] > key
				high = mid - 1;
			}
		}
		return -1;
	}
	
	// Outputs all permutations of the given string.
	// https://courses.cs.washington.edu/courses/cse143/15au/lectures/11-09/17-recursive-backtracking.pdf
	public static void permute(String s) {
		permute(s, "");
	}
	private static void permute(String s, String chosen) {
		if (s.length() == 0) {
			// base case: no choices left to be made
			System.out.println(chosen);
		} else {
			// recursive case: choose each possible next letter
			for (int i = 0; i < s.length(); i++) {
				// choose; cut out a letter from each index in s, add to chosen
				char c = s.charAt(i); // choose
				s = s.substring(0, i) + s.substring(i + 1);
				chosen += c;
				// explore
				permute(s, chosen);
				// un-choose; put s back the way it was, remove last letter of chosen
				s = s.substring(0, i) + c + s.substring(i);
				chosen = chosen.substring(0, chosen.length() - 1);
			}
		}
	}
	
	// shorter way of doing same thing
	public static void permutation(String str) {
		permutation("", str);
	}
	
	private static void permutation(String prefix, String str) {
		int n = str.length();
		if (n == 0) System.out.println(prefix);
		else {
			for (int i = 0; i < n; i++)
				permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
		}
	}
	
	
	/**
	* Compares two version strings.
	*
	* Use this instead of String.compareTo() for a non-lexicographical
	* comparison that works for version strings. e.g. "1.10".compareTo("1.6").
	*
	* @note It does not work if "1.10" is supposed to be equal to "1.10.0".
	*
	* @param str1 a string of ordinal numbers separated by decimal points.
	* @param str2 a string of ordinal numbers separated by decimal points.
	* @return The result is a negative integer if str1 is _numerically_ less than str2.
	*         The result is a positive integer if str1 is _numerically_ greater than str2.
	*         The result is zero if the strings are _numerically_ equal.
	*/
	public static int versionCompare(String str1, String str2) {
		String[] vals1 = str1.split("\\.");
		String[] vals2 = str2.split("\\.");
		int i = 0;
		// set index to first non-equal ordinal or length of shortest version string
		while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
			i++;
		}
		// compare first non-equal ordinal number
		if (i < vals1.length && i < vals2.length) {
			int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
			return Integer.signum(diff);
		}
		// the strings are equal or one string is a substring of the other
		// e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
		return Integer.signum(vals1.length - vals2.length);
	}
}
