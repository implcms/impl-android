package cn.subat.impl_demo.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;


import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

import cn.subat.impl_android.activity.IMBaseActivity;
import cn.subat.impl_android.model.IMViewModel;
import cn.subat.impl_android.utils.IMUtils;
import cn.subat.impl_android.view.IMRecyclerView;
import cn.subat.impl_demo.R;
import cn.subat.impl_demo.model.TopSearch;
import cn.subat.impl_demo.pages.DetailPage;
import cn.subat.impl_demo.pages.HomePage;
import cn.subat.impl_demo.utils.Constant;
import cn.subat.impl_demo.views.WordItem;

public class HomeActivity extends IMBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.ImplMain);
        IMUtils.updateLocal(this,false);
        super.onCreate(savedInstanceState);

        LogUtils.getConfig().setGlobalTag("impl_log");

        registerViews();

        navigateTo(new HomePage());

        BusUtils.register(this);
    }

    @Override
    public void onUpdateLocal() {
        super.onUpdateLocal();
        redirectTo(new HomePage());
    }

    public void registerViews(){
        IMRecyclerView.views.put(Constant.viewTypeWord, WordItem.class);
    }

    @Override
    public void onItemClick(int index, IMViewModel data) {
        super.onItemClick(index, data);
        if(data instanceof TopSearch.Word){
            DetailPage page = new DetailPage();
            page.setParam("word",data);
            navigateTo(page);
        }
    }
}

