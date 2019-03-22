package me.limeice.common.function;

import android.Manifest;
import android.content.ContentResolver;
import android.provider.Settings;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import android.view.Window;
import android.view.WindowManager;

import me.limeice.common.base.EasyCommon;

/**
 * 亮度处理工具
 * <pre>
 *     author: LimeVista(Lime)
 *     time  : 2019/02/24
 *     desc  : 屏幕亮度处理工具类
 *     github: https://github.com/LimeVista/EasyCommon
 * </pre>
 */
public final class BrightnessUtils {

    private BrightnessUtils() {
        throw new UnsupportedOperationException("Don't instantiate...");
    }

    /**
     * 获取窗口亮度
     *
     * @param window {@link Window}
     * @return 屏幕亮度 [0, 255]
     */
    public static int getWindowBrightness(
            final Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        float brightness = lp.screenBrightness;
        return brightness < 0 ? getBrightness() : (int) (brightness * 255);
    }

    /**
     * 设置窗口亮度
     *
     * @param window     {@link Window}
     * @param brightness 亮度值 [0, 255]
     */
    public static void setWindowBrightness(
            @NonNull final Window window,
            @IntRange(from = 0, to = 255) final int brightness) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255f;
        window.setAttributes(lp);
    }

    /**
     * 是否开启自动亮度
     *
     * @return {@code true}: auto
     */
    public static boolean isAutoBrightness() {
        final ContentResolver resolver = EasyCommon.getApp().getContentResolver();
        try {
            return Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 打开自动亮度设置
     *
     * @param enabled {@code true}: enable
     * @return {@code true}: successful
     */
    @RequiresPermission(Manifest.permission.WRITE_SETTINGS)
    public static boolean setAutoBrightness(final boolean enabled) {
        final ContentResolver resolver = EasyCommon.getApp().getContentResolver();
        return Settings.System.putInt(
                resolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                enabled ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                        : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        );
    }

    /**
     * 获取屏幕亮度
     *
     * @return 屏幕亮度 [0, 255]
     */
    public static int getBrightness() {
        final ContentResolver resolver = EasyCommon.getApp().getContentResolver();
        try {
            return Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 设置屏幕亮度
     *
     * @param brightness 亮度值 [0, 255]
     */
    @RequiresPermission(Manifest.permission.WRITE_SETTINGS)
    public static boolean setBrightness(@IntRange(from = 0, to = 255) final int brightness) {
        final ContentResolver resolver = EasyCommon.getApp().getContentResolver();
        boolean result = Settings.System.putInt(resolver,
                Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null); // notify
        return result;
    }
}