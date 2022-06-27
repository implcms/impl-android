package cn.subat.impl_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.subat.impl_android.R;
import cn.subat.impl_android.fragment.IMBasePage;
import cn.subat.impl_android.model.IMViewModel;
import cn.subat.impl_android.utils.IMUtils;
import cn.subat.impl_android.view.IMConstraintLayout;
import cn.subat.impl_android.view.dialog.IMActionSheet;

public class IMBaseActivity extends FragmentActivity implements IMBasePage.Navigator{

    long mExitTime;
    public IMConstraintLayout view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments",null);
        }
        view = new IMConstraintLayout(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setId(View.generateViewId());
        }else{
            view.setId((int) (Math.random()*1000));
        }
        IMUtils.setStatusBarFullTransparent();
        view.setFitsSystemWindows(false);
        setContentView(view);
        BusUtils.register(this);
    }

    @BusUtils.Bus(tag = "updateLocal")
    public void onUpdateLocal(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        IMUtils.updateLocal(this,false);
    }

    public void onReCreate(){

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public <T> T getSPExtraData(String key,final Class<T> t){
        Intent intent = getIntent();
        return getSPExtraData(intent,key,t);
    }

    public String getSPExtraString(String key){
        Intent intent = getIntent();
        return intent.getStringExtra(key);
    }

    public <T> T getSPExtraData(Intent intent,String key,final Class<T> t){
        String data = intent.getStringExtra(key);
        return new Gson().fromJson(data,t);
    }

    @Override
    public void navigateTo(IMBasePage fragment) {
        navigateTo(fragment,view);
    }

    @Override
    public void navigateTo(IMBasePage fragment, View targetView) {
        if(targetView == null) return;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_out,R.anim.fade_in);
        if(fragments().size()>0){
            topFragment().onHide();
        }
        fragment.navigator = this;
        transaction.add(targetView.getId(),fragment);
        transaction.commit();
        fragment.onShow();
    }


    @Override
    public void redirectTo(IMBasePage fragment) {
        redirectTo(fragment,view);
    }

    @Override
    public void redirectTo(IMBasePage fragment, View targetView) {
        if(targetView == null) return;
        fragment.navigator = this;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out);
        transaction.replace(targetView.getId(),fragment);
        transaction.commit();
    }


    @Override
    public void navigateBack(int delta) {
        FragmentTransaction transaction = getManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        int size = fragments().size();
        int index = size - delta - 1;
        if(index<0) return;

        IMBasePage oldFragment = fragments().get(index+1);
        oldFragment.onNavigateBack();

        IMBasePage fragment = fragments().get(index);
        if(fragment == null) return;
        fragment.onShow();

        transaction.show(fragment);
        transaction.commit();
        for (int i = (size-1); i >= (size - delta); i--) {
            transaction.remove(fragments().get(i));
        }
    }

    public IMBasePage topFragment(){
        return fragments().get(fragments().size()-1);
    }

    public void onItemClick(int index, IMViewModel data) {

    }


    @Override
    public FragmentManager getManager() {
        return getSupportFragmentManager();
    }

    public ArrayList<IMBasePage> fragments(){
        ArrayList<IMBasePage> fragments = new ArrayList<>();
        for (Fragment fragment:getManager().getFragments()){
            IMBasePage baseFragment = (IMBasePage) fragment;
            if(!baseFragment.isViewPager){
                fragments.add(baseFragment);
            }
        }
        return fragments;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onBackPressed() {

        //先关闭所有ActionSheet
        if(IMActionSheet.actionSheetList != null && IMActionSheet.actionSheetList.size()>0){
            IMActionSheet.actionSheetList.get(0).hide();return;
        }

        //先返回到首页
        List<Fragment> allFragments = getSupportFragmentManager().getFragments();
        try {
            LogUtils.w(allFragments);
            if(allFragments.size()>1){
                IMBasePage fragment = (IMBasePage) allFragments.get(allFragments.size()-1);
                if(!fragment.onBackPressed()){
                    navigateBack(1);
                }
                return;
            }
        }catch (Exception e){

        }

        //延时退出
        long currentTime = System.currentTimeMillis();
        if((currentTime-mExitTime)>=2000) {
            exitAlert();
            mExitTime = currentTime;
            return;
        }
        super.onBackPressed();
    }

    public void exitAlert(){
        ToastUtils.showShort(R.string.really_exit);
    }

}
