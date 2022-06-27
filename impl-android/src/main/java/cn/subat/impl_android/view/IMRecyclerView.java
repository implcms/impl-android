package cn.subat.impl_android.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.subat.impl_android.model.IMBaseModel;
import cn.subat.impl_android.utils.IMConstant;
import cn.subat.impl_android.utils.IMUtils;

public class IMRecyclerView extends RecyclerView {

    public Adapter adapter;
    Listener listener;
    IMLoadMoreView loadMoreView;
    boolean loadMoreEnabled;
    IMRefreshView refreshView;
    boolean refreshEnabled;
    private float mLastY = -1;
    public static HashMap<Integer,Class> views = IMConstant.recyclerViews();

    public IMRecyclerView(Context context) {
        super(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        init(layoutManager);
    }

    public IMRecyclerView(Context context, @RecyclerView.Orientation int orientation) {
        super(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,orientation,false);
        init(layoutManager);
    }

    public IMRecyclerView(Context context, boolean grid, int spanCount) {
        super(context);
        SPGridLayoutManager layoutManager = new SPGridLayoutManager(context, spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(loadMoreEnabled){
                    if(adapter.getItemCount()-1 == position) return spanCount;
                }
                if(refreshEnabled){
                    if(position == 0) return spanCount;
                    position--;
                }
                IMBaseModel model = (IMBaseModel) adapter.data.get(position);
                if(model.viewColumn == 0){
                    return 1;
                }else if(model.viewColumn == -1){
                    return spanCount;
                }
                return model.viewColumn;
            }
        });
        init(layoutManager);
    }


    public void init(LayoutManager layoutManager){
        adapter = new Adapter();
        adapter.recyclerView = this;
        setAdapter(adapter);
        setLayoutManager(layoutManager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if(IMUtils.rtl()){
                setLayoutDirection(LAYOUT_DIRECTION_RTL);
            }else{
                setLayoutDirection(LAYOUT_DIRECTION_LTR);
            }
        }
    }

    public void setRadius(float radius){
        //layoutHelper.setRadius(SPUtils.dp2px(radius));
    }

    public void setShadowElevation(int elevation) {
        //layoutHelper.setShadowElevation(elevation);
    }

    public void setShadowColor(int color) {
        //layoutHelper.setShadowColor(color);
    }

    public void setSpace(int space){
        setSpace(space,space);
    }

    public void setSpace(int horizontal,int vertical){
        SpaceItemDecoration spacingDecoration = new SpaceItemDecoration(IMUtils.dp2px(horizontal),IMUtils.dp2px(vertical));
        addItemDecoration(spacingDecoration);
    }


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.loadMoreEnabled = loadMoreEnabled;
    }

    public void setRefreshEnabled(boolean refreshEnabled) {
        this.refreshEnabled = refreshEnabled;
    }

    public void refreshComplete(){
        if(refreshEnabled && refreshView != null){
            refreshView.setLoadState(IMConstant.LoadState.Normal);
        }

    }

    public void loadMoreComplete(){
        if(loadMoreEnabled && loadMoreView != null){
            loadMoreView.setState(IMConstant.LoadState.Normal);
        }

    }

    float y1, y2;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                y1 = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if(isOnTop() && refreshEnabled){
                    refreshView.onMove(deltaY / 3);
                }

                break;
            default:
                mLastY = -1; // reset

                if(isOnTop() && refreshEnabled){
                    if(refreshView.releaseAction()){
                        if(listener != null){
                            listener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isOnTop() {
        if(!refreshEnabled) return false;
        if (refreshView.getParent() != null) {
            return true;
        } else {
            return false;
        }

    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && loadMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof SPGridLayoutManager) {
                lastVisibleItemPosition = ((SPGridLayoutManager) layoutManager).findLastVisibleItemPosition();
            }  else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            boolean isTop;
            if(refreshEnabled){
                isTop = (isOnTop() && refreshView.view.getHeight()>5);
            }else{
                isTop = isOnTop();
            }
            if(loadMoreView != null && !isTop && lastVisibleItemPosition >= adapter.getItemCount()-2 && loadMoreView.state != IMConstant.LoadState.Loading){
                loadMoreView.setState(IMConstant.LoadState.Loading);
                post(()->{
                    smoothScrollToPosition(lastVisibleItemPosition+1);
                });
                if(listener != null){
                    listener.onLoadMore(this);
                }
            }
        }
    }

    public void smoothScrollToPositionCenter(int position) {
        SmoothScroller smoothScroller = new CenterSmoothScroller(getContext());
        smoothScroller.setTargetPosition(position);
        if(getLayoutManager() != null){
            getLayoutManager().startSmoothScroll(smoothScroller);
        }
    }

    public class CenterSmoothScroller extends LinearSmoothScroller {

        public CenterSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }
    }

    public void smoothScrollToPositionWithDuration(int position,float flag) {
        SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_ANY;
            }
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return flag / displayMetrics.densityDpi;
            }
        };
        smoothScroller.setTargetPosition(position);
        if(getLayoutManager() != null){
            getLayoutManager().startSmoothScroll(smoothScroller);
        }
    }

    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;
        private int bottom;

        public SpaceItemDecoration(int space) {
            this.space = space;
            this.bottom = 0;
        }
        public SpaceItemDecoration(int space,int bottom) {
            this.space = space;
            this.bottom = bottom;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
                int spanSize = layoutParams.getSpanSize();
                int spanIndex = layoutParams.getSpanIndex();
                int totalSpanSize = gridLayoutManager.getSpanCount();

                if(totalSpanSize == spanSize && spanIndex == 0){
                    outRect.left = space ;
                    outRect.right = space;
                }else if (spanIndex == 0) {//left
                    outRect.left = IMUtils.rtl()?space / 2:space;
                    outRect.right = IMUtils.rtl()?space:space / 2;
                } else if (spanSize + spanIndex == totalSpanSize) {//right
                    outRect.right = IMUtils.rtl()?space / 2:space;
                    outRect.left = IMUtils.rtl()?space:space / 2;
                } else if (spanIndex > 0 && spanSize + spanIndex < totalSpanSize) {
                    outRect.left = space / 2;
                    outRect.right = space / 2;
                }
                outRect.bottom = bottom;
            }else if(parent.getLayoutManager() instanceof LinearLayoutManager){
                outRect.bottom = bottom/2;
                outRect.left = space;
                outRect.right = space;
                outRect.top = bottom/2;
            }
        }
    }

    public interface Listener<T>{

        default void onItemClick(int index,T data){

        }
        default void onItemFocused(int index,View v, boolean hasFocus){

        }
        default void onDelete(int index){

        }
        default void onChildOnClick(View view,T model){

        }
        default boolean onChildLongClick(View view,T model){
            return false;
        }
        default void onLoadMore(IMRecyclerView recyclerView){

        }
        default void onRefresh(){

        }
    }

    public static class SPGridLayoutManager extends GridLayoutManager {

        public SPGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        @Override
        public boolean isAutoMeasureEnabled() {
            return true;
        }

    }

    public static class Holder extends RecyclerView.ViewHolder{
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class Adapter extends RecyclerView.Adapter<Holder> {

        ArrayList data = new ArrayList();
        IMRecyclerView recyclerView;

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(getItemViewByType(viewType,parent));
        }


        public ArrayList getData() {
            return data;
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            if(loadMoreEnabled){
                if(position == getItemCount()-1) return;
            }
            if(refreshEnabled){
                if(position == 0) return;
                position--;
            }

            IMBaseItem item = (IMBaseItem) holder.itemView;
            item.listener = listener;
            int finalPosition = position;
            item.setOnFocusChangeListener((v, hasFocus) -> {
                if(listener != null){
                    listener.onItemFocused(finalPosition,item,hasFocus);
                }
            });
            item.setOnClickListener(v -> {
                if(listener != null){
                    listener.onItemClick(finalPosition,data.get(finalPosition));
                }
            });
            item.setData(data.get(position));
        }

        @Override
        public int getItemCount() {
            int size = data.size();
            if(loadMoreEnabled){
                size++;
            }
            if(refreshEnabled){
                size++;
            }
            return size;
        }

        public void setData(List data) {
            this.data.clear();
            if(data != null){
                this.data.addAll(data);
            }
            notifyDataSetChanged();
        }
        public void setData(Object data) {
            this.data.clear();
            if(data != null){
                this.data.add(data);
                notifyDataSetChanged();
            }
        }

        public void setData(List dataList,int type) {
            this.data.clear();
            if(dataList != null){
                for (Object data:dataList){
                    ((IMBaseModel)data).viewType = type;
                }
                this.data.addAll(dataList);
                notifyDataSetChanged();
            }
        }

        public void setData(List dataList,int type,int column) {
            this.data.clear();
            if(dataList != null){
                for (Object data:dataList){
                    ((IMBaseModel)data).viewType = type;
                    ((IMBaseModel)data).viewColumn = column;
                }
                this.data.addAll(dataList);
                notifyDataSetChanged();
            }
        }

        public void addData(ArrayList dataList,int type,int column) {
            int pos = getItemCount();
            if(dataList.size() != 0){
                for (Object data:dataList){
                    ((IMBaseModel)data).viewType = type;
                    ((IMBaseModel)data).viewColumn = column;
                }
                this.data.addAll(dataList);
                notifyItemRangeInserted(pos,this.data.size()-1);
            }
        }
        public void addData(ArrayList dataList,int type) {
            int pos = getItemCount();
            if(dataList.size() != 0){
                for (Object data:dataList){
                    ((IMBaseModel)data).viewType = type;
                }
                this.data.addAll(dataList);
                notifyItemRangeInserted(pos,this.data.size()-1);
            }
        }
        public void addData(ArrayList data) {
            int pos = getItemCount();
            if(data.size() != 0){
                this.data.addAll(data);
                notifyItemRangeInserted(pos,this.data.size()-1);
            }
        }
        public void addDataToTop(ArrayList data) {
            if(data.size() != 0){
                this.data.addAll(0,data);
                notifyItemRangeInserted(0,data.size()-1);
            }
        }

        public void removeAt(int index) {
            this.data.remove(index);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            if(loadMoreEnabled){
                if(position == getItemCount()-1) return IMConstant.ViewTypeLoadMoreView;
            }
            if(refreshEnabled){
                if(position == 0) return IMConstant.ViewTypeRefreshView;
                position--;
            }
            return ((IMBaseModel)data.get(position)).viewType;
        }

        public View getItemViewByType(int viewType, View parent){

            Class<?> viewTypeClass = views.get(viewType);
            View view = null;
            if(viewTypeClass != null){
                try {
                    Constructor<?> constructor = viewTypeClass.getConstructor(Context.class);
                    view = (View) constructor.newInstance(parent.getContext());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            if(view == null){
                view = new IMBaseItem<>(parent.getContext());
            }
            if(view instanceof IMLoadMoreView){
                loadMoreView = (IMLoadMoreView) view;
            }

            if(view instanceof IMRefreshView){
                refreshView = (IMRefreshView) view;
            }

            return view;
        }
    }
}

