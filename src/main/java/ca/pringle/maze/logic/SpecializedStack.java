package ca.pringle.maze.logic;

import ca.pringle.maze.util.Arrays;
import ca.pringle.maze.util.Checks;

import java.util.ArrayList;
import java.util.List;

public final class SpecializedStack<T> {

    private final List<T[]> stack;
    private final int arraySize;
    private int listIndex;
    private int arrayIndex;

    public SpecializedStack(final int arraySize) {

        stack = new ArrayList<>();
        listIndex = 0;
        arrayIndex = 0;
        this.arraySize = Checks.check(arraySize).isGreaterThan(0, "Array size must be > 0");
    }

    void push(T value) {

        if (++arrayIndex >= arraySize) {
            stack.add(Arrays.newInstance(arraySize));
            listIndex++;
            arrayIndex = 0;
        }

        if (listIndex >= stack.size()) {
            stack.add(Arrays.newInstance(arraySize));
            listIndex = Math.min(listIndex, stack.size() - 1);
        }

        stack.get(listIndex)[arrayIndex] = value;
    }

    T peek() {
        return stack.get(listIndex)[arrayIndex];
    }

    T pop() {
        final T value = peek();
        if (arrayIndex == 0) {
            arrayIndex = arraySize - 1;
            listIndex = Math.max(0, listIndex - 1);
        } else {
            arrayIndex--;
        }
        return value;
    }

    int size() {
        return listIndex * arraySize + arrayIndex;
    }

    boolean isEmpty() {
        return size() == 0;
    }

    T[] toArray() {

        final T[] array = Arrays.newInstance(size());
        int sIndex = 0;
        int aIndex = 0;

        for (int i = 0; i < size(); i++) {
            if (++aIndex == arraySize) {
                aIndex = 0;
                sIndex++;
            }
            array[i] = stack.get(sIndex)[aIndex];
        }

        return array;
    }
}
