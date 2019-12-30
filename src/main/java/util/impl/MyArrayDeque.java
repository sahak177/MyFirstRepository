package util.impl;

import util.MyDeque;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<T> implements MyDeque<T> {

    private Object[] elements;

    private int head;

    private int tail;

    private static final int MIN_INITIAL_CAPACITY = 8;

    private int calculateSize(int numElements) {
        int initialCapacity = MIN_INITIAL_CAPACITY;
        if (numElements >= initialCapacity) {
            initialCapacity = numElements;
            initialCapacity |= (initialCapacity >>> 1);
            initialCapacity |= (initialCapacity >>> 2);
            initialCapacity |= (initialCapacity >>> 4);
            initialCapacity |= (initialCapacity >>> 8);
            initialCapacity |= (initialCapacity >>> 16);
            initialCapacity++;

            if (initialCapacity < 0)   // Too many elements, must back off
                initialCapacity >>>= 1;// Good luck allocating 2 ^ 30 elements
        }
        return initialCapacity;
    }

    public MyArrayDeque() {
        elements = new Object[16];
    }


    public MyArrayDeque(int numElements) {
        elements = new Object[calculateSize(numElements)];
    }


    public MyArrayDeque(Collection<? extends T> c) {
        elements = new Object[calculateSize(c.size())];
        addAll(c);
    }

    public boolean addAll(Collection<? extends T> values) {
        for (T item:values) {
            add(item);
        }
        return true;
    }
    @Override
    public boolean add(T value) {
        addFirst(value);
        return true;
    }

    @Override
    public boolean offer(T value) {
        offerFirst(value);
        return true;
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public void push(T value) {
        addFirst(value);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public void addFirst(T e) {
        if (e == null)
            throw new NullPointerException();
        elements[head = (head - 1) & (elements.length - 1)] = e;
        if (head == tail)
            doubleCapacity();
    }

    @Override
    public void addLast(T e) {
        if (e == null)
            throw new NullPointerException();
        elements[tail] = e;
        if ((tail = (tail + 1) & (elements.length - 1)) == head)
            doubleCapacity();
    }

    private void doubleCapacity() {
        int p = head;
        int n = elements.length;
        int r = n - p;
        int newCapacity = n * 2;
        if (newCapacity < 0)
            throw new IllegalStateException("Sorry, deque too big");
        Object[] a = new Object[newCapacity];
        System.arraycopy(elements, p, a, 0, r);
        System.arraycopy(elements, 0, a, r, p);
        elements = a;
        head = 0;
        tail = n;
    }

    @Override
    public boolean offerFirst(T e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(T e) {
        addLast(e);
        return true;
    }

    @Override
    public T removeFirst() {
        T x = pollFirst();
        if (x == null)
            throw new NoSuchElementException();
        return x;
    }

    @Override
    public T removeLast() {
        T x = pollLast();
        if (x == null)
            throw new NoSuchElementException();
        return x;
    }

    @Override
    public T pollFirst() {
        int h = head;
        T result = (T) elements[h];
        if (result == null)
            return null;
        elements[h] = null;
        head = (h + 1) & (elements.length - 1);
        return result;

    }

    @Override
    public T pollLast() {
        int h = tail;
        T result = (T) elements[h];
        if (result == null)
            return null;
        elements[h] = null;
        tail = (h + 1) & (elements.length - 1);
        return result;
    }

    @Override
    public T getFirst() {
        int h = head;
        T result = (T) elements[h];
        if (result == null)
            throw new NoSuchElementException();
        return result;

    }

    @Override
    public T getLast() {
        int h = tail;
        T result = (T) elements[h];
        if (result == null)
            throw new NoSuchElementException();
        return result;
    }

    @Override
    public T peekFirst() {
        return (T) elements[head];
    }

    @Override
    public T peekLast() {
        return (T) elements[tail];
    }

    @Override
    public int size() {
        return (tail - head) & (elements.length - 1);
    }

    @Override
    public boolean contains(Object obj) {
        Iterator iter = this.iterator();
        while (iter.hasNext()) {
            if (iter.next() == obj) {
                return true;
            }
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new DeqIterator();
    }

    private class DeqIterator implements Iterator<T> {

        private int cursor = head;

        private int fence = tail;

        private int lastRet = -1;

        public boolean hasNext() {
            return cursor != fence;
        }

        public T next() {
            if (cursor == fence)
                throw new NoSuchElementException();

            T result = (T) elements[cursor];

            if (tail != fence || result == null)
                throw new ConcurrentModificationException();
            lastRet = cursor;
            cursor = (cursor + 1) & (elements.length - 1);
            return result;
        }
    }
}