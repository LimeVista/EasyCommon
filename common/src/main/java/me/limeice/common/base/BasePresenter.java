package me.limeice.common.base;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base Presenter
 * Created by LimeVista(Lime) on 2018/2/28.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BasePresenter<VIEW, MODEL> {

    public Context mContext;
    public MODEL mModel;
    public VIEW mView;

    /* Use this,please call {@link #getDisposable()} */
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public BasePresenter<VIEW, MODEL> set(VIEW view, MODEL model) {
        this.mView = view;
        this.mModel = model;
        return this;
    }

    public void onStart() {

    }

    public void onStop(){

    }

    public void onDestroy() {
        getDisposable().clear();
    }

    public CompositeDisposable getDisposable() {
        return mDisposable;
    }

    public abstract void onCreate();
}
