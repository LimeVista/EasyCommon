package me.limeice.common.base.rx;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava线程调度
 * Created by LimeV on 2018/2/28.
 */
public class RxSchedulers {

    public static <T> ObservableTransformer<T, T> ioToMain() {
        return in -> in.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> computationToMain() {
        return in -> in.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
