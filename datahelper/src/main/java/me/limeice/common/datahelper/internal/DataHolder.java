package me.limeice.common.datahelper.internal;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import me.limeice.common.function.BytesUtils;

class DataHolder implements DataType {

    static Object get(MetaData meta, byte[] bs) {
        switch (meta.type) {
            case TYPE_BOOLEAN:
                return BytesUtils.getBoolean(bs[0]);

            case TYPE_BYTE:
                return bs[0];

            case TYPE_SHORT:
                return BytesUtils.getShort(bs);

            case TYPE_INT:
                return BytesUtils.getInt(bs);

            case TYPE_FLOAT:
                return BytesUtils.getFloat(bs);

            case TYPE_LONG:
                return BytesUtils.getLong(bs);

            case TYPE_DOUBLE:
                return BytesUtils.getDouble(bs);

            case TYPE_STRING:
                return new String(bs, Charset.forName("UTF-8"));

            case TYPE_ARRAY_1_BOOLEAN:
                return ArraysUtils.readBooleanArray1(bs);

            case TYPE_ARRAY_1_BYTE:
                return bs;

            case TYPE_ARRAY_1_SHORT:
                return ArraysUtils.readShortArray1(bs);

            case TYPE_ARRAY_1_INT:
                return ArraysUtils.readIntArray1(bs);

            case TYPE_ARRAY_1_FLOAT:
                return ArraysUtils.readFloatArray1(bs);

            case TYPE_ARRAY_1_LONG:
                return ArraysUtils.readLongArray1(bs);

            case TYPE_ARRAY_1_DOUBLE:
                return ArraysUtils.readDoubleArray1(bs);

            case TYPE_ARRAY_1_STRING:
                return ArraysUtils.readStringArray1(bs);

            case TYPE_NONE:
                return null;
        }
        if (meta.type >> 16 == TYPE_LIST >> 16) {
            return ListUtils.read(meta, bs, new ArrayList<>());
        }
        throw new UnsupportedOperationException("Type Unknown or Unsupported!");
    }

    static byte[] put(MetaData meta, Object objects) {
        switch (meta.type) {
            case TYPE_BOOLEAN:
                meta.size = 1;
                byte[] bs = new byte[1];
                bs[0] = BytesUtils.toBytes((boolean) objects);
                return bs;

            case TYPE_BYTE:
                byte[] bsBytes = new byte[1];
                meta.size = 1;
                bsBytes[0] = (byte) objects;
                return bsBytes;

            case TYPE_SHORT:
                meta.size = 2;
                return BytesUtils.toBytes((short) objects);

            case TYPE_INT:
                meta.size = 4;
                return BytesUtils.toBytes((int) objects);

            case TYPE_FLOAT:
                meta.size = 4;
                return BytesUtils.toBytes((float) objects);

            case TYPE_LONG:
                meta.size = 8;
                return BytesUtils.toBytes((long) objects);

            case TYPE_DOUBLE:
                meta.size = 8;
                return BytesUtils.toBytes((double) objects);


            case TYPE_STRING:
                byte[] bsString = ((String) objects).getBytes(Charset.forName("UTF-8"));
                meta.size = bsString.length;
                return bsString;

            case TYPE_ARRAY_1_BOOLEAN:
                meta.size = ((boolean[]) objects).length;
                return ArraysUtils.writeArray1((boolean[]) objects);

            case TYPE_ARRAY_1_BYTE:
                meta.size = ((byte[]) objects).length;
                return (byte[]) objects;

            case TYPE_ARRAY_1_SHORT:
                meta.size = ((short[]) objects).length << 1;
                return ArraysUtils.writeArray1((short[]) objects);

            case TYPE_ARRAY_1_INT:
                meta.size = ((int[]) objects).length << 2;
                return ArraysUtils.writeArray1((int[]) objects);

            case TYPE_ARRAY_1_FLOAT:
                meta.size = ((float[]) objects).length << 2;
                return ArraysUtils.writeArray1((float[]) objects);

            case TYPE_ARRAY_1_LONG:
                meta.size = ((long[]) objects).length << 3;
                return ArraysUtils.writeArray1((long[]) objects);

            case TYPE_ARRAY_1_DOUBLE:
                meta.size = ((double[]) objects).length << 3;
                return ArraysUtils.writeArray1((double[]) objects);

            case TYPE_ARRAY_1_STRING:
                byte[] bsStr = ArraysUtils.writeArray1((String[]) objects);
                meta.size = bsStr.length;
                return bsStr;

            case TYPE_NONE:
                meta.size = 0;
                return null;
        }

        if (meta.type >> 16 == TYPE_LIST >> 16) {
            //noinspection unchecked
            return ListUtils.write(meta, (List<Object>) objects);
        }
        throw new UnsupportedOperationException("Type Unknown or Unsupported!");
    }
}
