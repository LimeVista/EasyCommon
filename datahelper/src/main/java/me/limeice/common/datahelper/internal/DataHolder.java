package me.limeice.common.datahelper.internal;

import java.nio.charset.Charset;

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

            default:
                throw new UnsupportedOperationException("Type Unknown or Unsupported!");
        }
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
                // TODO

            case TYPE_ARRAY_1_BYTE:
                meta.size = ((byte[]) objects).length;
                return (byte[]) objects;

            case TYPE_ARRAY_1_SHORT:
                // TODO

            case TYPE_ARRAY_1_INT:
                // TODO

            case TYPE_ARRAY_1_FLOAT:
                // TODO

            case TYPE_ARRAY_1_LONG:
                // TODO

            case TYPE_ARRAY_1_DOUBLE:
                // TODO

            case TYPE_ARRAY_1_STRING:
                // TODO

            case TYPE_NONE:
                return null;

            default:
                throw new UnsupportedOperationException("Type Unknown or Unsupported!");
        }
    }
}
