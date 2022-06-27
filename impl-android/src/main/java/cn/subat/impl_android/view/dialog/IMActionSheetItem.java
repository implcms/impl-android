package cn.subat.impl_android.view.dialog;

import android.content.Context;
import android.view.Gravity;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

import cn.subat.impl_android.model.IMActionSheetData;
import cn.subat.impl_android.utils.IMColor;
import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.view.IMBaseItem;
import cn.subat.impl_android.view.IMTextView;

public class IMActionSheetItem extends IMBaseItem<IMActionSheetData> {

    protected IMTextView titleView;

    public IMActionSheetItem(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setupView() {
        super.setupView();
        titleView = new IMTextView(getContext(),14, IMColor.text);
        titleView.setGravity(Gravity.CENTER);
        titleView.setSingleLine();
        view.addViews(titleView);
        view.setBackgroundColor(IMColor.actionSheetBackground);
        IMPLayout.init(titleView).centerX().padding(15);
    }

    @Override
    public void setData(IMActionSheetData data) {
        super.setData(data);
        titleView.setText(data.name);
        if(data.backgroundColor != -1){
            view.setBackgroundColor(data.backgroundColor);
        }
        if(data.textColor != 0){
            titleView.setTextColor(data.textColor);
        }
        if(data.marginTop != 0){
            IMPLayout.update(this).marginTop(data.marginTop);
        }
        if(data.padding != 0){
            IMPLayout.init(titleView).padding(data.padding);
        }

        if(data.radius != -1){
            view.setRadius(data.radius);
        }

        if(data.marginHorizontal != 0){
            IMPLayout.update(this).marginRight(data.marginHorizontal);
            IMPLayout.update(this).marginLeft(data.marginHorizontal);
        }else{
            view.setRadius(0);
        }

        if(data.padding != 0){
            IMPLayout.init(titleView).centerX().padding(data.padding);
        }

        if(data.lastOne && view.borderLine != null){
            view.borderLine.setVisibility(INVISIBLE);
        }
    }
}
