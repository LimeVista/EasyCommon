package me.limeice.common.base.mvp;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base Presenter
 * Created by LimeVista(Lime) on 2018/2/28.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BasePresenter<VIEW, MODEL> {

    protected Context mContext;
    protected MODEL mModel;
    protected VIEW mView;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public BasePresenter<VIEW, MODEL> set(VIEW view, MODEL model) {
        this.mView = view;
        this.mModel = model;
        return this;
    }

    public void onStart() {

    }

    public void onStop() {

    }

    public void onDestroy() {
        getDisposable().clear();
    }

    public abstract void onCreate();

    public CompositeDisposable getDisposable() {
        return mDisposable;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public MODEL getModel() {
        return mModel;
    }

    public VIEW getView() {
        return mView;
    }
}
