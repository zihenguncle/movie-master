package com.bw.movie.mvp.utils;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApils {
    //get请求
    @GET
    Observable<ResponseBody> getRequest(@Url String url);

    //post请求
    @POST
    Observable<ResponseBody> postRequest(@Url String url, @QueryMap Map<String,String> map);
}
