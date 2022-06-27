package cn.subat.impl_android.utils;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;

import cn.subat.impl_android.R;

public class IMColor {

    public static int text = getColor(R.color.text);
    public static int danger = getColor(R.color.danger);
    public static int primary = getColor(R.color.primary);
    public static int actionSheetMask = getColor(R.color.actionSheetMask);
    public static int actionSheetBackground = getColor(R.color.actionSheetBackground);

    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    public static int getRandColor(){
        return ColorUtils.getRandomColor();
    }

    public static int getColor(@ColorRes int... id){
        if(IMUtils.getDarkMode() && id.length>1){
            return ContextCompat.getColor(Utils.getApp(), id[1]);
        }
        return ContextCompat.getColor(Utils.getApp(), id[0]);
    }
}
