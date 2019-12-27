package util.impl;

import util.MyList;

import java.util.Collection;

public class MyLinkedList<T> implements MyList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    public MyLinkedList(MyList<? extends T> c) {
        this();
        addAll(c);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(Object object) {
        Node<T> container = first;
        for (int i = 0; i < size; i++) {
            container = container.next;
            if (container.item.equals(object)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T o) {
        Node<T> container = first;
        for (int i = 0; i < size; i++) {
            container = container.next;
            if (container.item.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(int index, T value) {
        checkIndex(index);
        if (index < size / 2) {
            Node<T> container = first;
            for (int i = 0; i < index; i++) {
                container = container.next;
            }
            Node<T> nodeContainer = container.next;
            Node<T> newNode = new Node<>(container, value, nodeContainer);
            nodeContainer.prev = newNode;
            container.next = newNode;
            size++;
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            first = new Node<>(null, value, first);
        } else {
            Node<T> container = last;
            for (int i = size - 1; i > index; i--) {
                container = container.prev;
            }
            Node<T> nodeContainer = container.prev;
            Node<T> newNode = new Node<>(nodeContainer, value, container);
            container.prev = newNode;
            nodeContainer.next = newNode;
            size++;
        }

        return true;
    }

    @Override
    public boolean add(T value) {
        final Node<T> container = last;
        final Node<T> newNode = new Node<>(container, value, null);
        last = newNode;
        if (container == null)
            first = newNode;
        else
            container.next = newNode;
        size++;
        return true;
    }

    @Override
    public boolean addAll(MyList<? extends T> values) {
        for (int i = 0; i < values.size(); i++) {
            add(values.get(i));
        }
        return false;
    }

    @Override
    public T get(int index) {
        checkIndexForGet(index);
        if (index < size / 2) {
            Node<T> container = first;
            for (int i = 0; i < index; i++) {
                container = container.next;
            }
            return container.item;
        } else {
            Node<T> container = last;
            for (int i = size - 1; i > index; i--) {
                container = container.prev;
            }
            return container.item;
        }
    }

    @Override
    public boolean set(int index, T value) {
        checkIndex(index);
        if (index < size / 2) {
            Node<T> container = first;
            for (int i = 0; i < index - 1; i++) {
                container = container.next;
            }
            container.item = value;
        } else {
            Node<T> container = last;
            for (int i = size; i > index; i--) {
                container = container.prev;
            }
            container.item = value;
        }
        return true;
    }

    @Override
    public boolean remove(Object value) {
        remove(indexOf(value));
        return true;
    }


    public T remove(int index) {
        checkIndex(index);
        T returnValue;
        if (index < size / 2) {
            Node<T> container = first;
            for (int i = 0; i < index - 1; i++) {
                container = container.next;
            }
            Node<T> nodeContainer = container;
            returnValue = nodeContainer.item;
            container.prev.next = nodeContainer.next;
            nodeContainer.next.prev = nodeContainer.prev;
        } else {
            Node<T> container = last;
            for (int i = size; i > index; i--) {
                container = container.prev;
            }
            Node<T> nodeContainer = container;
            returnValue = nodeContainer.item;
            container.prev.next = nodeContainer.next;
            nodeContainer.next.prev = nodeContainer.prev;
        }

        size--;

        return returnValue;
    }

    @Override
    public boolean removeAll(MyList<? extends T> values) {
        for (int i = 0; i < values.size(); i++) {
            remove(values.get(i));
        }
        return true;
    }

    @Override
    public boolean clear(int index) {
        first = null;
        last = null;
        size = 0;
        return true;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
    }

    private void checkIndexForGet(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
    }
}
