package com.bw.movie.mvp.model;

import android.widget.Toast;

import com.bw.movie.application.MyApplication;
import com.bw.movie.mvp.utils.MCallBack;
import com.bw.movie.mvp.utils.RetrofitManger;
import com.bw.movie.tools.NetWorkUtils;
import com.bw.movie.tools.ToastUtils;
import com.google.gson.Gson;

import java.util.Map;

public class IModelmpl implements IModel {
    @Override
    public void requestDataGet(String url, final Class clazz, final MCallBack mCallBack) {
        if(!NetWorkUtils.hasNetwork(MyApplication.getApplication() )){
            ToastUtils.toast("当前网络不可用");
        }else {
             RetrofitManger.getInstance().getRequest(url, new RetrofitManger.HttpCallBack() {
                 @Override
                 public void onSuccess(String data) {
                     try {
                         Gson gson=new Gson();
                         Object o = gson.fromJson(data, clazz);
                         if(mCallBack!=null){
                             mCallBack.successData(o);
                         }
                     }catch (Exception e){
                         e.printStackTrace();
                         if(mCallBack!=null){
                            mCallBack.failData(e.getMessage());
                         }
                     }
                 }

                 @Override
                 public void onFail(String error) {
                     if(mCallBack!=null){
                        mCallBack.failData(error);
                     }
                 }
             });
        }
    }

    @Override
    public void requestDataPost(String url, Map<String, String> map, final Class clazz, final MCallBack mCallBack) {
        if(!NetWorkUtils.hasNetwork(MyApplication.getApplication() )){
            ToastUtils.toast("当前网络不可用");
        }else{
            RetrofitManger.getInstance().postRequest(url, map, new RetrofitManger.HttpCallBack() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Gson gson=new Gson();
                        Object o = gson.fromJson(data, clazz);
                        if(mCallBack!=null){
                            mCallBack.successData(o);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        if(mCallBack!=null){
                            mCallBack.failData(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFail(String error) {
                    if(mCallBack!=null){
                        mCallBack.failData(error);
                    }
                }
            });
        }
    }

    @Override
    public void postFiles(String dataUrl, Map<String, String> params, final Class clazz, final MCallBack mCallBack) {
        if(!NetWorkUtils.hasNetwork(MyApplication.getApplication() )){
            ToastUtils.toast("当前网络不可用");
        }else{
            RetrofitManger.getInstance().upLoadFile(dataUrl, params, new RetrofitManger.HttpCallBack() {
                @Override
                public void onSuccess(String data) {
                    try {
                        Gson gson=new Gson();
                        Object o = gson.fromJson(data, clazz);
                        if(mCallBack!=null){
                            mCallBack.successData(o);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        if(mCallBack!=null){
                            mCallBack.failData(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFail(String error) {
                    if(mCallBack!=null){
                        mCallBack.failData(error);
                    }
                }
            });
        }
    }
}
