@file:JvmName("RxSchedulersEx")
@file:Suppress("NOTHING_TO_INLINE")

package me.limeice.common.base.rx

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * RxJava线程调度
 * Created by LimeV on 2018/2/28.
 */
inline fun <T> Observable<T>.ioToMain() =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

inline fun <T> Observable<T>.computationToMain() =
        subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
