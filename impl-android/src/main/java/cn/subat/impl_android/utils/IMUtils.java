package cn.subat.impl_android.utils;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;

import java.util.Locale;

import cn.subat.impl_android.activity.IMBaseActivity;

public class IMUtils {

    public static Context getContext(){
        if(ActivityUtils.getTopActivity() != null){
            return ActivityUtils.getTopActivity();
        }else if(Utils.getApp() != null){
            return Utils.getApp().getApplicationContext();
        }else{
            return null;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return SizeUtils.dp2px(dpValue);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dp(float pxValue) {
        return SizeUtils.px2dp(pxValue);
    }

    /**
     * 字体大小转换
     * @param sp
     * @return
     */
    public static int sp2px(float sp){
        LogUtils.w(sp,Resources.getSystem().getDisplayMetrics().scaledDensity,Resources.getSystem().getDisplayMetrics().densityDpi);
        return (int) sp;
    }

    /**
     * 是否从右到左
     */
    public static boolean rtl(){
        switch (SPUtils.getInstance().getInt("lang")){
            case 0:
                return true;
            default:
                return false;
        }
    }

    /**
     * 深色模式
     */

    public static boolean getDarkMode(){
        return SPUtils.getInstance().getBoolean("dark_mode");
    }

    /**
     * 全透状态栏
     */
    public static void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = ActivityUtils.getTopActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            ActivityUtils.getTopActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 更新语言包
     * @param activity
     */
    public static void updateLocal(Context activity,boolean recreate){
        if(getContext() == null) return;
        int lang = SPUtils.getInstance().getInt("lang");
        Locale local = getLocal(lang);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        Configuration configuration = activity.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(local);
        }
        activity.getResources().updateConfiguration(configuration, metrics);
        if(recreate){
            BusUtils.post("updateLocal");
        }

    }

    public static Locale getLocal(int index){
        switch (index){
            case 0:{
                return new Locale("ug", Locale.CHINA.getCountry());
            }
            case 1:{
                return Locale.SIMPLIFIED_CHINESE;
            }
            default:{
                return Locale.ENGLISH;
            }
        }
    }

    public static Typeface font(){
        switch (SPUtils.getInstance().getInt("lang")){
            case 0:
                return IMFont.getTypeface();
            default:
                return Typeface.DEFAULT;
        }
    }

    public static void lightStatusBar(){
        BarUtils.setStatusBarLightMode(ActivityUtils.getTopActivity(),true);
    }

    public static void darkStatusBar(){
        BarUtils.setStatusBarLightMode(ActivityUtils.getTopActivity(),false);
    }

    public static int getThemeId(int resId){
        if(resId > 0 && getContext() != null && getDarkMode()){
            String name = getContext().getResources().getResourceName(resId);
            int darkRes = getContext().getResources().getIdentifier(name+"_night",null,null);
            if(darkRes > 0){
                resId = darkRes;
            }
        }
        return resId;
    }

    public static String getString(int id){
        return getContext().getResources().getString(id);
    }

    public static float getScreenPxWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenPxHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
