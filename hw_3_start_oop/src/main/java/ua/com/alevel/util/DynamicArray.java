package ua.com.alevel.util;

import ua.com.alevel.entity.User;

public class DynamicArray {
    private final int INITIAL_CAPACITY = 2;
    private final double INCREMENT = 1.5;
    private Object[] array;
    private int capacity;
    private int size;

    public DynamicArray() {
        array = new Object[INITIAL_CAPACITY];
        size = 0;
        capacity = INITIAL_CAPACITY;
    }

    public void add(Object element) {
        if (size == capacity) increaseArraySize();
        array[size] = element;
        size++;
    }

    private void increaseArraySize() {
        Object[] tempArray = new Object[(int) (capacity * INCREMENT + 1)];
        System.arraycopy(array, 0, tempArray, 0, capacity);
        array = tempArray;
        capacity = capacity * (int) (capacity * INCREMENT + 1);
    }

    public void delete(int element) {
        for (int i = element; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
    }

    public void out() {
        for (int i = 0; i < size; i++) {
            System.out.println(array[i]);
        }
    }

    public int size() {
        return size;
    }

    public Object getElement(int element) {
        return array[element];
    }
}


