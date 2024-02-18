package tqs.lab1;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TQSStack<T> {

    LinkedList<T> stack;
    public int stack_size = 0;
    public int max_size = 5;

    TQSStack() { // size for the stack
        this.stack_size = 0;
        this.stack = new LinkedList<T>();
    }

    T pop() {

        if (stack_size == 0) {
            throw new NoSuchElementException();
        }
        stack_size--;
        return stack.removeLast();

    }

    int size() {
        return stack_size;
    }

    T peek() {

        if (stack_size == 0) {
            throw new NoSuchElementException();
        }

        return stack.getLast();
    }

    void push(T element) {

        if (stack_size == max_size) {
            throw new IllegalStateException();
        }
        stack.add(element);
        stack_size++;

    }

    boolean isEmpty() {
        return this.stack_size == 0;
    }

}
