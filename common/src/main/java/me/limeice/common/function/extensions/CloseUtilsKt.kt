@file: JvmName("CloseUtilsKt")

package me.limeice.common.function.extensions

import java.io.Closeable
import java.io.IOException

/**
 * <pre>
 *      author: LimeVista(Lime)
 *      time  : 2018/03/12
 *      desc  : 关闭相关工具类
 *      github: https://github.com/LimeVista/EasyCommon
 *</pre>
 */

/**
 * 关闭IO(静默操作)
 *
 */
fun Closeable?.closeIOQuietly() =
        try {
            this?.close()
        } catch (ignored: IOException) {
            // who cares
        }
