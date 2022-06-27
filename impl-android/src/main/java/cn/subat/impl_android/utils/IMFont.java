package cn.subat.impl_android.utils;

import android.content.Context;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Type;
import java.util.HashMap;

import cn.subat.impl_android.R;

public class IMFont {
    private static final HashMap<String, Typeface> fontCache = new HashMap<>();
    public static Typeface getTypeface() {
        Context context = IMUtils.getContext();
        if(context != null){
            Typeface typeface = fontCache.get("impl_main");
            if (typeface == null) {
                try {
                    typeface = ResourcesCompat.getFont(IMUtils.getContext(), R.font.impl_font);
                } catch (Exception e) {
                    return null;
                }
                fontCache.put("impl_main", typeface);
            }
            return typeface;
        }
        return Typeface.DEFAULT;
    }
}
