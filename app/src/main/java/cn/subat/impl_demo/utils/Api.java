package cn.subat.impl_demo.utils;

import com.google.gson.internal.LinkedTreeMap;

import cn.subat.impl_android.utils.IMApi;

public class Api<T> extends IMApi<T> {

    public static <T> Api<T> post(Class<T> t, String path){
        Api<T> api = new Api<>();
        return (Api<T>) api.init(t,path,"post");
    }
    public static <T> Api<T> get(Class<T> t, String path){
        Api<T> api = new Api<>();
        return (Api<T>) api.init(t,path,"get");
    }

    @Override
    protected String getHost() {
        return "https://www.zhihu.com/api/v4/";
    }

    @Override
    protected LinkedTreeMap<String, Object> setupParam(LinkedTreeMap<String, Object> param) {
        param.put("terminal","wap");
        return param;
    }
}
