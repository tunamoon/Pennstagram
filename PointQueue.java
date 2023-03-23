package org.cis1200;

import java.util.LinkedList;
import java.util.List;

/**
 * This is an implementation of a Queue in Java that uses a built-in data
 * structure about which we haven't learned yet. You should assume that it
 * works just like our Queues in OCaml. Each element in the queue is an array
 * of two ints.
 *
 * This class will be useful to you when you write the flood() function in
 * Manipulate.java.
 * 
 * You do not need to modify this file.
 */
public class PointQueue {

    // The internal data structure
    private final List<int[]> l = new LinkedList<>();

    /**
     * Add an element to the queue.
     *
     * @param v The int array to add to the queue.
     */
    public void add(int[] v) {
        l.add(v);
    }

    /**
     * Determine whether the queue is empty.
     *
     * @return true if the queue is empty; false otherwise.
     */
    public boolean isEmpty() {
        return l.isEmpty();
    }

    /**
     * Remove an element from the queue.
     *
     * @param index The (zero-based) position of the element to be removed.
     * @return The element that was removed from the list.
     */
    public int[] remove(int index) {
        return l.remove(index);
    }

    /**
     * Retrieves the size of the queue.
     *
     * @return The size of the queue.
     */
    public int size() {
        return l.size();
    }

    /**
     * Determines whether the queue contains a given value.
     *
     * @param c The value to check for existence.
     * @return true if the value is in the queue; false otherwise.
     */
    public boolean contains(int[] c) {
        return l.contains(c);
    }

    /**
     * Get an element from the queue.
     *
     * @param index The location of the element to get.
     * @return The element at the given location in the queue.
     */
    public int[] get(int index) {
        return l.get(index);
    }

    /**
     * Get a copy of the queue.
     *
     * @return A deep copy of the queue.
     */
    public PointQueue deepCopy() {
        PointQueue iQ = new PointQueue();
        for (int[] i : l) {
            iQ.add(new int[] { i[0], i[1] });
        }
        return iQ;
    }

}
