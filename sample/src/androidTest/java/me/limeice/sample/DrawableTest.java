package me.limeice.sample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import me.limeice.common.function.DrawableUtils;


@RunWith(AndroidJUnit4.class)
public class DrawableTest {

    @Test
    public void testDrawable() {
        Drawable d = new ColorDrawable(Color.WHITE);
        DrawableUtils.tintDrawable(d, Color.WHITE);
    }
}
