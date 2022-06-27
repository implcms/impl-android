package cn.subat.impl_android.view.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;

import cn.subat.impl_android.R;
import cn.subat.impl_android.model.IMActionSheetData;
import cn.subat.impl_android.utils.IMAnimate;
import cn.subat.impl_android.utils.IMColor;
import cn.subat.impl_android.utils.IMConstant;
import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.utils.IMUtils;
import cn.subat.impl_android.view.IMImageButton;
import cn.subat.impl_android.view.IMRecyclerView;
import cn.subat.impl_android.view.IMTextView;

public class IMActionSheet extends IMBaseDialog implements IMRecyclerView.Listener{

    public static ArrayList<IMActionSheet> actionSheetList;
    public IMRecyclerView recyclerView;
    IMImageButton closeBtn;
    IMTextView titleView;
    float height;
    Listener listener;
    ConfirmListener confirmListener;

    public interface Listener<T>{
        default void onItemClick(IMActionSheet actionSheet, int index, T data) {

        }
        default void onLoadMore(IMActionSheet actionSheet){

        }

        default void onShow(IMActionSheet actionSheet){

        }
        default void onHide(IMActionSheet actionSheet){

        }
    }

    public interface ConfirmListener{
        void onConfirm(IMActionSheet actionSheet, boolean confirm);
    }

    public static IMActionSheet create(Context context){
        return new IMActionSheet(context);
    }

    public IMActionSheet(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setup() {
        super.setup();
        container.setRadius(10);
        recyclerView = new IMRecyclerView(getContext(),true,12);
        recyclerView.setListener(this);
        closeBtn = new IMImageButton(getContext(), R.drawable.ic_close);
        closeBtn.setOnClickListener(v -> hide());
        titleView = new IMTextView(getContext(),14, IMColor.text);
        titleView.setText("");
        container.addViews(titleView,closeBtn,recyclerView);
        IMPLayout.init(container).topToBottomOf(view).widthMatchParent().heightWrapContent();
        IMPLayout.init(titleView).rightToRightOf(container,15).topToTopOf(container,15);
        IMPLayout.init(closeBtn).size(44).padding(8).centerY(titleView).leftToLeftOf(container);
        IMPLayout.init(recyclerView).topToBottomOf(closeBtn,10).widthMatchParent(container,0).padding(0,0,0,15);

    }

    public IMActionSheet setTitle(String title){
        titleView.setText(title);
        return this;
    }

    public IMActionSheet setTitle(int title){
        titleView.setText(IMUtils.getString(title));
        return this;
    }

    @Override
    public IMActionSheet show(){
        super.show();
        if(IMActionSheet.actionSheetList == null){
            IMActionSheet.actionSheetList = new ArrayList<>();
        }
        IMActionSheet.actionSheetList.add(this);
        view.post(()-> {
            height = Math.min(IMUtils.px2dp(container.getHeight()),IMUtils.px2dp(IMUtils.getScreenPxHeight()/1.5f));

            IMAnimate.init(container).on(state -> {
                if(state == IMConstant.SPAnimateState.Complete){
                    if(listener != null){
                        listener.onShow(this);
                    }
                    if(IMUtils.px2dp(container.getHeight())>IMUtils.px2dp(IMUtils.getScreenPxHeight()/1.5f)){
                        IMPLayout.update(container).height(height);
                        IMPLayout.update(recyclerView).bottomToBottomOf(container).heightMatchConstraint();
                    }
                }
            }).moveVertically(-height).run(200);
        });
        return this;
    }

    @Override
    public void hide(){
        super.hide();
        if(IMActionSheet.actionSheetList != null){
            IMActionSheet.actionSheetList.remove(this);
        }
        IMAnimate.init(container).moveVertically(0).on(state -> {
            if(state == IMConstant.SPAnimateState.Complete){
                window.dismiss();
                if(listener != null){
                    listener.onHide(this);
                }
            }
        }).run(200);
    }

    @Override
    public void onItemClick(int index, Object data) {
        if(listener != null){
            listener.onItemClick(this,index,data);
        }
    }

    @Override
    public void onLoadMore(IMRecyclerView recyclerView) {
        if(listener != null){
            listener.onLoadMore(this);
        }
    }

    public IMActionSheet setPadding(int padding){
        IMPLayout.update(recyclerView).widthMatchParent(container,padding);
        return this;
    }

    public IMActionSheet setData(ArrayList models){
        recyclerView.adapter.setData(models);
        requestLayout();
        return this;
    }

    public IMActionSheet setData(ArrayList models, int type){
        recyclerView.adapter.setData(models,type);
        requestLayout();
        return this;
    }

    public IMActionSheet setData(ArrayList models, int type, int column){
        recyclerView.adapter.setData(models,type,column);
        requestLayout();
        return this;
    }

    public IMActionSheet setActionSheetData(int...words){
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            strings.add(IMUtils.getString(words[i]));
        }
        String[] newStrings = new String[strings.size()];
        strings.toArray(newStrings);
        return setActionSheetData(newStrings);
    }

    public IMActionSheet setActionSheetData(String...words){

        ArrayList<IMActionSheetData> list = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            IMActionSheetData category = new IMActionSheetData();
            category.name = words[i];
            category.viewType = IMConstant.ViewTypeActionSheet;
            category.viewColumn = 1;
            list.add(category);
        }

        recyclerView.adapter.addData(list,IMConstant.ViewTypeActionSheet,12);

        requestLayout();

        return this;
    }

    public IMActionSheet onClick(Listener listener){
        this.listener = listener;
        return this;
    }

    public IMActionSheet onConfirm(ConfirmListener confirmListener){
        this.confirmListener = confirmListener;
        return this;
    }

    public IMActionSheet setLoadMore(Boolean enable){
        recyclerView.setLoadMoreEnabled(enable);
        return this;
    }
}
