package com.bw.movie.mvp.utils;

public interface MCallBack<T> {
    void successData(T data);  //请求成功
    void failData(String error);  //请求失败
}
