package cn.subat.impl_android.utils;

import java.util.HashMap;

import cn.subat.impl_android.view.IMLoadMoreView;
import cn.subat.impl_android.view.IMRefreshView;
import cn.subat.impl_android.view.dialog.IMActionSheetItem;

public class IMConstant {

    public static HashMap<Integer,Class> recyclerViews(){
        HashMap<Integer,Class> views = new HashMap<>();
        views.put(ViewTypeLoadMoreView, IMLoadMoreView.class);
        views.put(ViewTypeRefreshView, IMRefreshView.class);
        views.put(ViewTypeActionSheet, IMActionSheetItem.class);
        return views;
    }
    public static int ViewTypeLoadMoreView = 2001;
    public static int ViewTypeRefreshView = 2002;
    public static int ViewTypeActionSheet = 2003;
    public static int ViewTypeSpaceItem = 2004;
    public enum LoadState{
        Loading,
        Normal,
        NoData,
    }
    public enum SPAnimateState{
        Start,
        Complete,
        Animating,
    }
}
