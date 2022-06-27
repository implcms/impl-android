package cn.subat.impl_android.view;

import android.content.Context;
import android.graphics.Color;

import cn.subat.impl_android.utils.IMUtils;


public class IMImageButton extends androidx.appcompat.widget.AppCompatImageButton {

    Context mContext;
    int backgroundColor;
    int resourceId;
    boolean ripple;

    public IMImageButton(Context context) {
        super(context);
        mContext = context;
        setup();
    }

    public IMImageButton(Context context, int resourceId) {
        super(context);
        mContext = context;
        this.resourceId = resourceId;
        setup();
    }

    public IMImageButton(Context context, int resourceId, int backgroundColor, boolean ripple) {
        super(context);
        this.mContext = context;
        this.backgroundColor = backgroundColor;
        this.resourceId = resourceId;
        this.ripple = ripple;
        setup();
    }

    @Override
    public void setImageResource(int resId) {
        resId = IMUtils.getThemeId(resId);
        super.setImageResource(resId);
    }

    public void setup() {
        setBackgroundColor(backgroundColor == 0?Color.TRANSPARENT:backgroundColor);
        setScaleType(ScaleType.FIT_CENTER);
        setImageResource(resourceId);
        setPadding(2);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    public void setPadding(float padding) {
        setPadding(IMUtils.dp2px(padding), IMUtils.dp2px(padding), IMUtils.dp2px(padding), IMUtils.dp2px(padding));
    }
}
