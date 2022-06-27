package cn.subat.impl_android.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.wang.avi.AVLoadingIndicatorView;

import cn.subat.impl_android.utils.IMColor;
import cn.subat.impl_android.utils.IMConstant;
import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.utils.IMUtils;

public class IMRefreshView extends IMConstraintLayout {

    int mMeasuredHeight = IMUtils.dp2px(60);
    public IMConstant.LoadState state;
    AVLoadingIndicatorView loadingIndicatorView;

    public IMRefreshView(@NonNull Context context) {
        super(context);
        setup();
    }

    @Override
    public void setup() {
        super.setup();
        IMPLayout.init(this).widthMatchParent().heightWrapContent();
        addContainer();
        loadingIndicatorView = new AVLoadingIndicatorView(getContext());
        loadingIndicatorView.setIndicator("BallScaleIndicator");
        loadingIndicatorView.setIndicatorColor(IMColor.primary);
        loadingIndicatorView.hide();
        view.addViews(loadingIndicatorView);
        IMPLayout.init(loadingIndicatorView).center(view).width(40).height(40).margin(10);
    }

    public void setLoadState(IMConstant.LoadState loadState){
        state = loadState;
        if(state == IMConstant.LoadState.Normal){
            smoothScrollTo(0);
            loadingIndicatorView.hide();
        }
    }

    public void onMove(float delta) {
        IMPLayout.init(view).widthMatchParent().height(IMUtils.px2dp(view.getHeight()+delta));
    }

    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = view.getHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if (height > mMeasuredHeight && state != IMConstant.LoadState.Loading) {
            state = IMConstant.LoadState.Loading;
            loadingIndicatorView.smoothToShow();
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (state == IMConstant.LoadState.Loading && height <= mMeasuredHeight) {
            //return;
        }

        if (height <= mMeasuredHeight) {
            smoothScrollTo(0);
        }

        if (state == IMConstant.LoadState.Loading) {
            int destHeight = mMeasuredHeight;
            smoothScrollTo(destHeight);
        }

        return isOnRefresh;
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(view.getHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                IMPLayout.init(view).widthMatchParent().height(IMUtils.px2dp((int) animation.getAnimatedValue()));
            }
        });
        animator.start();
    }
}
