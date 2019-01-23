package com.bw.movie.mvp.model;

import com.bw.movie.mvp.utils.MCallBack;

import java.util.Map;

public interface IModel {
    void requestDataGet(String url, Class clazz, MCallBack mCallBack);
    void requestDataPost(String url, Map<String,String> map, Class clazz,MCallBack mCallBack);
}
