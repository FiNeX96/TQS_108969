package tqs.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;

import java.util.NoSuchElementException;

public class TQSStackTest {

    TQSStack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new TQSStack<Integer>();
    }

    @Test
    @DisplayName("A stack is empty on construction.")
    public void EmptyOnConstruction() {
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("A stack has size 0 on construction.")
    public void Size0OnConstruction() {

        assertTrue(stack.size() == 0);
    }

    @Test
    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    public void PushTest() {

        stack.push(1);
        stack.push(3);
        stack.push(2);
        assertTrue(stack.size() == 3 && stack.isEmpty() == false);

    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x.")
    public void PopTest() {

        stack.push(1);
        stack.push(2);
        assertTrue(stack.pop() == 2);
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    public void PeekTest() {

        stack.push(1);
        stack.push(2);
        assertTrue(stack.peek() == 2);
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    public void SizeTest() {

        stack.push(1);
        stack.push(2);
        assertTrue(stack.stack_size == 2);
        assertTrue(stack.size() == 2);
        stack.pop();
        stack.pop();
        assertTrue(stack.size() == 0);
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    public void PopFromEmpty() {

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test
    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    public void PeekFromEmpty() {

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });
    }

    @Test
    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    public void PushToFull() {

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            stack.push(6);
        });
    }

}
