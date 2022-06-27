package cn.subat.impl_demo.views;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;

import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.view.IMBaseItem;
import cn.subat.impl_android.view.IMTextView;
import cn.subat.impl_demo.model.TopSearch;
import cn.subat.impl_demo.utils.IDColor;

public class WordItem extends IMBaseItem<TopSearch.Word> {

    IMTextView textView;

    public WordItem(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setupView() {
        super.setupView();
        textView = new IMTextView(getContext(),14, IDColor.text);
        view.addView(textView);
        IMPLayout.init(view).padding(10);
        IMPLayout.init(textView).center(view);
    }

    @Override
    public void setData(TopSearch.Word data) {
        super.setData(data);
        textView.setText(data.query);
    }
}
