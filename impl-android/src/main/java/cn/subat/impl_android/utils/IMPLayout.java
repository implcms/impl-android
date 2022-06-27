package cn.subat.impl_android.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.blankj.utilcode.util.ColorUtils;

import cn.subat.impl_android.view.IMConstraintLayout;


public class IMPLayout {

    private ViewGroup.MarginLayoutParams lp;
    private View mainView;
    private ConstraintSet set;
    private boolean rtlOnly;

    public static IMPLayout init(View view){
        return new IMPLayout(view);
    }
    public static IMPLayout update(View view){
        return new IMPLayout(view,true);
    }

    public IMPLayout(View view, boolean isUpdate){
        lp = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.setLayoutParams(lp);
        set = new ConstraintSet();
        mainView = view;
    }
    public IMPLayout(View view){
        if(view.getLayoutParams() != null){
            lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        }else{
            lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        view.setLayoutParams(lp);
        set = new ConstraintSet();
        mainView = view;
    }

    public IMPLayout rtlOnly() {
        this.rtlOnly = true;
        return this;
    }

    public IMPLayout clear(){
        if(mainView.getParent() != null){
            set.clone((ConstraintLayout) mainView.getParent());
            set.clear(mainView.getId(),ConstraintSet.BOTTOM);
            set.clear(mainView.getId(),ConstraintSet.TOP);
            set.clear(mainView.getId(),ConstraintSet.RIGHT);
            set.clear(mainView.getId(),ConstraintSet.LEFT);
            set.clear(mainView.getId(),ConstraintSet.START);
            set.clear(mainView.getId(),ConstraintSet.END);
            set.applyTo((ConstraintLayout) mainView.getParent());
            lp.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            lp.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            mainView.setPadding(0,0,0,0);
        }
        return this;
    }

    public IMPLayout padding(int padding){
        padding = IMUtils.dp2px(padding);
        mainView.setPadding(padding,padding,padding,padding);
        return this;
    }

    public IMPLayout padding(int left, int top, int right, int bottom){
        left = IMUtils.dp2px(left);
        top = IMUtils.dp2px(top);
        right = IMUtils.dp2px(right);
        bottom = IMUtils.dp2px(bottom);
        mainView.setPadding(left,top,right,bottom);
        return this;
    }

    public IMPLayout padding(int paddingh,int paddingv){
        paddingh = IMUtils.dp2px(paddingh);
        paddingv = IMUtils.dp2px(paddingv);
        mainView.setPadding(paddingh,paddingv,paddingh,paddingv);
        return this;
    }


    public IMPLayout paddingBottom(float padding){
        padding = IMUtils.dp2px(padding);
        mainView.setPadding(mainView.getPaddingLeft(),mainView.getPaddingTop(),mainView.getPaddingRight(),(int) padding);
        return this;
    }

    public IMPLayout paddingTop(float padding){
        padding = IMUtils.dp2px(padding);
        mainView.setPadding(mainView.getPaddingLeft(), (int) padding,mainView.getPaddingRight(),mainView.getPaddingBottom());
        return this;
    }
    public IMPLayout paddingRight(int padding){
        padding = IMUtils.dp2px(padding);
        mainView.setPadding(mainView.getPaddingLeft(),mainView.getPaddingTop(),padding,mainView.getPaddingBottom());
        return this;
    }
    public IMPLayout paddingLeft(int padding){
        padding = IMUtils.dp2px(padding);
        mainView.setPadding(padding,mainView.getPaddingTop(),mainView.getPaddingRight(),mainView.getPaddingBottom());
        return this;
    }

    public IMPLayout margin(int margin){
        margin = IMUtils.dp2px(margin);
        lp.setMargins(margin,margin,margin,margin);
        return this;
    }


    public IMPLayout margin(int marginh,int marginv){
        marginh = IMUtils.dp2px(marginh);
        marginv = IMUtils.dp2px(marginv);
        lp.setMargins(marginh,marginv,marginh,marginv);
        return this;
    }


    public IMPLayout margin(int left, int top, int right, int bottom){
        left = IMUtils.dp2px(left);
        top = IMUtils.dp2px(top);
        right = IMUtils.dp2px(right);
        bottom = IMUtils.dp2px(bottom);
        lp.setMargins(left,top,right,bottom);
        return this;
    }

    public IMPLayout marginRight(float margin){
        margin = IMUtils.dp2px(margin);
        if(IMUtils.rtl() || rtlOnly){
            lp.setMargins(lp.leftMargin,lp.topMargin,(int)margin,lp.bottomMargin);
        }else{
            lp.setMargins((int)margin,lp.topMargin,lp.rightMargin,lp.bottomMargin);
        }
        return this;
    }
    public IMPLayout marginLeft(int margin){
        margin = IMUtils.dp2px(margin);
        if(IMUtils.rtl() || rtlOnly){
            lp.setMargins(margin,lp.topMargin,lp.rightMargin,lp.bottomMargin);
        }else{
            lp.setMargins(lp.leftMargin,lp.topMargin,margin,lp.bottomMargin);
        }

        return this;
    }

    public IMPLayout marginBottom(int margin){
        margin = IMUtils.dp2px(margin);
        lp.setMargins(lp.leftMargin,lp.topMargin,lp.rightMargin,margin);
        return this;
    }
    public IMPLayout marginBottomPx(int margin){
        lp.setMargins(lp.leftMargin,lp.topMargin,lp.rightMargin,margin);
        return this;
    }

    public IMPLayout marginTop(int margin){
        margin = IMUtils.dp2px(margin);
        lp.setMargins(lp.leftMargin,margin,lp.rightMargin,lp.bottomMargin);
        return this;
    }
    public IMPLayout marginTopPx(int margin){
        lp.setMargins(lp.leftMargin,margin,lp.rightMargin,lp.bottomMargin);
        return this;
    }

    public IMPLayout width(float width){
        lp.width = IMUtils.dp2px(width);
        return this;
    }
    public IMPLayout height(float height){
        lp.height = IMUtils.dp2px(height);
        return this;
    }
    public IMPLayout minWidth(float width){
        mainView.setMinimumWidth(IMUtils.dp2px(width));
        return this;
    }

    public IMPLayout minHeight(float width){
        mainView.setMinimumHeight(IMUtils.dp2px(width));
        return this;
    }

    public IMPLayout heightPx(int height){
        lp.height = height;
        return this;
    }
    public IMPLayout widthPx(int width){
        lp.width = width;
        return this;
    }
    public IMPLayout ratio(String ratio){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.setDimensionRatio(mainView.getId(),ratio);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }
    public IMPLayout edge(View view){
        rightToRightOf(view);
        leftToLeftOf(view);
        topToTopOf(view);
        bottomToBottomOf(view);
        heightMatchConstraint();
        widthMatchConstraint();
        return this;
    }
    public IMPLayout widthMatchParent(){
        lp.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        return this;
    }

    public IMPLayout widthMatchParent(View view,int margin){
        rightToRightOf(view,margin);
        leftToLeftOf(view,margin);
        widthMatchConstraint();
        return this;
    }
    public IMPLayout heightMatchParent(){
        lp.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
        return this;
    }

    public IMPLayout heightMatchConstraint(){
        lp.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        return this;
    }

    public IMPLayout widthMatchConstraint(){
        lp.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        return this;
    }

    public IMPLayout heightWrapContent(){
        lp.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        return this;
    }
    public IMPLayout widthWrapContent(){
        lp.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        return this;
    }

    public IMPLayout size(float size){
        lp.height = IMUtils.dp2px(size);
        lp.width = IMUtils.dp2px(size);
        return this;
    }

    public IMPLayout rightToRightOf(View view){
        return this.rightToRightOf(view,0);
    }
    public IMPLayout rightToRightOf(View view,float margin){
        set.clone((ConstraintLayout) this.mainView.getParent());
        if(IMUtils.rtl() || rtlOnly){
            set.connect(this.mainView.getId(), ConstraintSet.RIGHT,view.getId() , ConstraintSet.RIGHT, IMUtils.dp2px(margin));
        }else{
            set.connect(this.mainView.getId(), ConstraintSet.LEFT,view.getId() , ConstraintSet.LEFT, IMUtils.dp2px(margin));
        }
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout rightToLeftOf(View view){
        return this.rightToLeftOf(view,0);
    }

    public IMPLayout rightToLeftOf(View view,float margin){
        set.clone((ConstraintLayout) this.mainView.getParent());
        if(IMUtils.rtl() || rtlOnly){
            set.connect(this.mainView.getId(), ConstraintSet.RIGHT,view.getId() , ConstraintSet.LEFT, IMUtils.dp2px(margin));
        }else{
            set.connect(this.mainView.getId(), ConstraintSet.LEFT,view.getId() , ConstraintSet.RIGHT, IMUtils.dp2px(margin));
        }
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }


    public IMPLayout leftToLeftOf(View view){
        return this.leftToLeftOf(view,0);
    }
    public IMPLayout leftToLeftOf(View view,float margin){
        set.clone((ConstraintLayout) this.mainView.getParent());
        if(IMUtils.rtl() || rtlOnly){
            set.connect(this.mainView.getId(), ConstraintSet.LEFT,view.getId() , ConstraintSet.LEFT, IMUtils.dp2px(margin));
        }else{
            set.connect(this.mainView.getId(), ConstraintSet.RIGHT,view.getId() , ConstraintSet.RIGHT, IMUtils.dp2px(margin));
        }
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }


    public IMPLayout leftToRightOf(View view){
        return this.leftToRightOf(view,0);
    }

    public IMPLayout leftToRightOf(View view,float margin){
        set.clone((ConstraintLayout) this.mainView.getParent());
        if(IMUtils.rtl() || rtlOnly){
            set.connect(this.mainView.getId(), ConstraintSet.LEFT,view.getId() , ConstraintSet.RIGHT, IMUtils.dp2px(margin));
        }else{
            set.connect(this.mainView.getId(), ConstraintSet.RIGHT,view.getId() , ConstraintSet.LEFT, IMUtils.dp2px(margin));
        }

        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }


    public IMPLayout maxWidthMatchParent(float max){
        int maxPx = IMUtils.dp2px(max);
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.constrainMaxWidth(this.mainView.getId(), maxPx);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout startToEndOf(View view,int margin){
        margin = IMUtils.dp2px(margin);
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.START,view.getId() , ConstraintSet.END, margin);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout endToStartOf(View view,int margin){
        margin = IMUtils.dp2px(margin);
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.END,view.getId() , ConstraintSet.START, margin);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }


    public IMPLayout translationY(float y){
        this.mainView.setTranslationY(IMUtils.dp2px(y));
        return this;
    }
    //topToBottomOf
    public IMPLayout topToBottomOf(View view){
        return this.topToBottomOf(view,0);
    }
    public IMPLayout topToBottomOf(View view,int margin){
        margin = IMUtils.dp2px(margin);
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.TOP,view.getId() , ConstraintSet.BOTTOM, margin);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout bottomToTopOf(View view){
        return this.bottomToTopOf(view,0);
    }
    public IMPLayout bottomToTopOf(View view,int margin){
        margin = IMUtils.dp2px(margin);
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.BOTTOM,view.getId() , ConstraintSet.TOP, margin);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout topToTopOf(View view){
        return this.topToTopOf(view,0);
    }
    public IMPLayout topToTopOf(View view,float margin){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.TOP,view.getId() , ConstraintSet.TOP, IMUtils.dp2px(margin));
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout bottomToBottomOf(View view){
        return this.bottomToBottomOf(view,0);
    }
    public IMPLayout bottomToBottomOf(View view,float margin){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.BOTTOM,view.getId() , ConstraintSet.BOTTOM, IMUtils.dp2px(margin));
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }
    public IMPLayout bottomToBottomOfDp(View view,float margin){
        int newMargin = IMUtils.dp2px(margin);
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.BOTTOM,view.getId() , ConstraintSet.BOTTOM, newMargin);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout bottomToBottomOfParent(){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.BOTTOM,((ConstraintLayout) this.mainView.getParent()).getId() , ConstraintSet.BOTTOM, 0);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout centerX(){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.START,((ConstraintLayout) this.mainView.getParent()).getId() , ConstraintSet.START);
        set.connect(this.mainView.getId(), ConstraintSet.END,((ConstraintLayout) this.mainView.getParent()).getId() , ConstraintSet.END);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout centerX(View view){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.START,view.getId() , ConstraintSet.START);
        set.connect(this.mainView.getId(), ConstraintSet.END,view.getId() , ConstraintSet.END);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }
    public IMPLayout centerX(View view,float margin){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.START,view.getId() , ConstraintSet.START,margin>0?IMUtils.dp2px(margin):0);
        set.connect(this.mainView.getId(), ConstraintSet.END,view.getId() , ConstraintSet.END,margin<0?IMUtils.dp2px(Math.abs(margin)):0);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout centerY(){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.TOP,((ConstraintLayout) this.mainView.getParent()).getId() , ConstraintSet.TOP);
        set.connect(this.mainView.getId(), ConstraintSet.BOTTOM,((ConstraintLayout) this.mainView.getParent()).getId() , ConstraintSet.BOTTOM);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }
    public IMPLayout centerY(View view){
        set.clone((ConstraintLayout) this.mainView.getParent());
        set.connect(this.mainView.getId(), ConstraintSet.TOP,view.getId() , ConstraintSet.TOP);
        set.connect(this.mainView.getId(), ConstraintSet.BOTTOM,view.getId() , ConstraintSet.BOTTOM);
        set.applyTo((ConstraintLayout) this.mainView.getParent());
        return this;
    }

    public IMPLayout center(View view){
        this.centerY(view);
        this.centerX(view);
        return this;
    }

    public IMPLayout debugMode(){
        this.mainView.setBackgroundColor(ColorUtils.getRandomColor());
        return this;
    }
    public IMPLayout debugMode(int color){
        this.mainView.setBackgroundColor(color);
        return this;
    }

    public IMPLayout backgroundColor(int color){
        this.mainView.setBackgroundColor(color);
        return this;
    }

    public IMPLayout radius(float radius){
        if(mainView instanceof IMConstraintLayout){
            ((IMConstraintLayout)mainView).setRadius(radius);
        }
        return this;
    }
}
