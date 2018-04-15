package me.limeice.common.function;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class ValueOfUtilsTest {

    @Test
    public void valueOf() {
        int val = ValueOfUtils.valueOf("5", 0);
        assertEquals(val, 5);
    }

    @Test
    public void valueOf1() {
        long val = ValueOfUtils.valueOf("5", 0L);
        assertEquals(val, 5);
        val = ValueOfUtils.valueOf("", 0L);
        assertNotEquals(val, 5);
    }

    @Test
    public void valueOf2() {
        short val = ValueOfUtils.valueOf("2", (short) 0);
        assertEquals(val, 2);
    }

    @Test
    public void valueOf3() {
        byte val = ValueOfUtils.valueOf("256", (byte) 0);
        assertEquals(val, 0);
    }
}