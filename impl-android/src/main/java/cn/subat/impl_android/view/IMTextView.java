package cn.subat.impl_android.view;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;

import androidx.annotation.NonNull;

import cn.subat.impl_android.utils.IMUtils;

public class IMTextView extends androidx.appcompat.widget.AppCompatTextView {

    public IMTextView(@NonNull Context context) {
        super(context);
    }

    public IMTextView(Context context, float size, int color) {
        super(context);
        this.setTextSize(size);
        this.setTextColor(color);
        this.setTypeface(IMUtils.font());
        if(IMUtils.rtl()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.setTextDirection(TEXT_DIRECTION_RTL);
                this.setLayoutDirection(LAYOUT_DIRECTION_RTL);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                this.setTextDirection(TEXT_DIRECTION_LTR);
                this.setLayoutDirection(LAYOUT_DIRECTION_LTR);
            }
        }
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }
}
