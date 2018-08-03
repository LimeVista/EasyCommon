package me.limeice.common.function;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具
 */
public final class TransferAnimUtils {

    /**
     * 显示 View (View.VISIBLE)
     */
    public static final short ANIM_SHOW = 0x01;

    /**
     * 隐藏 View (View.INVISIBLE)
     */
    public static final short ANIM_HIDE = 0x02;

    /**
     * 隐藏 View (View.GONE)
     */
    public static final short ANIM_GONE = 0x03;

    /**
     * 从屏幕底部上升动画，动画不产生{@link View}真实位移
     *
     * @param v        View
     * @param type     动画类型效果，{@link #ANIM_GONE}, {@link #ANIM_HIDE},{@link #ANIM_SHOW}
     * @param duration 动画持续时间，毫秒
     */
    public static void bottomPopupAnim(@NonNull View v, short type, int duration) {
        bottomPopupAnim(v, type, duration, null);
    }

    /**
     * 从屏幕底部上升动画，动画不产生{@link View}真实位移
     *
     * @param v        View
     * @param type     动画类型效果，{@link #ANIM_GONE}, {@link #ANIM_HIDE},{@link #ANIM_SHOW}
     * @param duration 动画持续时间，毫秒
     * @param taskEnd  动画结束回调
     */
    public static void bottomPopupAnim(@NonNull View v, short type, int duration, @Nullable Runnable taskEnd) {
        TranslateAnimation anim;
        int value;
        switch (type) {
            case ANIM_HIDE:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                anim.setInterpolator(new AccelerateInterpolator());
                value = View.INVISIBLE;
                break;
            case ANIM_SHOW:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                anim.setInterpolator(new DecelerateInterpolator());
                value = View.VISIBLE;
                v.setVisibility(value);
                break;
            case ANIM_GONE:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                anim.setInterpolator(new DecelerateInterpolator());
                value = View.GONE;
                break;
            default:
                throw new IllegalArgumentException("传入类型有误");
        }
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!(type == ANIM_SHOW)) v.setVisibility(value);
                if (taskEnd != null) taskEnd.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }

    /**
     * 从右向左动画，动画不产生{@link View}真实位移
     *
     * @param v        View
     * @param type     动画类型效果，{@link #ANIM_GONE}, {@link #ANIM_HIDE},{@link #ANIM_SHOW}
     * @param duration 动画持续时间，毫秒
     */
    public static void rtlPopupAnim(@NonNull View v, short type, int duration) {
        rtlPopupAnim(v, type, duration, null);
    }

    /**
     * 从右向左动画，动画不产生{@link View}真实位移
     *
     * @param v        View
     * @param type     动画类型效果，{@link #ANIM_GONE}, {@link #ANIM_HIDE},{@link #ANIM_SHOW}
     * @param duration 动画持续时间，毫秒
     * @param taskEnd  动画结束回调
     */
    public static void rtlPopupAnim(@NonNull View v, short type, int duration, @Nullable Runnable taskEnd) {
        TranslateAnimation anim;
        int value;
        switch (type) {
            case ANIM_HIDE:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                anim.setInterpolator(new AccelerateInterpolator());
                value = View.INVISIBLE;
                break;
            case ANIM_SHOW:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                anim.setInterpolator(new DecelerateInterpolator());
                value = View.VISIBLE;
                v.setVisibility(value);
                break;
            case ANIM_GONE:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                anim.setInterpolator(new DecelerateInterpolator());
                value = View.GONE;
                break;
            default:
                throw new IllegalArgumentException("传入类型有误");
        }
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!(type == ANIM_SHOW)) v.setVisibility(value);
                if (taskEnd != null) taskEnd.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }

    /**
     * 从左向右动画，动画不产生{@link View}真实位移
     *
     * @param v        View
     * @param type     动画类型效果，{@link #ANIM_GONE}, {@link #ANIM_HIDE},{@link #ANIM_SHOW}
     * @param duration 动画持续时间，毫秒
     */
    public static void ltrPopupAnim(@NonNull View v, short type, int duration) {
        ltrPopupAnim(v, type, duration, null);
    }

    /**
     * 从左向右动画，动画不产生{@link View}真实位移
     *
     * @param v        View
     * @param type     动画类型效果，{@link #ANIM_GONE}, {@link #ANIM_HIDE},{@link #ANIM_SHOW}
     * @param duration 动画持续时间，毫秒
     * @param taskEnd  动画结束回调
     */
    public static void ltrPopupAnim(@NonNull View v, short type, int duration, @Nullable Runnable taskEnd) {
        TranslateAnimation anim;
        int value;
        switch (type) {
            case ANIM_HIDE:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                anim.setInterpolator(new AccelerateInterpolator());
                value = View.INVISIBLE;
                break;
            case ANIM_SHOW:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                anim.setInterpolator(new DecelerateInterpolator());
                value = View.VISIBLE;
                v.setVisibility(value);
                break;
            case ANIM_GONE:
                anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                anim.setInterpolator(new DecelerateInterpolator());
                value = View.GONE;
                break;
            default:
                throw new IllegalArgumentException("Type error.");
        }
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!(type == ANIM_SHOW)) v.setVisibility(value);
                if (taskEnd != null) taskEnd.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);
    }
}
