package me.limeice.common.base.rx;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * RxJava线程调度
 * Created by LimeV on 2018/2/28.
 * 实例：<code>
 * Observable.just(1).compose(RxSchedulers.ioToMain()).subscribe();
 * </code>
 */
public class RxSchedulers {

    /**
     * Io线程切换到主线程
     *
     * @param <T> 泛型约束
     * @return RxJava流
     */
    public static <T> ObservableTransformer<T, T> ioToMain() {
        return in -> in.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 计算线程切换到主线程
     *
     * @param <T> 泛型约束
     * @return RxJava流
     */
    public static <T> ObservableTransformer<T, T> computationToMain() {
        return in -> in.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 主线程切换到IO线程
     *
     * @param <T> 泛型约束
     * @return RxJava流
     */
    public static <T> ObservableTransformer<T, T> mainToIo() {
        return in -> in.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io());
    }

    /**
     * 主线程切换到计算线程
     *
     * @param <T> 泛型约束
     * @return RxJava流
     */
    public static <T> ObservableTransformer<T, T> mainToComputation() {
        return in -> in.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.computation());
    }

    /**
     * Io线程切换到主线程,延迟错误相应
     *
     * @param <T> 泛型约束
     * @return RxJava流
     */
    public static <T> ObservableTransformer<T, T> ioToMainDelayError() {
        return in -> in.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true);
    }
}
