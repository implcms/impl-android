package cn.subat.impl_demo.pages;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;

import cn.subat.impl_android.fragment.IMBasePage;
import cn.subat.impl_android.model.IMActionSheetData;
import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.utils.IMUtils;
import cn.subat.impl_android.view.IMImageButton;
import cn.subat.impl_android.view.IMImageView;
import cn.subat.impl_android.view.IMRecyclerView;
import cn.subat.impl_android.view.IMTextView;
import cn.subat.impl_android.view.dialog.IMActionSheet;
import cn.subat.impl_demo.R;
import cn.subat.impl_demo.model.TopSearch;
import cn.subat.impl_demo.utils.Api;
import cn.subat.impl_demo.utils.Constant;
import cn.subat.impl_demo.utils.IDColor;

public class HomePage extends IMBasePage implements IMRecyclerView.Listener<TopSearch.Word> {


    IMRecyclerView recyclerView;

    @Override
    public void setupView() {
        super.setupView();
        view.setBackgroundColor(IDColor.background);

        IMTextView titleView = new IMTextView(getContext(),16, IDColor.text);
        titleView.setText(R.string.top_words);

        IMImageButton langBtn = new IMImageButton(getContext(),R.drawable.ic_lang);
        langBtn.setOnClickListener(this::selectLang);

        recyclerView = new IMRecyclerView(getContext(),true,12);
        recyclerView.setRefreshEnabled(true);
        recyclerView.setListener(this);

        view.addViews(titleView,langBtn,recyclerView);

        IMPLayout.init(titleView).centerX(view).topToTopOf(view,30);
        IMPLayout.init(langBtn).size(30).padding(5).centerY(titleView).leftToLeftOf(view,15);
        IMPLayout.init(recyclerView).widthMatchParent().topToBottomOf(titleView,15).bottomToBottomOf(view).heightMatchConstraint();
    }

    private void selectLang(View view) {
        IMActionSheet.create(getContext()).setTitle(R.string.choose_lang)
        .setActionSheetData(R.string.ug, R.string.zh, R.string.en)
        .onClick(new LangClick())
        .show();
    }

    @Override
    public void onShow() {
        super.onShow();
        IMUtils.lightStatusBar();
    }

    @Override
    public void loadData() {
        super.loadData();
        Api.get(TopSearch.class,"search/top_search").onRaw((data, rc, msg) -> {
            recyclerView.adapter.setData(data.top_search.words, Constant.viewTypeWord,12);
            recyclerView.refreshComplete();
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onItemClick(int index, TopSearch.Word data) {
        navigator.onItemClick(index,data);
    }

    public class LangClick implements IMActionSheet.Listener<IMActionSheetData>{
        @Override
        public void onItemClick(IMActionSheet actionSheet, int index, IMActionSheetData data) {
            actionSheet.hide();
            SPUtils.getInstance().put("lang",index);
            IMUtils.updateLocal(ActivityUtils.getTopActivity(),true);
        }
    }

}
