package me.limeice.common.datahelper.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.limeice.common.function.BytesUtils;

public final class ListUtils {

    public static List<Object> read(MetaData data, byte[] bytes, List<Object> inst) {
        if (inst == null) inst = new ArrayList<>();
        switch (data.type) {

            /* Boolean */
            case 0x101:
                for (int i = 0; i < bytes.length; i++)
                    inst.add(BytesUtils.getBoolean(bytes, i));
                break;

            /* Byte */
            case 0x102:
                for (byte aByte : bytes) inst.add(aByte);
                break;

            /* Short */
            case 0x103:
                for (int i = 0; i < bytes.length << 1; i++)
                    inst.add(BytesUtils.getShort(bytes, i << 1));
                break;

            /* Int */
            case 0x104:
                for (int i = 0; i < bytes.length << 2; i++)
                    inst.add(BytesUtils.getInt(bytes, i << 2));
                break;

            /* Float */
            case 0x105:
                for (int i = 0; i < bytes.length << 2; i++)
                    inst.add(BytesUtils.getFloat(bytes, i << 2));
                break;

            /* Long */
            case 0x106:
                for (int i = 0; i < bytes.length << 3; i++)
                    inst.add(BytesUtils.getLong(bytes, i << 3));
                break;

            /* Double */
            case 0x107:
                for (int i = 0; i < bytes.length << 3; i++)
                    inst.add(BytesUtils.getDouble(bytes, i << 3));
                break;

            /* String */
            case 0x109:
                String[] sArray = ArraysUtils.readStringArray1(bytes);
                inst.addAll(Arrays.asList(sArray));
                break;

            //None
            case 0x100:
                break;

            default:
                throw new UnsupportedOperationException("Unknown Type");
        }
        return inst;
    }

    public static byte[] write(MetaData data, List<Object> value) {
        Objects.requireNonNull(value);
        byte[] array = new byte[0];
        switch (data.type) {

            /* Boolean */
            case 0x101:
                array = new byte[value.size()];
                for (int i = 0; i < array.length; i++)
                    BytesUtils.put(array, (boolean) value.get(i), i);
                break;

            /* Byte */
            case 0x102:
                array = new byte[value.size()];
                for (int i = 0; i < array.length; i++)
                    BytesUtils.put(array, (byte) value.get(i), i);
                break;

            /* Short */
            case 0x103:
                array = new byte[value.size() << 1];
                for (int i = 0; i < value.size(); i++)
                    BytesUtils.put(array, (short) value.get(i), i << 1);
                break;

            /* Int */
            case 0x104:
                array = new byte[value.size() << 2];
                for (int i = 0; i < value.size(); i++)
                    BytesUtils.put(array, (int) value.get(i), i << 2);
                break;

            /* Float */
            case 0x105:
                array = new byte[value.size() << 2];
                for (int i = 0; i < value.size(); i++)
                    BytesUtils.put(array, (float) value.get(i), i << 2);
                break;

            /* Long */
            case 0x106:
                array = new byte[value.size() << 3];
                for (int i = 0; i < value.size(); i++)
                    BytesUtils.put(array, (long) value.get(i), i << 3);
                break;

            /* Double */
            case 0x107:
                array = new byte[value.size() << 3];
                for (int i = 0; i < value.size(); i++)
                    BytesUtils.put(array, (double) value.get(i), i << 3);
                break;

            /* String */
            case 0x109:
                //noinspection SuspiciousToArrayCall
                String[] strArray = value.toArray(new String[value.size()]);
                array = ArraysUtils.writeArray1(strArray);
                break;

            //None
            case 0x100:
                break;

            default:
                throw new UnsupportedOperationException("Unknown Type");
        }
        data.size = array.length;
        return array;
    }
}
