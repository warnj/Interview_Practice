package General_Questions;
import java.util.*;

public class HashMapDirty<K,V> {
    private Entry[] entries;
    private int size;
    private static final int DEFAULT_TABLE_SIZE = 11;
    
    public HashMapDirty() {
        entries = new Entry[DEFAULT_TABLE_SIZE];
    }

    public HashMapDirty(int capacity) {
        // set up array with load factor of 0.75
        entries = new Entry[(int) (capacity / 0.75)];
    }
    
    public boolean isEmpty() {return size == 0;}
    public void clear() { for(int i = 0; i < entries.length; i++) entries[i] = null; }
    public int getCount() {return size;}

    public V put(K key, V value) {
    	V retVal = get(key);
        if (containsKey(key)) remove(key);
        int i = indexOf(key);
        entries[i] = new Entry(key, value, entries[i]);
        if(++size > entries.length) rehash();
        return retVal;
    }

    public boolean containsKey(K key) {
        int i = indexOf(key);
        Entry current = entries[i];
        while (current != null) {
            if (current.key.equals(key))
                return true;
            current = current.next;
        }
        return false;
    }
    
    public V get(K key) {
    	int i = indexOf(key);
        Entry current = entries[i];
        while (current != null) {
            if (current.key.equals(key))
                return (V) current.value;
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
    	V retVal = null;
        if (containsKey(key)) {
            int i = indexOf(key);
            if (entries[i].key.equals(key)) {
            	retVal = (V) entries[i].value;
            	entries[i] = entries[i].next;
            } else {
                Entry current = entries[i];
                while (!current.next.key.equals(key))
                    current = current.next;
                retVal = (V) current.next.value;
                current.next = current.next.next;
            }
            size--;
        }
        return retVal;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append('{');
    	for(int i = 0; i < entries.length; i++) {
    		if(entries[i] != null) {
    			Entry cur = entries[i];
				while(cur != null) {
					sb.append(cur.toString() + ", ");
					cur = cur.next;
				}
    		}
    	}
    	if (sb.length() > 1) {
    	   sb.setLength(sb.length() - 2);
    	}
    	sb.append('}');
    	return sb.toString();
    }

    private int indexOf(K key) {
        return Math.abs(key.hashCode() % entries.length);
    }
    
    private void rehash( ) {
    	Entry[] oldArray = entries;
		entries = new Entry[nextPrime(2 * oldArray.length)];
		size = 0;
		for( int i = 0; i < oldArray.length; i++ ) {
			if(oldArray[i] != null) {
				Entry cur = oldArray[i];
				while(cur != null) {
					put((K)cur.key, (V)cur.value);
					cur = cur.next;
				}
			}
		}
	}
    
	private static int nextPrime( int n ) {
		if( n % 2 == 0 ) n++;
		for( ; !isPrime( n ); n += 2 )
			;
		return n;
	}
	private static boolean isPrime( int n ) {
		if( n == 2 || n == 3 ) return true;
		if( n == 1 || n % 2 == 0 ) return false;
		for( int i = 3; i * i <= n; i += 2 )
			if( n % i == 0 ) return false;
		return true;
	}
	
	
	
    
    public static void main(String[] args) {
    	HashMapDirty<String, Integer> map = new HashMapDirty<String, Integer>(10);
    	HashMap<String, Integer> m = new HashMap<String, Integer>();
    	String s = "";
    	System.out.println(m);
    	System.out.println(map);
    	for(int i = 0; i < 21; i++) {
    		m.put(s, i);
    		map.put(s, i);
    		s+="a";
    	}
    	System.out.println(m);
    	System.out.println(map);
    	System.out.println(map.containsKey("b"));
    }

    private static class Entry {
        public Object key;
        public Object value;
        public Entry next;

        public Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        public String toString() {
        	return  key + "=" + value;
    	}
    }
}
