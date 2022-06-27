package cn.subat.impl_android.view;

import android.animation.ValueAnimator;
import android.content.Context;

import androidx.annotation.NonNull;

import com.wang.avi.AVLoadingIndicatorView;

import cn.subat.impl_android.utils.IMColor;
import cn.subat.impl_android.utils.IMConstant;
import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.utils.IMUtils;

public class IMLoadMoreView extends IMConstraintLayout {

    AVLoadingIndicatorView loadingIndicatorView;
    public IMConstant.LoadState state = IMConstant.LoadState.Normal;

    public IMLoadMoreView(@NonNull Context context) {
        super(context);
        setup();
    }

    @Override
    public void setup() {
        super.setup();
        addContainer();
        loadingIndicatorView = new AVLoadingIndicatorView(getContext());
        loadingIndicatorView.setIndicator("BallScaleIndicator");
        loadingIndicatorView.setIndicatorColor(IMColor.primary);
        loadingIndicatorView.hide();
        view.addViews(loadingIndicatorView);
        IMPLayout.init(this).widthMatchParent().heightWrapContent();
        IMPLayout.init(loadingIndicatorView).center(view).width(40).height(40).margin(10);
    }

    public void setState(IMConstant.LoadState state) {
        this.state = state;
        if(state == IMConstant.LoadState.Loading){
            IMPLayout.init(view).widthMatchParent().height(60);
            loadingIndicatorView.smoothToShow();
        }else{
            smoothScrollTo(0);
            loadingIndicatorView.smoothToHide();
        }
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
