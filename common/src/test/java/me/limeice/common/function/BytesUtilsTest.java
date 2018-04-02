package me.limeice.common.function;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BytesUtilsTest {

    private static final int i1 = 0x32155648;
    private static final int i2 = -2;
    private static final long l1 = 0x1234567812345678L;
    private static final short s1 = 0x1234;

    @Test
    public void getAndPut() {
        byte[] bsInt = BytesUtils.toBytes(i1);
        byte[] bsInt2 = BytesUtils.toBytes(i2);
        byte[] bsLong = BytesUtils.toBytes(l1);
        byte bsBoolean = BytesUtils.toBytes(true);
        byte[] bsShort = BytesUtils.toBytes(s1);

        assertEquals(i1, BytesUtils.getInt(bsInt));
        assertEquals(i2, BytesUtils.getInt(bsInt2));
        assertEquals(l1, BytesUtils.getLong(bsLong));
        assertEquals(s1, BytesUtils.getShort(bsShort));
        assertEquals(true, BytesUtils.getBoolean(bsBoolean));


        byte[] array = new byte[19];

        BytesUtils.put(array, i1, 0);
        BytesUtils.put(array, i2, 4);
        BytesUtils.put(array, l1, 8);
        BytesUtils.put(array, false, 16);
        BytesUtils.put(array, s1, 17);

        assertEquals(i1, BytesUtils.getInt(array, 0));
        assertEquals(i2, BytesUtils.getInt(array, 4));
        assertEquals(l1, BytesUtils.getLong(array, 8));
        assertEquals(s1, BytesUtils.getShort(array, 17));
        assertEquals(false, BytesUtils.getBoolean(array, 16));
    }
}