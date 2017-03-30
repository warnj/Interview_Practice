package General_Questions;
import java.util.*;
import java.math.*;

public class Algos {
	public static void main(String[] args) {

	}
	
	// SEE StringQuestions.java for combinations and permutations

	// prints the whitespace-separated words in the given string in order of descending freq 
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

	// HASH A RANGE OF VALUES - want all the values within the range to have same hashcode
	// Simply populate a Map for all valid key values, with multiple keys mapping to the same value.
	// Assuming that you use HashMaps, this should be the most time efficient (O(1) lookups),
	// though you have more work at setup time and you use more space (when compared to a NavigableMap and use floorEntry(key)
	// to do the lookups. This should be less time efficient (O(log(N) lookups) but more space efficient.)
	// http://stackoverflow.com/questions/1314650/using-java-map-for-range-searches

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




}
