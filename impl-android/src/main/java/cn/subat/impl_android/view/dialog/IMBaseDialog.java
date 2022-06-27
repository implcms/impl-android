package cn.subat.impl_android.view.dialog;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;

import java.lang.reflect.Field;

import cn.subat.impl_android.utils.IMColor;
import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.view.IMConstraintLayout;

public class IMBaseDialog extends IMConstraintLayout {

    protected IMConstraintLayout container;
    protected PopupWindow window;

    public IMBaseDialog(@NonNull Context context) {
        super(context,true);
        setup();
    }

    public void setup() {
        window = new PopupWindow(this, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(window, true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        setOnClickListener(v -> hide());
        this.setBackgroundColor(IMColor.actionSheetMask);
        container = new IMConstraintLayout(getContext());
        container.setBackgroundColor(IMColor.actionSheetBackground);
        view.addViews(container);
        IMPLayout.init(view).widthMatchParent().heightMatchParent();

    }

    public IMBaseDialog show(){
        Activity activity = ActivityUtils.getTopActivity();
        if(activity != null){
            window.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER,0,0);
        }
        return this;
    }

    public void hide(){

    }

    public void dismiss(){
        window.dismiss();
    }
}
