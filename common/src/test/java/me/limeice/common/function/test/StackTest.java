package me.limeice.common.function.test;

import org.junit.Test;

import me.limeice.common.function.algorithm.util.ArrayStack;
import me.limeice.common.function.algorithm.util.IStack;

import static org.junit.Assert.*;

public class StackTest {

    @Test
    public void arrayDeepClone() {
        ArrayStack<String> as = new ArrayStack<>();
        as.push("1").push("2").push("3");
        IStack<String> asCopy = as.deepClone();
        assertEquals(asCopy.size(), as.size());
        assertEquals(as.first(), asCopy.first());
    }

    @Test
    public void arrayForEach() {
        ArrayStack<String> as = new ArrayStack<>();
        as.push("1").push("2").push("3");
        int i = 0;
        for (String element : as) {
            String value = String.valueOf(++i);
            assertEquals(element, value);
        }
    }
}
