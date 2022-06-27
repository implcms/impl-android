package cn.subat.impl_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cn.subat.impl_android.activity.IMBaseActivity;
import cn.subat.impl_android.model.IMBaseModel;
import cn.subat.impl_android.model.IMViewModel;
import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.view.IMConstraintLayout;
import cn.subat.impl_android.view.IMImageView;

public class IMBasePage extends Fragment {

    public IMConstraintLayout rootView;
    public IMConstraintLayout view;
    public Navigator navigator;
    protected Context mContext;
    public ExistFocusListener existFocusListener;
    public IMImageView backgroundImageView;
    public boolean isViewPager = false;

    public interface Navigator{
        void navigateTo(IMBasePage fragment);
        void navigateTo(IMBasePage fragment, View targetViewId);
        void redirectTo(IMBasePage fragment);
        void redirectTo(IMBasePage fragment, View targetViewId);
        void navigateBack(int delta);
        void onItemClick(int index, IMViewModel data);
        FragmentManager getManager();
        IMBasePage topFragment();
    }

    public interface ExistFocusListener{
        boolean onExitFocus();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = (IMBaseActivity)ActivityUtils.getTopActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IMPLayout.init(rootView).widthMatchParent().heightMatchParent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setupView();
        return rootView;
    }

    public void setupView(){
        rootView = new IMConstraintLayout(getContext());
        view = new IMConstraintLayout(getContext());
        rootView.addViews(view);
        view.setOnClickListener(v -> {
            onViewClick();
        });
        IMPLayout.init(view).widthMatchParent().heightMatchParent();
        rootView.post(this::loadData);
    }

    public void setParam(String key, IMBaseModel model){
        Bundle bundle = getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }
        bundle.putString(key,model.toJson());
        setArguments(bundle);
    }

    public void setParam(String key, String data){
        Bundle bundle = getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }
        bundle.putString(key,data);
        setArguments(bundle);
    }

    public String getParam(String key){
        return (String) getArguments().get(key);
    }

    public <T> T getParam(Class<T> t,String key){
        if(getArguments() == null) return null;
        if(getArguments().getString(key) == null) return null;
        return new Gson().fromJson(getArguments().getString(key), (Type) t);
    }

    public <T> ArrayList<T> getParamList(Class<T> t,String key){
        Type type = TypeToken.getParameterized(ArrayList.class,t).getType();
        return new Gson().fromJson(getArguments().getString(key), type);
    }

    public void onViewClick(){

    }

    public void onNavigateBack(){

    }

    public void loadData(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onBackPressed(){
        return false;
    }

    public void onShow(){
    }

    public void onHide(){

    }

    public View getContainer(){
        if(rootView != null){
            return (ViewGroup)rootView.getParent();
        }else {
            return null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    public boolean enterFocus(){
        return false;
    }

    public void setExistFocusListener(ExistFocusListener existFocusListener) {
        this.existFocusListener = existFocusListener;
    }

    /**
     * 遥控器点击事件处理
     * @param event
     * @return
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }

    public View getCurrentFocus(){
        return getActivity().getCurrentFocus();
    }

    public boolean fixFocus(KeyEvent event){
        if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
        View current = getCurrentFocus();
        int direction = -1;

        switch (event.getKeyCode()){
            case KeyEvent.KEYCODE_DPAD_DOWN:{
                direction = View.FOCUS_DOWN;
                break;
            }
            case KeyEvent.KEYCODE_DPAD_UP:{
                direction = View.FOCUS_UP;
                break;
            }
            case KeyEvent.KEYCODE_DPAD_RIGHT:{
                direction = View.FOCUS_RIGHT;
                break;
            }
            case KeyEvent.KEYCODE_DPAD_LEFT:{
                direction = View.FOCUS_LEFT;
                break;
            }
        }
        //如果不是方向键直接跳过
        if(direction == -1) return false;
        //查找下一个焦点，如果同级别不存在的话 停止移动
        View nextView = view.focusSearch(current,direction);
        if(nextView == null || nextView.getParent() != current.getParent()){
            return onInBorder(direction,current);
        }
        return false;
    }

    public boolean onInBorder(int direction,View current){
        return false;
    }
}