package cn.subat.impl_android.model;

import com.google.gson.Gson;

public class IMBaseModel {
    public int id;
    public String name;
    public int viewType;
    public int viewColumn;
    public String toJson(){
        return new Gson().toJson(this);
    }
}
