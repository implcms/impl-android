package cn.subat.impl_android.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.subat.impl_android.utils.IMPLayout;

public class IMBaseItem<T> extends IMConstraintLayout {

    public IMRecyclerView.Listener listener;

    public IMBaseItem(@NonNull Context context) {
        super(context);
    }

    public IMBaseItem(@NonNull Context context, boolean withContainer) {
        super(context,withContainer);
    }

    public IMBaseItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        setupView();
    }

    public void setupView(){
        IMPLayout.init(this).widthMatchParent().heightWrapContent();
        addContainer();
    }

    public void hideRipple(){

    }

    public void setData(T data){

    }

    public void addRipple(){

    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        onFocus(gainFocus);
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    public void onFocus(boolean gainFocus){

    }
}
