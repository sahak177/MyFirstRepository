package util;

public interface MyQueue<T> {

    boolean add(T value);

    boolean offer(T value);

    T remove();

    T poll();

    T element();

    void push(T value);

    T pop();

    T peek();
}
