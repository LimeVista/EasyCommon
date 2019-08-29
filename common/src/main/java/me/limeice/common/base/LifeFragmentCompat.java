package me.limeice.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;

import me.limeice.common.function.Objects;
import me.limeice.common.function.algorithm.util.ArrayStack;

/**
 * 用于管理 Fragment 生命周期，本类不建议用于管理树状 Fragment, 仅建议用于代替早期的多个 Activity 改为多 Fragment
 * <p>
 * 使用方法：
 * <p>1. 复写{@link Activity#attachBaseContext(Context)},使用{@link #install(Context, AppCompatActivity)}
 * <pre>
 *     <code>
 * protected void attachBaseContext(Context base) {
 *      super.attachBaseContext(LifeFragment.install(base, this));
 * }
 *     </code>
 * </pre>
 * <p>2. 继承{@link BaseLifeFragmentCompat},或者依据其原理，自定义使用方法
 */
@SuppressWarnings("WeakerAccess")
public final class LifeFragmentCompat {

    private final ArrayStack<Fragment> mFragments = new ArrayStack<>();
    private final AppCompatActivity mActivity;

    private LifeFragmentCompat(final AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * 获得当前LifeFragment
     *
     * @param context 上下文容器
     * @return 当前LifeFragment
     */
    public static LifeFragmentCompat getLifeFragment(@NonNull Context context) {
        return InternalContextWrapper.getLifeFragment(context);
    }

    /**
     * 使用方法：复写{@link Activity#attachBaseContext(Context)},使用{@link #install(Context, AppCompatActivity)}
     * <pre>
     *     <code>
     * protected void attachBaseContext(Context base) {
     *      super.attachBaseContext(LifeFragment.install(base, this));
     * }
     *     </code>
     * </pre>
     *
     * @param baseContext 上下文
     * @param activity    Activity
     * @return newBaseContext
     */
    @SuppressWarnings("JavaDoc")
    @NonNull
    public static Context install(@NonNull final Context baseContext, @NonNull final AppCompatActivity activity) {
        return new InternalContextWrapper(baseContext, activity);
    }

    /**
     * 获得当前 Fragment
     *
     * @return Fragment
     */
    @Nullable
    public Fragment getCurrentFragment() {
        return mFragments.size() < 1 ? null : mFragments.last();
    }

    /**
     * 添加当前 Fragment
     *
     * @param fragment Fragment
     */
    public void addFragment(@NonNull final Fragment fragment) {
        removeFragment(fragment);
        mFragments.push(fragment);
    }

    /**
     * Call {@link Activity#onBackPressed}
     */
    public void goBack() {
        mActivity.onBackPressed();
    }

    /**
     * 获得当前 Activity
     *
     * @return Activity
     */
    @NonNull
    public AppCompatActivity getActivity() {
        return mActivity;
    }


    /**
     * 跳转到 Fragment
     *
     * @param layoutId Identifier of the container whose fragment(s) are to be replaced.
     * @param fragment {@link Fragment}
     * @param tag      {@link Fragment} 标识
     */
    public void replaceCommitAllowingStateLoss(@IdRes int layoutId, @NonNull Fragment fragment, @NonNull String tag) {
        Objects.checkNonNull(fragment);
        Objects.checkNonNull(tag);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(layoutId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    /**
     * 跳转到 Fragment
     *
     * @param layoutId Identifier of the container whose fragment(s) are to be replaced.
     * @param fragment {@link Fragment}
     * @param tag      {@link Fragment} 标识
     */
    public void replaceCommit(@IdRes int layoutId, @NonNull Fragment fragment, @NonNull String tag) {
        Objects.checkNonNull(fragment);
        Objects.checkNonNull(tag);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(layoutId, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    /**
     * 删除当前 Fragment
     *
     * @param fragment Fragment
     */
    public void removeFragment(@Nullable final Fragment fragment) {
        synchronized (this) {
            if (fragment == null) return;
            Iterator<Fragment> it = mFragments.iterator();
            while (it.hasNext()) {
                final Fragment f = it.next();
                if (f != fragment)
                    continue;
                it.remove();
                break;
            }
        }
    }

    static final class InternalContextWrapper extends ContextWrapper {

        private static final String FRAGMENT_SERVICE = "ME.LIME_ICE.FRAGMENT_COMPAT_SERVICE";

        private final LifeFragmentCompat life;

        InternalContextWrapper(Context baseContext, AppCompatActivity activity) {
            super(baseContext);
            life = new LifeFragmentCompat(activity);
        }

        @Nullable
        public static LifeFragmentCompat getLifeFragment(Context context) {
            @SuppressWarnings("WrongConstant")
            LifeFragmentCompat systemService = (LifeFragmentCompat) context.getSystemService(FRAGMENT_SERVICE);
            return systemService;
        }

        @Override
        public Object getSystemService(String name) {
            if (FRAGMENT_SERVICE.equals(name)) {
                return life;
            }
            return super.getSystemService(name);
        }
    }
}
