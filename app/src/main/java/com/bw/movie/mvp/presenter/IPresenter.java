package com.bw.movie.mvp.presenter;

import java.util.Map;

public interface IPresenter {
    void startRequestGet(String url,Class clazz);
    void startRequestPost(String url, Map<String,String> map,Class clazz);
}
