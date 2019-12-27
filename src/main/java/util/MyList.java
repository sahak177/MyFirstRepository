package util;


public interface MyList<T> {

    int size();

    int indexOf(Object o);

    boolean isEmpty();

    boolean contains(T o);

    boolean add(int index, T value);

    boolean add(T value);

    //?????
    boolean addAll(MyList<? extends T> values);

    T get(int index);

    boolean set(int index, T value);

    T remove(int index);

    boolean remove(Object value);

    boolean removeAll(MyList<? extends T> value);

    boolean clear(int index);
}
