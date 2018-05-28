package me.limeice.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.Iterator;

import me.limeice.common.function.algorithm.util.ArrayStack;

/**
 * 用于管理Fragment生命周期
 * 使用方法：
 * <p>1. 复写{@link Activity#attachBaseContext(Context)},使用{@link #install(Context, Activity)}
 * <pre>
 *     <code>
 * protected void attachBaseContext(Context base) {
 *      super.attachBaseContext(LifeFragment.install(base, this));
 * }
 *     </code>
 * </pre>
 * <p>2. 继承{@link BaseLifeFragmentV4},或者依据其原理，自定义使用方法
 */
public final class LifeFragmentV4 {

    private final ArrayStack<Fragment> mFragments = new ArrayStack<>();
    private final Activity mActivity;

    private LifeFragmentV4(final Activity activity) {
        mActivity = activity;
    }

    /**
     * 获得当前LifeFragment
     *
     * @param context 上下文容器
     * @return 当前LifeFragment
     */
    public static LifeFragmentV4 getLifeFragment(@NonNull Context context) {
        return InternalContextWrapper.getLifeFragment(context);
    }

    /**
     * 使用方法：复写{@link Activity#attachBaseContext(Context)},使用{@link #install(Context, Activity)}
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
    @NonNull
    public static Context install(@NonNull final Context baseContext, @NonNull final Activity activity) {
        return new InternalContextWrapper(baseContext.getApplicationContext(), activity);
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
    public Activity getActivity() {
        return mActivity;
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
                Fragment f = it.next();
                if (f != fragment)
                    continue;
                it.remove();
                break;
            }
        }
    }

    static final class InternalContextWrapper extends ContextWrapper {

        private static final String FRAGMENT_SERVICE = "ME.LIME_ICE.FRAGMENT_SERVICE";

        private final LifeFragmentV4 life;

        InternalContextWrapper(Context baseContext, Activity activity) {
            super(baseContext);
            life = new LifeFragmentV4(activity);
        }

        @Nullable
        public static LifeFragmentV4 getLifeFragment(Context context) {
            @SuppressWarnings("WrongConstant")
            LifeFragmentV4 systemService = (LifeFragmentV4) context.getSystemService(FRAGMENT_SERVICE);
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
