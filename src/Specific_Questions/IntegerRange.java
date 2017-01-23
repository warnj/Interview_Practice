// used to hash integers within intervals of 10 together; 0-9, 10-19, 20-29...........
package Specific_Questions;
import General_Questions.*;

public class IntegerRange {
	public int value;

	public IntegerRange(int n) {
		value = n;
	}

	public int hashCode() {
		return value / 10;
	}

	public boolean equals(Object other) {
		if(other instanceof IntegerRange){
			IntegerRange r = (IntegerRange) other;
			return r.hashCode() == this.hashCode();
		}
		return false;
	}

	//	public T find( T x ) {
	//		int currentPos = findPos( x );
	//		return isActive( currentPos ) ? array[ currentPos ].element : null; // CHANGE THIS LINE TO:

	//		return isActive( currentPos ) ? x : null;
	//	}

	public String toString() {
		return Integer.toString(value);
	}

	public static void main(String[] args) {
		HashTableQuadraticProbing<IntegerRange> h = new HashTableQuadraticProbing<IntegerRange>( );

		for(int i = 0; i < 41; i+=10) {
			System.out.println("Inserting: " + i);
			h.insert(new IntegerRange(i));
		}

		for(int i = 2; i < 63; i+=10) {
			System.out.println("Finding: " + i);
			System.out.println(h.find(new IntegerRange(i)));
		}
	}
}
