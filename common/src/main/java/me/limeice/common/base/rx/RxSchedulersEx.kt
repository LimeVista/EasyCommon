@file:JvmName("RxSchedulersEx")
@file:Suppress("NOTHING_TO_INLINE")

package me.limeice.common.base.rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


/**
 * RxJava线程调度
 * Created by LimeV on 2018/2/28.
 */

/* Io线程切换到主线程 */
inline fun <T> Observable<T>.ioToMain() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


/* IO线程切换到主线程 */
inline fun <T> Observable<T>.ioToMainDelayError() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread(), true)


/* 计算线程切换到主线程 */
inline fun <T> Observable<T>.computationToMain() =
    subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())


/* 主线程切换到IO线程 */
inline fun <T> Observable<T>.mainToIO() =
    subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())


/* 主线程切换到计算线程 */
inline fun <T> Observable<T>.mainToComputation() =
    subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.computation())

