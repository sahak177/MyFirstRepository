package util;

public interface MyDeque<T> extends MyQueue<T> {
    void addFirst(T e);
    void addLast(T e);
    boolean offerFirst(T e);
    boolean offerLast(T e);
    T removeFirst();
    T removeLast();
    T pollFirst();
    T pollLast();
    T getFirst();
    T getLast();
    T peekFirst();
    T peekLast();
    public int size();
    boolean contains(Object o);

}
