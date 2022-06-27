package cn.subat.impl_android.view;

import android.content.Context;
import android.os.Build;


public class IMImageView extends androidx.appcompat.widget.AppCompatImageView {


    public IMImageView(Context context) {
        super(context);
        setScaleType(ScaleType.FIT_XY);
        init(context);
    }

    public IMImageView(Context context, boolean showRipple) {
        super(context);
        setScaleType(ScaleType.FIT_XY);
        if(showRipple){
            showRipple();
        }
        init(context);
    }

    public void init(Context context){

    }
    public void showRipple(){

    }

    //todo radius
    public void setRadius(float radius){

    }

}
