package me.limeice.common.base

import android.os.Handler
import android.os.Looper

object AndroidScheduler {

    val mainHandler by lazy { Handler(Looper.getMainLooper()) }

    /**
     *  post to ui(main) thread queue
     *
     * @param run () -> Unit
     */
    inline fun postUiThread(crossinline run: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            run()
        } else {
            mainHandler.post { run() }
        }
    }

    /**
     *  post to ui(main) thread queue
     *
     * @param run Runnable
     */
    @JvmStatic
    fun postUiThread(run: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            run.run()
        } else {
            mainHandler.post(run)
        }
    }
}