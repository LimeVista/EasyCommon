package me.limeice.common.datahelper.internal;

interface DataType {

    String EXT = ".w_lime";

    short TYPE_UNKNOWN = (short) 0xFFFF; // 未知类型

    short TYPE_NONE = 0x0;       // 空类型

    short TYPE_BOOLEAN = 0x1;    // 布尔类型

    short TYPE_BYTE = 0x2;       // 字节类型

    short TYPE_SHORT = 0x3;      // 短整型

    short TYPE_INT = 0x4;        // 整型

    short TYPE_FLOAT = 0x5;      // 单精度浮点型

    short TYPE_LONG = 0x6;       // 长整型

    short TYPE_DOUBLE = 0x7;     // 双精度浮点型

    // short TYPE_CHAR = 0x8;    // 字符类型(暂时不支持)

    short TYPE_STRING = 0x9;     // 字符串类型


    short TYPE_ARRAY_1_BOOLEAN = 0xA;     // 布尔类型(一维数组)

    short TYPE_ARRAY_1_BYTE = 0xB;        // 字节类型(一维数组)

    short TYPE_ARRAY_1_SHORT = 0xC;       // 短整型(一维数组)

    short TYPE_ARRAY_1_INT = 0xD;         // 整型(一维数组)

    short TYPE_ARRAY_1_FLOAT = 0xE;       // 单精度浮点型(一维数组)

    short TYPE_ARRAY_1_LONG = 0xF;        // 长整型(一维数组)

    short TYPE_ARRAY_1_DOUBLE = 0x10;     // 双精度浮点型(一维数组)

    // short TYPE_ARRAY_1_CHAR = 0x11;    // 字符类型(一维数组),请使用String

    short TYPE_ARRAY_1_STRING = 0x12;     // 字符串类型(一维数组)


//    short TYPE_ARRAY_2_BOOLEAN = 0x13;    // 布尔类型(二维数组)
//
//    short TYPE_ARRAY_2_BYTE = 0x14;       // 字节类型(二维数组)
//
//    short TYPE_ARRAY_2_SHORT = 0x15;      // 短整型(二维数组)
//
//    short TYPE_ARRAY_2_INT = 0x16;        // 整型(二维数组)
//
//    short TYPE_ARRAY_2_FLOAT = 0x17;      // 单精度浮点型(二维数组)
//
//    short TYPE_ARRAY_2_LONG = 0x18;       // 长整型(二维数组)
//
//    short TYPE_ARRAY_2_DOUBLE = 0x19;     // 双精度浮点型(二维数组)
//
//    // short TYPE_ARRAY_2_CHAR = 0x1A;    // 字符类型(二维数组),请使用Sting[]
//
//    short TYPE_ARRAY_2_STRING = 0x1B;     // 字符串类型(二维数组)
//
//
//    short TYPE_ARRAY_3_BOOLEAN = 0x1C;    // 布尔类型(三维数组)
//
//    short TYPE_ARRAY_3_BYTE = 0x1D;       // 字节类型(三维数组)
//
//    short TYPE_ARRAY_3_SHORT = 0x1E;      // 短整型(三维数组)
//
//    short TYPE_ARRAY_3_INT = 0x1F;        // 整型(三维数组)
//
//    short TYPE_ARRAY_3_FLOAT = 0x20;      // 单精度浮点型(三维数组)
//
//    short TYPE_ARRAY_3_LONG = 0x21;       // 长整型(三维数组)
//
//    short TYPE_ARRAY_3_DOUBLE = 0x22;     // 双精度浮点型(三维数组)
//
//    short TYPE_ARRAY_3_CHAR = 0x23;       // 字符类型(三维数组)
//
//    short TYPE_ARRAY_3_STRING = 0x24;     // 字符串类型(三维数组)

    /**
     * List 暂时支持范围 0x101~0x109 保留范围 0x1FF ~ 0x100
     * 支持基本类型，例如 0x101为{@code List<Boolean>}
     */
    short TYPE_LIST = 0x100;

    /**
     * Map 暂时支持范围 0x211~0x299 保留范围 0x2FF ~ 0x200
     * 支持基本类型，例如 0x149为{@code Map<Integer,String>}
     */
    short TYPE_MAP = 0x200;     // Map
}
