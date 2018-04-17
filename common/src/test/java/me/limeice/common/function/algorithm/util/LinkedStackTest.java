package me.limeice.common.function.algorithm.util;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class LinkedStackTest {

    @Test
    public void test() {
        LinkedStack<Integer> stack = new LinkedStack<>();
        assertTrue(stack.isEmpty());
        stack.push(2);
        stack.push(4);

        assertFalse(stack.isEmpty());

        int i4 = stack.poll();
        assertEquals(i4, 4);

        stack.push(6);

        assertEquals(stack.size(), 2);
        stack.push(8);
        stack.forEach(it -> System.out.print(it + ","));

        System.out.println();
        System.out.println("first:" + stack.first());
        System.out.println("last: " + stack.last());
        System.out.println("size: " + stack.size());

        Iterator<Integer> it = stack.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        assertEquals(stack.size(), 0);
        assertTrue(stack.isEmpty());
    }

    @Test
    public void test2() {
        LinkedStack<Integer> stack = new LinkedStack<>();
        assertTrue(stack.isEmpty());
        stack.push(2);
        stack.push(4);
        stack.push(6);
        stack.push(8);
        System.out.println();
        stack.forEach(it -> System.out.print(it + ","));
        System.out.println();
        System.out.println("first:" + stack.first());
        System.out.println("last: " + stack.last());
        System.out.println("size: " + stack.size());

        Iterator<Integer> it = stack.iterator();
        while (it.hasNext()) {
            int next = it.next();
            if (next > 2 && next < 8) it.remove();
        }
        assertEquals(stack.size(), 2);
    }

    @Test
    public void testClone() {
        LinkedStack<Integer> stack = new LinkedStack<>();
        assertTrue(stack.isEmpty());
        stack.push(2);
        stack.push(4);
        stack.push(6);
        stack.push(8);
        LinkedStack<Integer> stackClone = stack.deepClone();
        assertNotEquals(stack, stackClone);
    }
}