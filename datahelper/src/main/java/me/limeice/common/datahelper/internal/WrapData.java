package me.limeice.common.datahelper.internal;

/**
 * 数据包裹
 */
final class WrapData {

    MetaData meta;

    byte[] bytes;   // 用于MetaDataReader

    Object data;    // 用户AllDataReader
}
