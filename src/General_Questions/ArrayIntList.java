package General_Questions;
// Class ArrayIntList can be used to store a list of integers.  This version
// has no fixed capacity.  It dynamically increases in size when necessary.

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIntList {
    private int[] elementData; // list of integers
    private int size;          // current number of elements in the list

    public static final int DEFAULT_CAPACITY = 100;

    // post: constructs an empty list of default capacity
    public ArrayIntList() {
        this(DEFAULT_CAPACITY);
    }

    // pre : capacity >= 0 (throws IllegalArgumentException if not)
    // post: constructs an empty list with the given capacity
    public ArrayIntList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }
        elementData = new int[capacity];
        size = 0;
    }

    // post: returns the current number of elements in the list
    public int size() {
        return size;
    }

    // pre : 0 <= index < size() (throws IndexOutOfBoundsException if not)
    // post: returns the integer at the given index in the list
    public int get(int index) {
        checkIndex(index);
        return elementData[index];
    }

    // post: creates a comma-separated, bracketed version of the list
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            String result = "[" + elementData[0];
            for (int i = 1; i < size; i++) {
                result += ", " + elementData[i];
            }
            result += "]";
            return result;
        }
    }

    // post : returns the position of the first occurrence of the given
    //        value (-1 if not found)
    public int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            if (elementData[i] == value) {
                return i;
            }
        }
        return -1;
    }

    // post: returns true if list is empty, false otherwise
    public boolean isEmpty() {
        return size == 0;
    }

    // post: returns true if the given value is contained in the list,
    //       false otherwise
    public boolean contains(int value) {
        return indexOf(value) >= 0;
    }

    // post: appends the given value to the end of the list
    public void add(int value) {
        add(size, value);
    }

    // pre : 0 <= index <= size() (throws IndexOutOfBoundsException if not)
    // post: inserts the given value at the given index, shifting subsequent
    //       values right
    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = value;
        size++;
    }

    // post: appends all values in the given list to the end of this list
    public void addAll(ArrayIntList other) {
        ensureCapacity(size + other.size);
        for (int i = 0; i < other.size; i++)
            add(other.elementData[i]);
    }

    // post: removes all occurrences of the values in the given list from
    //       this list
    public void removeAll(ArrayIntList other) {
        int newSize = 0;
        for (int i = 0; i < size; i++)
            if (!other.contains(elementData[i])) {
                elementData[newSize] = elementData[i];
                newSize++;
            }
        size = newSize;
    }

    // pre : 0 <= index < size()
    // post: replaces the integer at the given index with the given value
    public void set(int index, int value) {
        checkIndex(index);
        elementData[index] = value;
    }

    // post: list is empty
    public void clear() {
        size = 0;
    }

    // post: ensures that the list has the given capacity; if not, the size is
    //       doubled (or more if given capacity is even larger)
    public void ensureCapacity(int capacity) {
        if (capacity > elementData.length) {
            int newCapacity = elementData.length * 2 + 1;
            if (capacity > newCapacity) {
                newCapacity = capacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    // post: returns an iterator for this list
    public Iterator<Integer> iterator() {
        return new ArrayIntListIterator(this);
    }

    // pre : 0 <= index < size() (throws IndexOutOfBoundsException if not)
    // post: removes value at the given index, shifting subsequent values left
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }
        size--;
    }

    // post: throws an IndexOutOfBoundsException if the given index is
    //       not a legal index of the current list
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
    }
}

class ArrayIntListIterator implements Iterator<Integer> {
    private ArrayIntList list;      // list to iterate over
    private int position;           // current position within the list
    private boolean removeOK;       // whether it's okay to remove now

    // pre : list != null
    // post: constructs an iterator for the given list
    public ArrayIntListIterator(ArrayIntList list) {
		this.list = list;
		position = 0;
		removeOK = false;
    }

    // post: returns true if there are more elements left, false otherwise
    public boolean hasNext() {
		return position < list.size();
    }

    // pre : hasNext()
    // post: returns the next element in the iteration
    public Integer next() {
		if (!hasNext())
			throw new NoSuchElementException();
		int result = list.get(position);
		position++;
		removeOK = true;
		return result;
    }

    // pre : next() has been called without a call on remove (i.e., at most one
    //       call per call on next)
    // post: removes the last element returned by the iterator
    public void remove() {
		if (!removeOK)
			throw new IllegalStateException();
		list.remove(position - 1);
		position--;
		removeOK = false;
    }
}