package cn.subat.impl_android.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;


import java.util.concurrent.atomic.AtomicBoolean;

import cn.subat.impl_android.view.IMConstraintLayout;

public class IMAnimate {

    private View mainView;
    private Animator.AnimatorListener animatorListener;
    private ValueAnimator animator;
    private AtomicBoolean animateState;
    private AnimationListener animationListener;

    public static IMAnimate init(View view){
        IMAnimate animate = new IMAnimate();
        animate.mainView = view;
        return animate;
    }

    public IMAnimate width(float endWidth){
        animator = ValueAnimator.ofInt(mainView.getMeasuredWidth(), IMUtils.dp2px(endWidth));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int)animation.getAnimatedValue();
                if(val != 0){
                    IMPLayout.update(mainView).widthPx(val);
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(animationListener != null){
                    animationListener.on(IMConstant.SPAnimateState.Start);
                    if(mainView instanceof IMConstraintLayout){
                        ((IMConstraintLayout)mainView).animating = true;
                    }
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(animationListener != null){
                    animationListener.on(IMConstant.SPAnimateState.Complete);
                }
                if(mainView instanceof IMConstraintLayout){
                    ((IMConstraintLayout)mainView).animating = false;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return this;
    }


    public IMAnimate padding(float endValue,String dir){
        int start = 0;
        if(dir.equals("top")){
            start = mainView.getPaddingTop();
        }
        animator = ValueAnimator.ofInt(start, IMUtils.dp2px(endValue));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int)animation.getAnimatedValue();
                if(dir.equals("top")){
                    mainView.setPadding(mainView.getPaddingLeft(),val,mainView.getPaddingRight(),mainView.getPaddingBottom());
                }
            }
        });
        return this;
    }

    public IMAnimate height(float endHeight){
        animator = ValueAnimator.ofInt(mainView.getMeasuredHeight(), (int)endHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int)animation.getAnimatedValue();
                if(val != 0){
                    IMPLayout.update(mainView).heightPx(val);
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(animationListener != null){
                    animationListener.on(IMConstant.SPAnimateState.Start);
                    if(mainView instanceof IMConstraintLayout){
                        ((IMConstraintLayout)mainView).animating = true;
                    }
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(animationListener != null){
                    animationListener.on(IMConstant.SPAnimateState.Complete);
                }
                if(mainView instanceof IMConstraintLayout){
                    ((IMConstraintLayout)mainView).animating = false;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return this;
    }

    public IMAnimate on(AnimationListener listener){
        this.animationListener = listener;
        return this;
    }

    public void run(long duration){
        animator.setDuration(duration);
        animator.start();
    }

    public IMAnimate hide(long duration){
        animator = ValueAnimator.ofFloat(1,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float)animation.getAnimatedValue();
                mainView.setAlpha(val);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(animationListener != null){
                    animationListener.on(IMConstant.SPAnimateState.Start);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mainView.setVisibility(View.INVISIBLE);
                if(animationListener != null){
                    animationListener.on(IMConstant.SPAnimateState.Complete);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(duration);
        animator.start();
        return this;
    }
    public IMAnimate show(long duration){
        if(mainView.getVisibility() == View.VISIBLE) return this;
        mainView.setVisibility(View.VISIBLE);
        animator = ValueAnimator.ofFloat(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float)animation.getAnimatedValue();
                mainView.setAlpha(val);
            }
        });
        animator.setDuration(duration);
        animator.start();
        return this;
    }

    public void scale(float scale,long duration){
        Animation anim = new ScaleAnimation(
                mainView.getScaleX(), scale, // Start and end values for the X axis scaling
                mainView.getScaleY(), scale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true);
        anim.setDuration(duration);
        mainView.startAnimation(anim);
    }
    public void scale(float start,float end,long duration){
        Animation anim = new ScaleAnimation(
                start, end, // Start and end values for the X axis scaling
                start, end, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true);
        anim.setDuration(duration);
        mainView.startAnimation(anim);
    }

    public void rotate(long duration,float fromDegrees, float toDegrees){
        rotate(duration,fromDegrees,toDegrees,1);
    }
    public void rotate(long duration,float fromDegrees, float toDegrees,int repeat){
        AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);
        final RotateAnimation animRotate = new RotateAnimation(fromDegrees, toDegrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animRotate.setDuration(duration);
        animRotate.setFillAfter(true);
        animRotate.setRepeatCount(repeat);
        animSet.addAnimation(animRotate);
        mainView.startAnimation(animSet);
    }

    public interface AnimationListener{
        void on(IMConstant.SPAnimateState state);
    }

    public IMAnimate move(float distance,String name){
        animator = ObjectAnimator.ofFloat(mainView, name, IMUtils.dp2px(distance));
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(animationListener != null){
                    animationListener.on(IMConstant.SPAnimateState.Start);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(animationListener != null){
                    animationListener.on(IMConstant.SPAnimateState.Complete);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return this;
    }

    public IMAnimate moveVertically(float distance){
        return move(distance,"translationY");
    }
    public IMAnimate moveHorizontally(float distance){
        if(!IMUtils.rtl()){
            distance = - distance;
        }
        return move(distance,"translationX");
    }

}
