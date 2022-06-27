package cn.subat.impl_android.model;

import java.util.ArrayList;

public class IMPaginate<T> {
    public int current_page;
    public int last_page;
    public int total;
    public ArrayList<T> data;
    public void setViewType(int viewType){
        for (Object model:data){
            ((IMViewModel)model).viewType = viewType;
        }
    }
}
