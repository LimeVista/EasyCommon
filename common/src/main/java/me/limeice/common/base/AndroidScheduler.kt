package me.limeice.common.base

import android.os.Handler
import android.os.Looper

/**
 * Android Main Thread Scheduler
 *
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2019.01.03
 *     desc  : Android Main Thread Scheduler
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
object AndroidScheduler {

    val mainHandler by lazy { Handler(Looper.getMainLooper()) }

    /**
     *  post to ui(main) thread queue
     *
     * @param run () -> Unit
     */
    inline fun postMainThread(crossinline run: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            run()
        } else {
            mainHandler.post { run() }
        }
    }

    /**
     *  get a main(ui) thread handler,if use kotlin, see [mainHandler]
     */
    fun getMainThreadHandler(): Handler {
        return mainHandler
    }

    /**
     *  post to ui(main) thread queue
     *
     * @param run Runnable
     */
    @JvmStatic
    fun postMainThread(run: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            run.run()
        } else {
            mainHandler.post(run)
        }
    }

    /**
     *  post to ui(main) thread queue
     *
     * @param run Runnable
     */
    @Deprecated("The method is deprecated.", ReplaceWith("postMainThread(Runnable)", "postMainThread"))
    @JvmStatic
    fun postUiThread(run: Runnable) {
        postMainThread(run)
    }

    /**
     *  post to ui(main) thread queue
     *
     * @param run () -> Unit
     */
    @Deprecated("The method is deprecated.", ReplaceWith("postMainThread(() -> Unit)", "postMainThread"))
    inline fun postUiThread(crossinline run: () -> Unit) {
        postMainThread(run)
    }
}