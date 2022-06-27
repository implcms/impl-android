package cn.subat.impl_android.utils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import cn.subat.impl_android.model.IMBaseModel;
import cn.subat.impl_android.model.IMPaginate;
import cn.subat.impl_android.model.IMViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public abstract class IMApi<T>   implements Callback,JsonDeserializer<ArrayList<IMBaseModel>> {

    final static int responseTypeOne = 1;
    final static int responseTypeAll = 2;
    final static int responseTypeList = 3;
    final static int responseTypeRaw = 4;
    protected String path;
    protected LinkedTreeMap<String,Object> param;
    protected Type type;
    protected IMCallback callback;
    protected int responseType;
    protected String method;
    protected String hostClone;

    public IMApi<T> init(Class<T> t, String path,String method){
        this.path = path;
        this.param = new LinkedTreeMap<>();
        type = TypeToken.get(t).getType();
        this.method = method;
        return this;
    }

    public IMApi<T> addParam(LinkedTreeMap linkedHashMap){
        return this;
    }

    public IMApi<T> addParam(String key,Object value){
        param.put(key,value);
        return this;
    }

    public IMApi<T> onOne(IMCallback<T> callback){
        this.callback = callback;
        this.responseType = responseTypeOne;
        request();
        return this;
    }

    public IMApi<T> onAll(IMCallback<ArrayList<T>> callback){
        this.callback = callback;
        this.responseType = responseTypeAll;
        request();
        return this;
    }

    public IMApi<T> onRaw(IMCallback<T> callback){
        this.callback = callback;
        this.responseType = responseTypeRaw;
        request();
        return this;
    }

    public IMApi<T> onList(IMCallback<IMPaginate<T>> callback){
        this.callback = callback;
        this.responseType = responseTypeList;
        request();
        return this;
    }

    protected LinkedTreeMap<String,Object> setupParam(LinkedTreeMap<String,Object> param){
        return param;
    }
    protected String getHost(){
        return "https://localhost";
    }

    private void request(){
        param = setupParam(param);
        String host = getHost();
        if(this.path.contains("@")){
            param.put("impl",new Gson().fromJson("{api:"+path+"}",LinkedTreeMap.class));
        }else{
            host += path;
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient sClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .proxy(Proxy.NO_PROXY)
                .build();

        JsonObject jsonObject = new Gson().toJsonTree(param).getAsJsonObject();
        String body = new Gson().toJson(jsonObject,JsonObject.class);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"),body);

        final Request request;
        if(method.equals("post")){
            request = new Request.Builder().url(host).post(requestBody).build();
        }else{
            request = new Request.Builder().url(host).get().build();
        }
        sClient.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        LogUtils.e(e.getLocalizedMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.code() == 200){
            String body = response.body().string();
            JsonObject spResponse = new Gson().fromJson(body, JsonObject.class);
            onCallback(spResponse);
        }else{
            LogUtils.w(response.code(),response.body().string());
        }
    }

    public void onCallback(JsonObject spResponse){
        switch (responseType){
            case responseTypeList:
                type = TypeToken.getParameterized(IMPaginate.class,type).getType();
                break;
            case responseTypeAll:
                type = TypeToken.getParameterized(ArrayList.class,type).getType();
                break;
        }
        parseData(spResponse,type);
    }

    public void parseData(JsonObject spResponse,Type type){
        Object data;
        int rc = 1;
        String msg = "ok";
        if(responseType == responseTypeRaw){
            data = getGsonBuilder().create().fromJson(spResponse,type);
        }else{
            data = getGsonBuilder().create().fromJson(spResponse.getAsJsonObject("data"),type);
            rc = spResponse.get("rc").getAsInt();
            msg = spResponse.get("msg").getAsString();
        }
        Object finalList = data;
        int finalRc = rc;
        String finalMsg = msg;
        ThreadUtils.runOnUiThread(()->{
            if(this.callback != null){
                callback.onSuccess(finalList, finalRc, finalMsg);
            }
        });
    }

    public GsonBuilder getGsonBuilder(){
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
        builder.registerTypeAdapter(TypeToken.getParameterized(ArrayList.class,IMViewModel.class).getType(),this);
        return builder;
    }

    @Override
    public ArrayList<IMBaseModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<IMBaseModel> viewList = new ArrayList<>();
        return viewList;
    }
}

