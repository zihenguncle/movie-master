package com.bw.movie.mvp.utils;

import android.text.TextUtils;

import com.bw.movie.application.MyApplication;
import com.bw.movie.tools.SharedPreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
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
   // private final String BASE_URL="http://172.17.8.100/movieApi/";
    private final String BASE_URL="http://mobile.bwstudent.com/movieApi/";
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
     /*   HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor interceptor1 = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        client=new OkHttpClient.Builder()
                .writeTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000,TimeUnit.SECONDS)
                .connectTimeout(2000, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到请求
                        Request request = chain.request();
                        //取出登陆时获得的两个id
                        String userId = (String) SharedPreferencesUtils.getParam(MyApplication.getApplication(), "userId", "");
                        String sessionId = (String) SharedPreferencesUtils.getParam(MyApplication.getApplication(), "sessionId", "");

                        //重写构造请求
                        Request.Builder builder = request.newBuilder();
                        //把原来请求的数据原样放进去
                        builder.method(request.method(),request.body());

                        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                            builder.addHeader("userId",userId);
                            builder.addHeader("sessionId",sessionId);
                            builder.addHeader("sk","0110010010000");
                            builder.addHeader("Content-Type","application/x-www-form-urlencoded");
                        }

                        //打包
                        Request request1 = builder.build();

                        return chain.proceed(request1);
                    }
                })

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

    //上传头像
    public void upLoadFile(String url,Map<String,String> params, HttpCallBack httpCallBack) {
        baseApils.uploadFile(url,fileToMultipartBody(params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpCallBack));
    }

    public static MultipartBody fileToMultipartBody(Map<String,String> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(), "tp.png",
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
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
