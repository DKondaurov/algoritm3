package com.company;

import java.util.Arrays;
import java.util.Objects;

public class IntegerListImpl implements IntegerList {
    private Integer[] storage;
    private int size = 0;

    public IntegerListImpl() {
        this.storage = new Integer[4];
    }

    private static void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    @Override
    public Integer add(Integer item) {
        if (size == storage.length) {
            grow();
        }
        storage[size] = item;
        size++;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        validateIndex(index);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = item;
        size++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        validateIndex(index);
        storage[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        if (indexOf(item) != -1) {
            System.arraycopy(storage, indexOf(item) + 1, storage, indexOf(item), size - 1);
            size--;
            return item;

        }
        throw new NotFoundException();
    }

    @Override
    public Integer remove2(int index) {
        Integer removeItem = storage[index];
        storage[index] = null;
        if (index != storage.length - 1) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
            size--;
            return removeItem;
        }
        throw new NotFoundException();
    }

    @Override
    public boolean contains(Integer item) {
//        sort();
        quickSort(storage, 0, storage.length - 1);
        return binarySearch(item);
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = size - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndex(index);
        return storage[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        return Arrays.equals(storage, otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        storage = new Integer[4];
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] arr = new Integer[size];
        System.arraycopy(storage, 0, arr, 0, size);
        return arr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerListImpl that = (IntegerListImpl) o;
        return size == that.size && Arrays.equals(storage, that.storage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(storage);
        return result;
    }

    private void validateIndex(int index) {
        if (index >= size - 1 && index <= 0) {
            throw new NotFoundException();
        }
    }

    private boolean binarySearch(Integer item) {
        int min = 0;
        int max = storage.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (item == storage[mid]) {
                return true;
            }

            if (item < storage[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private void grow() {
        if (size == storage.length) {
            storage = Arrays.copyOf(storage, (storage.length + (storage.length / 2)));
        }
    }

    private void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }
}
