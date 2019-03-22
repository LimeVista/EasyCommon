@file: JvmName("EasyCFK")
@file:Suppress("NOTHING_TO_INLINE")

package me.limeice.common.function.extensions

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import me.limeice.common.base.EasyCommon
import me.limeice.common.function.*
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.io.Serializable

/**
 * @see DensityUtils.dip2px
 */
@JvmName("kt_001")
inline fun Float.dip2px() = DensityUtils.dip2px(this)

/**
 * @see DensityUtils.px2dip
 */
@JvmName("kt_002")
inline fun Float.px2dip() = DensityUtils.px2dip(this)

/**
 * @see DensityUtils.sp2px
 */
@JvmName("kt_003")
inline fun Float.sp2px() = DensityUtils.sp2px(this)

/**
 * @see DensityUtils.px2sp
 */
@JvmName("kt_004")
inline fun Float.px2sp() = DensityUtils.px2sp(this)

/**
 * @see DensityUtils.getScreenHeight
 */
@JvmName("kt_005")
inline fun getScreenWidth() = DensityUtils.getScreenWidth()

/**
 * @see DensityUtils.getScreenHeight
 */
@JvmName("kt_006")
inline fun getScreenHeight() = DensityUtils.getScreenHeight()

/**
 * @see DensityUtils.getStatusBarHeight
 */
@JvmName("kt_007")
inline fun getStatusBarHeight() = DensityUtils.getStatusBarHeight()

/**
 * @see DensityUtils.getScreenRealHeight
 */
@JvmName("kt_008")
inline fun getScreenRealHeight() = DensityUtils.getScreenRealHeight()

/**
 * @see DensityUtils.isPad
 */
@JvmName("kt_009")
inline fun isPad() = DensityUtils.isPad()

/**
 * @see DensityUtils.isSW600dp
 */
@JvmName("kt_010")
inline fun isSW600dp() = DensityUtils.isSW600dp()

/**
 * @see DensityUtils.isSW720dp
 */
@JvmName("kt_011")
inline fun isSW720dp() = DensityUtils.isSW720dp()

/**
 * @see DensityUtils.getVirtualBarHeight
 */
@JvmName("kt_012")
inline fun getVirtualBarHeight() = DensityUtils.getVirtualBarHeight()

/**
 * @see BytesUtils.toHexString
 */
@JvmName("kt_013")
inline fun ByteArray.toHexString() = BytesUtils.toHexString(this)

/**
 * @see BytesUtils.convert
 */
@JvmName("kt_014")
inline fun ByteArray.convert() = BytesUtils.convert(this)

/**
 * @see BytesUtils.convert
 */
@JvmName("kt_015")
inline fun Byte.convert() = BytesUtils.convert(this)

/**
 * @see BytesUtils.hexStringToBytes
 */
@JvmName("kt_016")
inline fun String?.hexStringToBytes() = BytesUtils.hexStringToBytes(this)

/**
 * Red
 */
@JvmName("kt_017")
inline fun Int.r() = (this and 0xFF0000) shr 16

/**
 * Green
 */
@JvmName("kt_018")
inline fun Int.g() = (this and 0xFF00) shr 8

/**
 *  Blue
 */
@JvmName("kt_019")
inline fun Int.b() = this and 0xFF

/**
 * Alpha
 */
@JvmName("kt_020")
inline fun Int.a() = this ushr 24

/**
 * @see ColorUtils.getAttrColor(Context, Int)
 */
@JvmName("kt_021")
inline fun Context.getAttrColor(@AttrRes attr: Int) = ColorUtils.getAttrColor(this, attr)

/**
 * @see ColorUtils.getColorDrawable(Context, Int)
 */
@JvmName("kt_022")
inline fun Context.getColorDrawable(@ColorRes id: Int) = ColorUtils.getColorDrawable(this, id)

/**
 * @see ColorUtils.getARGB
 */
@JvmName("kt_023")
inline fun Int.getARGB() = ColorUtils.getARGB(this)

/**
 * @see ColorUtils.getRGB
 */
@JvmName("kt_024")
inline fun Int.getRGB() = ColorUtils.getRGB(this)

/**
 * @see ContextCompat.getColor
 */
@ColorInt
@JvmName("kt_025")
inline fun @receiver:ColorRes Int.getAppColorRes() =
    ContextCompat.getColor(EasyCommon.getApp(), this)

/**
 * close io, ignore io exception
 */
@JvmName("kt_026")
inline fun Closeable?.closeIOQuietly() = try {
    this?.close()
} catch (ignored: IOException) {
    // who cares
}

/**
 * @see AppUtils.isServiceRunning
 */
@JvmName("kt_027")
inline fun String.isServiceRunning() = AppUtils.isServiceRunning(this)

/**
 * @see AppUtils.stopRunningService
 */
@JvmName("kt_028")
inline fun String.stopRunningService() = AppUtils.stopRunningService(this)

/**
 * @see DeepClone.deepClone
 */
@JvmName("kt_029")
inline fun <reified T : Serializable> T.deepClone(): T = DeepClone.deepClone(this)

/**
 * @see DeepClone.serialize
 */
@JvmName("kt_030")
inline fun <reified T : Serializable> T.serialize(file: File) = DeepClone.serialize(file, this)

/**
 * @see DeepClone.deepClone
 */
@JvmName("kt_031")
inline fun <reified T : Serializable> File.deepClone(): T? = DeepClone.deserialize(this)

/**
 * @see TextEncodeUtils.string2Html
 */
@JvmName("kt_032")
inline fun String.string2Html() = TextEncodeUtils.string2Html(this)