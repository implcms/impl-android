package cn.subat.impl_android.utils;

public interface IMCallback<T>{
    void onSuccess(T data,int rc,String msg);
}