package General_Questions;
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// T find( x )     --> Return item that matches x
// void makeEmpty( )      --> Remove all items

//Probing table implementation of hash tables.
//Note that all "matching" is based on the equals method.
public class HashTableQuadraticProbing<T> {
	public HashTableQuadraticProbing( ) {
		this( DEFAULT_TABLE_SIZE );
	}
	
	//@param size the approximate initial size.
	public HashTableQuadraticProbing( int size ) {
		allocateArray( size );
		makeEmpty( );
	}
	
	//Insert into the hash table. If the item is already present, do nothing.
	public void insert( T x ) {
		// Insert x as active
		int currentPos = findPos( x );
		if( isActive( currentPos ) ) return;
		
		array[ currentPos ] = new HashEntry<T>( x, true );
		
		if( ++currentSize > array.length / 2 ) rehash( );
	}
	
	// expand the hashtable
	private void rehash( ) {
		HashEntry<T>[ ] oldArray = array;
		
		// Create a new double-sized, empty table
		allocateArray( nextPrime( 2 * oldArray.length ) );
		currentSize = 0;
		
		// Copy table over
		for( int i = 0; i < oldArray.length; i++ )
			if( oldArray[ i ] != null && oldArray[ i ].isActive ) 
				insert( oldArray[ i ].element );
		
		return;
	}
	
	/**
	* Method that performs quadratic probing resolution.
	* @param x the item to search for.
	* @return the position where the search terminates.
	*/
	private int findPos( T x ) {
		int collisionNum = 0;
		int currentPos = Math.abs(x.hashCode() % array.length);
		
		while( array[ currentPos ] != null && !array[ currentPos ].element.equals( x ) ) {
			currentPos += 2 * ++collisionNum - 1;  // Compute ith probe
			if( currentPos >= array.length )       // Implement the mod
				currentPos -= array.length;
		}
		//we are either at an empty spot or the spot that already contains the element
		return currentPos;
	}
	
	//@param x the item to remove.
	public void remove( T x ) {
		int currentPos = findPos( x );
		if( isActive( currentPos ) )
			array[ currentPos ].isActive = false;
	}
	
	/**
	* Find an item in the hash table.
	* @param x the item to search for.
	* @return the matching item.
	*/
	public T find( T x ) {
		int currentPos = findPos( x );
		return isActive( currentPos ) ? array[ currentPos ].element : null;
		//return isActive( currentPos ) ? x : null;
	}
	
	/**
	* Return true if currentPos exists and is active.
	* @param currentPos the result of a call to findPos.
	* @return true if currentPos is active.
	*/
	private boolean isActive( int currentPos ) {
		return array[ currentPos ] != null && array[ currentPos ].isActive;
	}
	
	//Make the hash table logically empty.
	public void makeEmpty( ) {
		currentSize = 0;
		for( int i = 0; i < array.length; i++ )
			array[ i ] = null;
	}
	
	private static final int DEFAULT_TABLE_SIZE = 11;
	
	/** The array of elements. */
	private HashEntry<T>[ ] array;   // The array of elements
	private int currentSize;       // The number of occupied cells
	
	//Internal method to allocate array.
	private void allocateArray( int arraySize ) {
		array = new HashEntry[ arraySize ];
	}
	
	/**
	* Internal method to find a prime number at least as large as n.
	* @param n the starting number (must be positive).
	* @return a prime number larger than or equal to n.
	*/
	private static int nextPrime( int n ) {
		if( n % 2 == 0 ) n++;
		
		for( ; !isPrime( n ); n += 2 )
			;
		
		return n;
	}
	
	/**
	* Internal method to test if a number is prime.
	* Not an efficient algorithm.
	* @param n the number to test.
	* @return the result of the test.
	*/
	private static boolean isPrime( int n ) {
		if( n == 2 || n == 3 ) return true;
		
		if( n == 1 || n % 2 == 0 ) return false;
		
		for( int i = 3; i * i <= n; i += 2 )
			if( n % i == 0 ) return false;
		
		return true;
	}
	
	public static void main( String [ ] args ) {
		HashTableQuadraticProbing H = new HashTableQuadraticProbing( );
		
		final int NUMS = 4000;
		final int GAP  =   37;
		
		System.out.println( "Checking... (no more output means success)" );
		
		for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
			H.insert( new Integer( i ) );
		for( int i = 1; i < NUMS; i+= 2 )
			H.remove( new Integer( i ) );
		
		for( int i = 2; i < NUMS; i+=2 )
			if( ((Integer)(H.find( new Integer( i ) ))).intValue( ) != i )
				System.out.println( "Find fails " + i );
		
		for( int i = 1; i < NUMS; i+=2 ) {
			if( H.find( new Integer( i ) ) != null )
				System.out.println( "OOPS!!! " +  i  );
		}
		
		
		
		HashTableQuadraticProbing<Integer> h = new HashTableQuadraticProbing<Integer>( );
		
		for(int i = 0; i < 21; i++) h.insert(i);
		
		for(int i = 0; i < 21; i+=2) h.remove(i);
		
		for(int i = 0; i < 21; i++) {
			System.out.println(h.find(i));
		}
		
	}



	// The basic entry stored in ProbingHashTable
	private static class HashEntry<E> {
	    E element;   // the element: null is empty
	    boolean  isActive;  // false is deleted

	    public HashEntry( E e ) {
	        this( e, true );
	    }

	    public HashEntry( E e, boolean i ) {
	        element   = e;
	        isActive  = i;
	    }
	}
}

