package General_Questions;

// 2 ways to resolve collisions: 
// separate chaining (what this implementation does with linked list of nodes) and 
// linear probing (traversing up the array from the index given by hashfunction until find a free spot) (need a way to mark cells as empty, used or deleted)

public class HashSetDirty<E> {
    private HashNode[] entries;
    private int size;

    public HashSetDirty(int capacity) {
        // set up array with load factor of 0.75
        entries = new HashNode[(int) (capacity / 0.75)];
    }

    public void add(E value) {
        if (!contains(value)) {
            int i = indexOf(value);
            entries[i] = new HashNode(value, entries[i]);
            size++;
        }
    }

    public boolean contains(E value) {
        int i = indexOf(value);
        HashNode current = entries[i];
        while (current != null) {
            if (current.data.equals(value))
                return true;
            current = current.next;
        }
        return false;
    }

    public void remove(E value) {
        if (contains(value)) {
            int i = indexOf(value);
            if (entries[i].data.equals(value))
                entries[i] = entries[i].next;
            else {
                HashNode current = entries[i];
                while (!current.next.data.equals(value))
                    current = current.next;
                current.next = current.next.next;
            }
            size--;
        }
    }

    public int size() {
        return size;
    }

    private int indexOf(E value) {
        return Math.abs(value.hashCode() % entries.length);
    }

    private static class HashNode {
        public Object data;
        public HashNode next;

        public HashNode(Object data, HashNode next) {
            this.data = data;
            this.next = next;
        }
    }
}
