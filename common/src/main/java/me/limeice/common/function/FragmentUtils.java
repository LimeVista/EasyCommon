package me.limeice.common.function;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.limeice.common.R;
import me.limeice.common.base.EasyCommon;

/**
 * Fragment 处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2019/03/25
 *     desc  : Fragment 工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class FragmentUtils {

    private FragmentUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * install bar to activity, the fragment parent must be {@link AppCompatActivity}
     *
     * @param fragment {@link Fragment}
     * @param bar      {@link Toolbar}
     */
    public static void installToolbar(@NonNull Fragment fragment, @NonNull Toolbar bar) {
        installAndGetToolbar(fragment, bar);
    }

    /**
     * install bar to activity, the fragment parent must be {@link AppCompatActivity}
     *
     * @param fragment {@link Fragment}
     * @param bar      {@link Toolbar}
     * @param title    Title, if {@code null}, set title {@code ""}
     */
    public static void installToolbar(@NonNull final Fragment fragment,
                                      @NonNull final Toolbar bar,
                                      @Nullable String title) {
        AppCompatActivity act = installAndGetToolbar(fragment, bar);
        final ActionBar actionBar;
        if (act == null || (actionBar = act.getSupportActionBar()) == null)
            return;
        actionBar.setTitle(title == null ? "" : title);
    }

    /**
     * install bar to activity, the fragment parent must be {@link AppCompatActivity}
     *
     * @param fragment {@link Fragment}
     * @param bar      {@link Toolbar}
     * @param title    Title
     */
    public static void installToolbar(@NonNull final Fragment fragment,
                                      @NonNull final Toolbar bar,
                                      @StringRes int title) {
        installToolbar(fragment, bar, EasyCommon.getApp().getString(title));
    }

    /**
     * install bar to activity, the fragment parent must be {@link AppCompatActivity}
     *
     * @param fragment       {@link Fragment}
     * @param bar            {@link Toolbar}
     * @param showHomeButton show home button, if {@code false} eq {@link FragmentUtils#installToolbar(Fragment, Toolbar)}
     * @param buttonColor    button icon tint color
     */
    public static void installToolbar(@NonNull final Fragment fragment,
                                      @NonNull final Toolbar bar,
                                      boolean showHomeButton,
                                      @ColorInt int buttonColor) {
        AppCompatActivity act = installAndGetToolbar(fragment, bar);
        final ActionBar actionBar;
        if (act == null || (!showHomeButton) || (actionBar = act.getSupportActionBar()) == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = tintDrawable(act, buttonColor);
        if (upArrow != null) actionBar.setHomeAsUpIndicator(upArrow);
    }

    /**
     * install bar to activity, the fragment parent must be {@link AppCompatActivity}
     *
     * @param fragment {@link Fragment}
     * @param bar      {@link Toolbar}
     * @param drawable home button drawable, {@link ActionBar#setHomeAsUpIndicator(Drawable)}
     */
    public static void installToolbarCustomHomeButton(@NonNull final Fragment fragment,
                                                      @NonNull final Toolbar bar,
                                                      @Nullable Drawable drawable) {
        AppCompatActivity act = installAndGetToolbar(fragment, bar);
        final ActionBar actionBar;
        if (act == null || (actionBar = act.getSupportActionBar()) == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(drawable);
    }

    /**
     * install bar to activity, the fragment parent must be {@link AppCompatActivity}
     *
     * @param fragment {@link Fragment}
     * @param bar      {@link Toolbar}
     * @param drawable home button drawable, {@link ActionBar#setHomeAsUpIndicator(int)}
     */
    public static void installToolbarCustomHomeButton(@NonNull final Fragment fragment,
                                                      @NonNull final Toolbar bar,
                                                      @DrawableRes int drawable) {
        AppCompatActivity act = installAndGetToolbar(fragment, bar);
        final ActionBar actionBar;
        if (act == null || (actionBar = act.getSupportActionBar()) == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(drawable);
    }

    /**
     * install bar to activity, the fragment parent must be {@link AppCompatActivity}
     *
     * @param fragment        {@link Fragment}
     * @param bar             {@link Toolbar}
     * @param homeButtonColor button icon tint color
     * @param title           Title, if {@code null}, set title {@code ""}
     */
    public static void installToolbar(@NonNull final Fragment fragment,
                                      @NonNull final Toolbar bar,
                                      @ColorInt int homeButtonColor,
                                      @Nullable String title) {
        AppCompatActivity act = installAndGetToolbar(fragment, bar);
        final ActionBar actionBar;
        if (act == null || (actionBar = act.getSupportActionBar()) == null)
            return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title == null ? "" : title);
        final Drawable upArrow = tintDrawable(act, homeButtonColor);
        if (upArrow != null) actionBar.setHomeAsUpIndicator(upArrow);
    }

    /**
     * install bar to activity, the fragment parent must be {@link AppCompatActivity}
     *
     * @param fragment        {@link Fragment}
     * @param bar             {@link Toolbar}
     * @param homeButtonColor button icon tint color
     * @param title           Title, if {@code null}, set title {@code ""}
     */
    public static void installToolbar(@NonNull final Fragment fragment,
                                      @NonNull final Toolbar bar,
                                      @ColorInt int homeButtonColor,
                                      @StringRes int title) {
        installToolbar(fragment, bar, homeButtonColor, EasyCommon.getApp().getString(title));
    }

    /**
     * uninstall toolbar from activity
     *
     * @param fragment {@link Fragment}
     */
    public static void uninstallToolbar(@NonNull final Fragment fragment) {
        final Activity act = Objects.requireNonNull(fragment).getActivity();
        if (act instanceof AppCompatActivity) {
            ((AppCompatActivity) act).setSupportActionBar(null);
        }
    }

    /**
     * set title
     *
     * @param fragment {@link Fragment}
     * @param title    Title
     */
    public static void setToolbarTitle(@NonNull Fragment fragment, @StringRes int title) {
        final Activity act = Objects.requireNonNull(fragment).getActivity();
        if (!(act instanceof AppCompatActivity) || act.isFinishing())
            return;
        ActionBar bar = ((AppCompatActivity) act).getSupportActionBar();
        if (bar != null) bar.setTitle(title);
    }

    /**
     * set title
     *
     * @param fragment {@link Fragment}
     * @param title    Title
     */
    public static void setToolbarTitle(@NonNull Fragment fragment, String title) {
        final Activity act = Objects.requireNonNull(fragment).getActivity();
        if (!(act instanceof AppCompatActivity) || act.isFinishing())
            return;
        ActionBar bar = ((AppCompatActivity) act).getSupportActionBar();
        if (bar != null) bar.setTitle(title);
    }

    private static AppCompatActivity installAndGetToolbar(
            @NonNull final Fragment fragment, final @NonNull Toolbar bar) {
        Objects.checkNonNull(bar);
        final Activity act = Objects.requireNonNull(fragment).getActivity();
        if (!(act instanceof AppCompatActivity) || act.isFinishing())
            return null;
        ((AppCompatActivity) act).setSupportActionBar(bar);
        return (AppCompatActivity) act;
    }

    private static Drawable tintDrawable(final Context context, final int color) {
        @SuppressLint("PrivateResource")
        Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material);
        if (upArrow == null)
            return null;
        upArrow = upArrow.mutate();
        upArrow.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        return upArrow;
    }
}