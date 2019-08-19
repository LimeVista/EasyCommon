@file:Suppress("unused")

package me.limeice.common.base.app

import android.app.Activity
import androidx.annotation.MainThread
import me.limeice.common.base.AndroidScheduler
import me.limeice.common.function.algorithm.util.ArrayStack
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * Application Manager
 *
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2018/02/28, last update 2019.02.14
 *     desc  : Application Manager
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
class AppManager private constructor() {

    private val mActStack: ArrayStack<Activity> = ArrayStack()
    private val mRWLock = ReentrantReadWriteLock()

    companion object {
        @JvmStatic
        val inst: AppManager by lazy { AppManager() }
    }

    /**
     * 结束当前Activity
     */
    @MainThread
    fun finishActivity() {
        AndroidScheduler.requireMainThread()
        try {
            mRWLock.writeLock().lock()
            mActStack.pop()?.let {
                if (!it.isFinishing) it.finish()
            }
        } finally {
            mRWLock.writeLock().unlock()
        }
    }

    /**
     * 添加Activity
     */
    fun addActivity(activity: Activity) {
        try {
            mRWLock.writeLock().lock()
            mActStack.push(activity)
        } finally {
            mRWLock.writeLock().unlock()
        }
    }

    /**
     * 当前Activity
     */
    fun currentActivity(): Activity? {
        try {
            mRWLock.readLock().lock()
            return mActStack.last()
        } finally {
            mRWLock.readLock().unlock()
        }
    }


    /**
     * 结束指定的Activity
     *
     * @param activity Activity
     */
    @MainThread
    fun finishActivity(activity: Activity) {
        AndroidScheduler.requireMainThread()
        try {
            mRWLock.writeLock().lock()
            mActStack.remove(activity)
            if (!activity.isFinishing)
                activity.finish()
        } finally {
            mRWLock.writeLock().unlock()
        }
    }

    /**
     * 移除指定的Activity
     *
     * @param activity Activity
     */
    fun removeActivity(activity: Activity): Boolean {
        mRWLock.writeLock().lock()
        val result = mActStack.remove(activity)
        mRWLock.writeLock().unlock()
        return result
    }

    /**
     * 返回到指定的Activity
     */
    @MainThread
    fun <T> returnToActivity(clazz: Class<T>) where T : Activity {
        AndroidScheduler.requireMainThread()
        try {
            mRWLock.writeLock().lock()
            while (mActStack.size() > 0) {
                val act = mActStack.pop() ?: continue
                if (act.javaClass == clazz) {
                    mActStack.push(act)
                    break
                } else {
                    act.finish()
                }
            }
        } finally {
            mRWLock.writeLock().unlock()
        }
    }

    /**
     * 查找是否存在已初始化好的 Activity
     *
     * @param clazz Class<T> Activity类名
     * @return Activity? 不存在时返回空
     */
    fun <T> findActivity(clazz: Class<T>): Activity? where T : Activity {
        try {
            mRWLock.readLock().lock()
            for (act in mActStack) {
                if (act.javaClass == clazz)
                    return act
            }
        } finally {
            mRWLock.readLock().unlock()
        }
        return null
    }

    /**
     * 是否已经打开指定的activity
     * @param clazz Activity类名
     * @return
     */
    fun <T> isOpenActivity(clazz: Class<T>): Boolean where T : Activity {
        var isOpen = false
        try {
            mRWLock.readLock().lock()
            mActStack.forEach { act ->
                if (act.javaClass == clazz) {
                    isOpen = true
                    return@forEach
                }
            }
        } finally {
            mRWLock.readLock().unlock()
        }
        return isOpen
    }

    /**
     * 遍历打开的 Activity,只读模式
     * @param consumer me.limeice.common.function.Consumer<Activity>
     */
    fun forEachReadOnly(consumer: me.limeice.common.function.Consumer<Activity>) {
        try {
            mRWLock.readLock().lock()
            mActStack.foreach(consumer)
        } finally {
            mRWLock.readLock().unlock()
        }
    }

    /**
     * 遍历打开的 Activity,读写模式
     * @param consumer me.limeice.common.function.Consumer<Activity>
     */
    fun forEachReadAndWrite(consumer: me.limeice.common.function.Consumer<Activity>) {
        try {
            mRWLock.writeLock().lock()
            mActStack.foreach(consumer)
        } finally {
            mRWLock.writeLock().unlock()
        }
    }

    /**
     * 退出应用程序
     * @param isBackground 是否开开启后台运行
     */
    @MainThread
    fun appExit(isBackground: Boolean) {
        AndroidScheduler.requireMainThread()
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
    private fun finishAll() {
        try {
            mRWLock.writeLock().lock()
            mActStack.forEach { it?.finish() }
            mActStack.clear()
        } finally {
            mRWLock.writeLock().unlock()
        }
    }
}