package util.impl;

import util.MyList;

import java.util.*;

public class MyArrayList<T> implements MyList<T> {

    private static final int PERCENT_INCREASE = 30;
    private static final int DEFAULT_FILLING_INDEX = 75;
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size;

    public MyArrayList() {
        this.array = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int index) {
        if (index > 0) {
            this.array = new Object[index];
        } else if (index == 0) {
            this.array = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Illegal capacity " + index);
        }
    }

    public MyArrayList(Collection<? extends T> values) {
        if (values.isEmpty()) {
            this.array = new Object[DEFAULT_CAPACITY];
        } else if (values.size() > 0) {
            this.array = values.toArray();
            this.size = array.length;
        } else {
            throw new IllegalArgumentException("Illegal argument");
        }
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object object) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(int index, T value) {
        checkIndex(index);
        if (fillingPercent() >= DEFAULT_FILLING_INDEX) {
            growArray();
        }
        for (int i = size - 1; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = value;
        size++;
        return true;

    }

    @Override
    public boolean add(T value) {
        if (fillingPercent() >= DEFAULT_FILLING_INDEX) {
            growArray();
        }
        array[size] = value;
        size++;
        return true;
    }

    @Override
    public boolean addAll(MyList<? extends T> values) {
        if (fillingPercent() + values.size() >= DEFAULT_FILLING_INDEX) {
            growArray();
        }
        for (int i = 0; i < values.size(); i++) {
            array[size] = values.get(i);
            size++;
        }
        return true;
    }

    @Override
    public T get(int index) {
        return (T)array[index];
    }

    @Override
    public boolean remove(Object value) {
        for (int i = 0; i < size - 1; i++) {
            if (array[i].equals(value)) {
                remove(i);
                size--;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean set(int index, Object value) {
        checkIndex(index);
        array[index] = value;
        size++;
        return true;


    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Object returnValue = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return (T)returnValue;
    }


    public boolean clear(int index) {
        this.array = new Object[DEFAULT_CAPACITY];
        size=0;
        return true;
    }

    @Override
    public boolean removeAll(MyList<? extends T> values) {
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[j] == values.get(i)) {
                    remove(j);
                }
            }
        }
        return true;
    }

    private void checkIndex(int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
    }

    private int fillingPercent() {

        if(size!=0&&array.length!=0) {
            return (int)(((double)size / array.length)*100);
        }
        return 0;
    }

    private void growArray() {

        int newLength = array.length + (array.length * PERCENT_INCREASE) / 100;
        array = Arrays.copyOf(array, newLength);

        System.out.println("Error    "+array.length);
    }

}
