package me.limeice.common.function;

import android.content.Context;
import android.support.annotation.NonNull;

@SuppressWarnings("FinalStaticMethod")
final class QuickBridge {

    static final float getDensity(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

}
