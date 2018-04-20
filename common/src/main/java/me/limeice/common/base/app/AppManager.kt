@file:Suppress("unused")

package me.limeice.common.base.app

import android.app.Activity
import me.limeice.common.function.algorithm.util.ArrayStack

/**
 * Application Manager
 * Created by LimeVista(Lime) on 2018/02/28.
 */
class AppManager private constructor() {

    private val mActStack: ArrayStack<Activity> = ArrayStack()

    companion object {
        @JvmStatic val inst: AppManager by lazy { AppManager() }
    }

    /**
     * 结束当前Activity
     */
    fun finishActivity() {
        mActStack.pop()?.let {
            if (!it.isFinishing) it.finish()
        }
    }

    /**
     * 添加Activity
     */
    fun addActivity(activity: Activity) {
        mActStack.push(activity)
    }

    /**
     * 当前Activity
     */
    fun currentActivity(): Activity? = mActStack.last()


    /**
     * 结束指定的Activity
     *
     * @param activity Activity
     */
    fun finishActivity(activity: Activity) {
        mActStack.remove(activity)
        activity.finish()
    }

    /**
     * 移除指定的Activity
     *
     * @param activity Activity
     */
    fun removeActivity(activity: Activity) = mActStack.remove(activity)

    /**
     * 返回到指定的Activity
     */
    fun <T> returnToActivity(clazz: Class<T>) where T : Activity {
        while (mActStack.size() > 0) {
            val act = mActStack.pop() ?: continue
            if (act.javaClass == clazz)
                break
            else
                act.finish()
        }
    }

    /**
     * 是否已经打开指定的activity
     * @param clazz Activity类名
     * @return
     */
    fun <T> isOpenActivity(clazz: Class<T>): Boolean where T : Activity {
        var isOpen = false
        mActStack.forEach { act ->
            if (act.javaClass == clazz) {
                isOpen = true
                return@forEach
            }
        }
        return isOpen
    }

    /**
     * 退出应用程序
     * @param isBackground 是否开开启后台运行
     */
    fun appExit(isBackground: Boolean) {
        try {
            finishAll()
        } catch (e: Exception) {
            // ignore
        } finally {
            // if not exist background,kill system
            if (!isBackground) System.exit(0)
        }
    }

    /**
     * 清除所有Activity
     */
    @Synchronized
    private fun finishAll() {
        mActStack.forEach({ act: Activity? -> act?.finish() })
        mActStack.clear()
    }
}

/***********************************************************************
 *
 *                          Kotlin 扩展函数
 *
 **********************************************************************/

/**
 * 结束当前Activity
 */
fun Activity.finishCurrent() = AppManager.inst.finishActivity()


