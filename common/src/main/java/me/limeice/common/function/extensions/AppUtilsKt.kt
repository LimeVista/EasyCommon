@file: JvmName("AppUtilsKt")

package me.limeice.common.function.extensions

import android.content.Context
import me.limeice.common.function.AppUtils

/**
 * App处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/03/24
 *     desc  : App工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
///////////////////////////////////////////////////////////////
/**
 * 获取版本信息
 *
 * @return 版本信息（若获取失败返回null）
 */
inline fun <reified T : Context> T.getVerName(): String? = AppUtils.getVerName(this)

/**
 * 获取版本号
 *
 * @return 版本号（若获取失败返回0）
 */
inline fun <reified T : Context> T.getVerCode(): Int = AppUtils.getVerCode(this)

/**
 * 检测服务是否运行
 *
 * @param className 服务类名
 * @return 运行状态
 */
inline fun <reified T : Context> T.isServiceRunning(className: String): Boolean =
        AppUtils.isServiceRunning(this, className)

/**
 * 停止运行中的服务
 *
 * @param className 服务类名
 * @return 是否执行成功
 * @throws ClassNotFoundException 无找到此类
 */
@Throws(ClassNotFoundException::class)
inline fun <reified T : Context> T.stopRunningService(className: String): Boolean =
        AppUtils.stopRunningService(this, className)