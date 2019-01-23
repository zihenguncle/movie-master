package com.bw.movie.mvp.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManger {
    private static RetrofitManger instance;
    private OkHttpClient client;
    private BaseApils baseApils;
    private final String BASE_URL="172.17.8.100";
    //private final String BASE_URL="mobile.bwstudent.com";
    //创建单例
    public static RetrofitManger getInstance(){
        if(instance==null){
            synchronized (RetrofitManger.class){
                instance=new RetrofitManger();
            }
        }
        return instance;
    }
    //构造方法
    private RetrofitManger(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor interceptor1 = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client=new OkHttpClient.Builder()
                .writeTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000,TimeUnit.SECONDS)
                .connectTimeout(2000, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor1)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();

        baseApils=retrofit.create(BaseApils.class);

    }
    //get请求
    public void getRequest(String url,HttpCallBack httpCallBack){
        baseApils.getRequest(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpCallBack));

    }
    //post请求
    public void postRequest(String url, Map<String,String> map, HttpCallBack httpCallBack){
        if(map==null){
            map=new HashMap<>();
        }
        baseApils.postRequest(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpCallBack));
    }

    private Observer getObserver(final HttpCallBack callBack) {
        Observer observer=new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(callBack!=null){
                    callBack.onFail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    if(callBack!=null){
                        callBack.onSuccess(result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(callBack!=null){
                        callBack.onFail(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }

    //接口传值
    public HttpCallBack httpCallBack;

    public void setOnHttpCallBack(HttpCallBack callBack){
        this.httpCallBack=callBack;
    }

    public interface HttpCallBack{
        void onSuccess(String data);//成功
        void onFail(String error);//失败
    }
}
