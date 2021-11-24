package ua.com.alevel.util;

import java.util.NoSuchElementException;

public class MathSet<T extends Number & Comparable<T>> {

    private final int DEFAULT_CAPACITY = 10;
    private final double INCREMENT = 1.5;
    private Number[] mathSet;
    private int size = 0;

    public MathSet() {
        mathSet = new Number[DEFAULT_CAPACITY];
    }

    public MathSet(int capacity) {
        mathSet = new Number[capacity];
    }

    public MathSet(T[] numbers) {
        mathSet = new Number[DEFAULT_CAPACITY];
        addArrayToMathSet(numbers);
    }

    @SafeVarargs
    public MathSet(T[]... numbers) {
        mathSet = new Number[DEFAULT_CAPACITY];
        for (int i = 0; i < numbers.length; i++) {
            addArrayToMathSet(numbers[i]);
        }
    }

    public MathSet(MathSet<T> numbers) {
        mathSet = numbers.toArray();
        size = numbers.size;
    }

    @SafeVarargs
    public MathSet(MathSet<T>... numbers) {
        mathSet = new Number[DEFAULT_CAPACITY];
        join(numbers);
    }

    public void add(T n) {
        if (doesNotContain(n)) {
            if (mathSet.length == size) {
                increaseSize();
            }
            mathSet[size] = n;
            size++;
        }
    }

    @SafeVarargs
    public final void add(T... n) {
        addArrayToMathSet(n);
    }

    public void clear() {
        mathSet = new Number[DEFAULT_CAPACITY];
        size = 0;
    }

    public void join(MathSet<T> ms) {
        Number[] array = ms.toArray();
        for (int i = 0; i < ms.size; i++) {
            if (doesNotContain(array[i])) {
                if (mathSet.length == size) {
                    increaseSize();
                }
                mathSet[size] = array[i];
                size++;
            }
        }
    }

    @SafeVarargs
    public final void join(MathSet<T>... ms) {
        for (int i = 0; i < ms.length; i++) {
            join(ms[i]);
        }
    }

    public void intersection(MathSet<T> ms) {
        Number[] intersection = new Number[DEFAULT_CAPACITY];
        int intersectionSize = 0;
        Number[] msArray = ms.toArray();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < msArray.length; j++) {
                if (mathSet[i].equals(msArray[j])) {
                    if (intersection.length == intersectionSize) {
                        Number[] tempArray = new Number[(int) (intersectionSize * INCREMENT + 1)];
                        System.arraycopy(intersection, 0, tempArray, 0, intersectionSize);
                        intersection = tempArray;
                    }
                    intersection[intersectionSize] = mathSet[i];
                    intersectionSize++;
                    break;
                }
            }
        }
        mathSet = intersection.clone();
        size = intersectionSize;
    }

    @SafeVarargs
    public final void intersection(MathSet<T>... ms) {
        for (int i = 0; i < ms.length; i++) {
            intersection(ms);
        }
    }

    public void sortDesc() {
        quickSort(0, size - 1, true);
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        quickSort(firstIndex, lastIndex, true);
    }

    public void sortDesc(T value) {
        int firstIndex = getIndexByValue(value);
        if (firstIndex != -1) {
            quickSort(firstIndex, size - 1, true);
        } else throw new NoSuchElementException();
    }

    public void sortAsc(T value) {
        int firstIndex = getIndexByValue(value);
        if (firstIndex != -1) {
            quickSort(firstIndex, size - 1, false);
        } else throw new NoSuchElementException();
    }

    public void sortAsc() {
        quickSort(0, size - 1, false);
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        quickSort(firstIndex, lastIndex, false);
    }

    public T get(int index) {
        if (index >= 0 && index < size) {
            return (T) mathSet[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public T getMax() {
        T max;
        if (size > 0) {
            max = get(0);
            for (int i = 1; i < size; i++) {
                if (get(i).compareTo(max) > 0) max = get(i);
            }
            return max;
        } else throw new NoSuchElementException();
    }

    public T getMin() {
        T min;
        if (size > 0) {
            min = get(0);
            for (int i = 1; i < size; i++) {
                if (get(i).compareTo(min) < 0) min = get(i);
            }
            return min;
        } else throw new NoSuchElementException();
    }

    public Number getAverage() {
        if (size > 0) {
            double sum = 0;
            for (int i = 0; i < size; i++) {
                sum += get(i).doubleValue();
            }
            return sum / size;
        } else {
            throw new NoSuchElementException();
        }
    }

    public Number getMedian() {
        if (size > 0) {
            sortAsc();
            if (size % 2 == 0) {
                return (get(size / 2).doubleValue() + get(size / 2 - 1).doubleValue()) / 2;
            } else return get((size - 1) / 2);
        } else {
            throw new NoSuchElementException();
        }
    }

    public T[] toArray() {
        Number[] array = new Number[size];
        for (int i = 0; i < size; i++) {
            array[i] = mathSet[i];
        }
        return (T[]) array;
    }

    public T[] toArray(int firstIndex, int lastIndex) {
        int arraySize = lastIndex - firstIndex + 1;
        Number[] array = new Number[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = mathSet[i + firstIndex];
        }
        return (T[]) array;
    }

    public MathSet<T> cut(int firstIndex, int lastIndex) {
        int cutSize = lastIndex - firstIndex + 1;
        MathSet<T> cutMathSet;
        if (cutSize > DEFAULT_CAPACITY) {
            cutMathSet = new MathSet<>(cutSize);
        } else {
            cutMathSet = new MathSet<>();
        }
        for (int i = 0; i < cutSize; i++) {
            cutMathSet.set(i, mathSet[firstIndex + i]);
            cutMathSet.size++;
        }
        cutMathSet.size = cutSize;
        return cutMathSet;
    }

    public void clear(T[] numbers) {
        Number[] cleared = new Number[size];
        int clearedSize = 0;
        for (int i = 0; i < size; i++) {
            boolean match = false;
            for (int j = 0; j < numbers.length; j++) {
                if (mathSet[i].equals(numbers[j])) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                cleared[clearedSize] = mathSet[i];
                clearedSize++;
            }
        }
        mathSet = cleared.clone();
        size = clearedSize;
    }

    public int size() {
        return size;
    }

    private void set(int index, Number value) {
        mathSet[index] = value;
    }

    private boolean doesNotContain(Number number) {
        for (int i = 0; i < size; i++) {
            if (mathSet[i].equals(number)) {
                return false;
            }
        }
        return true;
    }

    private void increaseSize() {
        Number[] tempArray = new Number[(int) (size * INCREMENT + 1)];
        System.arraycopy(mathSet, 0, tempArray, 0, size);
        mathSet = tempArray.clone();
    }

    private void addArrayToMathSet(T[] n) {
        for (int i = 0; i < n.length; i++) {
            add(n[i]);
        }
    }

    private int getIndexByValue(T value) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (mathSet[i].equals(value)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void quickSort(int firstIndex, int lastIndex, boolean reversing) {
        T[] src = (T[]) mathSet;
        int leftMarker = firstIndex;
        int rightMarker = lastIndex;
        T pivot = src[(leftMarker + rightMarker) / 2];
        do {
            if (reversing) {
                while (src[leftMarker].compareTo(pivot) > 0) leftMarker++;
                while (src[rightMarker].compareTo(pivot) < 0) rightMarker--;
            } else {
                while (src[leftMarker].compareTo(pivot) < 0) leftMarker++;
                while (src[rightMarker].compareTo(pivot) > 0) rightMarker--;
            }
            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    T tmp = src[leftMarker];
                    src[leftMarker] = src[rightMarker];
                    src[rightMarker] = tmp;
                }
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);
        if (leftMarker < lastIndex) {
            quickSort(leftMarker, lastIndex, reversing);
        }
        if (firstIndex < rightMarker) {
            quickSort(firstIndex, rightMarker, reversing);
        }
    }
}


