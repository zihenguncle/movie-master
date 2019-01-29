package com.bw.movie.mvp.utils;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApils {
    //get请求
    @GET
    Observable<ResponseBody> getRequest(@Url String url);

    //post请求
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postRequest(@Url String url, @FieldMap Map<String,String> map);

    @POST
    Observable<ResponseBody> uploadFile(@Url String url,@Body MultipartBody body);

}
