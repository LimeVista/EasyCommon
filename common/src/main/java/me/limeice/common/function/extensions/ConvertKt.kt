@file:Suppress("NOTHING_TO_INLINE")
@file: JvmName("ConvertKt")

package me.limeice.common.function.extensions

import me.limeice.common.function.BytesUtils

/* Convert byte array to hex string. eg:[0x1A,0x2C,0x3B] -> 1A2C3B */
inline fun ByteArray.toHexString() = BytesUtils.toHexString(this)

/* Convert byte array to hex values. eg: [8,1] -> [0x08, 0x01] */
inline fun ByteArray.convert() = BytesUtils.convert(this)

/* Convert byte to hex value. eg: 8 -> 0x08 */
inline fun Byte.convert() = BytesUtils.convert(this)

/* Convert hex string to byte array. eg: 1A2C3B-> [0x1A,0x2C,0x3B] */
inline fun String?.hexStringToBytes() = BytesUtils.hexStringToBytes(this)
