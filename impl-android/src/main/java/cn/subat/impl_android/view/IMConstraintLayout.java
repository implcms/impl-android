package cn.subat.impl_android.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.mmin18.widget.RealtimeBlurView;

import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.utils.IMUtils;

public class IMConstraintLayout extends ConstraintLayout {

    public boolean animating;
    public IMConstraintLayout view;
    public View borderLine;
    public RealtimeBlurView realtimeBlurView;


    public IMConstraintLayout(@NonNull Context context, boolean widthContainer) {
        super(context);
        if(widthContainer){
            view = new IMConstraintLayout(context);
            addViews(view);
            IMPLayout.init(view).widthMatchParent().heightMatchParent();
        }
        init(context,null);
    }

    public IMConstraintLayout(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public IMConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs){
        if(IMUtils.rtl()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.setLayoutDirection(LAYOUT_DIRECTION_LTR);
            }
        }
        this.setBackgroundColor(Color.TRANSPARENT);
        setFocusable(false);
    }

    public void setup(){

    }

    public void addContainer(){
        view = new IMConstraintLayout(getContext());
        int id;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            id = View.generateViewId();
        }else{
            id = (int)(Math.random() * 1000);
        }
        view.setId(id);
        addView(view,0);
        IMPLayout.init(view).widthMatchParent().heightMatchParent();
    }

    public void addViews(View... views){
        for (int i = 0; i < views.length; i++){
            int id;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                id = View.generateViewId();
            }else{
                id = (int)(Math.random() * 1000);
            }
            if(this.findViewById(id) != null){
                id = id+i;
            }
            views[i].setId(id);
            this.addView(views[i]);
        }
    }

    @Override
    public void addView(View child, int index) {
        int id;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            id = View.generateViewId();
        }else{
            id = (int)(Math.random() * 1000);
        }
        if(this.findViewById(id) != null){
            id = id+index;
        }
        child.setId(id);
        super.addView(child, index);
    }

    //todo 圆角
    public void setRadius(float radius){
        //mLayoutHelper.setRadius(IMUtils.dp2px(radius));
    }

    public void addGradient(){
        GradientDrawable gradientDrawable= new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[]{Color.TRANSPARENT,Color.BLACK});
        setBackgroundColor(Color.TRANSPARENT);
        setBackground(gradientDrawable);
    }

    public void addBlurBackground(int overlyColor,int radius,int zIndex){
        realtimeBlurView = new RealtimeBlurView(getContext(),null);
        realtimeBlurView.setBlurRadius(radius);
        realtimeBlurView.setOverlayColor(overlyColor);
        addView(realtimeBlurView,zIndex);
        IMPLayout.init(realtimeBlurView).widthMatchParent().heightMatchParent();
    }

    public void addBlurBackground(int overlyColor){
        addBlurBackground(overlyColor,50,-1);
    }

    //todo 阴影
    public void setShadowElevation(int elevation){

    }


}
