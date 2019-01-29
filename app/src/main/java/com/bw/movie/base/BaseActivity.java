package com.bw.movie.base;

import android.Manifest;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.loading.LoadingUtils;
import com.bw.movie.mvp.presenter.IPresemterImpl;
import com.bw.movie.mvp.view.IView;

import java.util.Map;

public abstract class BaseActivity extends AppCompatActivity implements IView{

    IPresemterImpl iPresemter;

    private Dialog loadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewById());
        initView(savedInstanceState);
        iPresemter = new IPresemterImpl(this);
        initData();
        stateNetWork();

    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getViewById();

    protected abstract void initData();

    protected abstract void successed(Object data);
    protected abstract void failed(String error);

    protected void startRequestGet(String url,Class clazz){
       if(loadingDialog == null){
            loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
        }
        iPresemter.startRequestGet(url,clazz);
    }

    protected void startRequestPost(String url, Map<String,String> map,Class clazz){
       if(loadingDialog == null) {
            loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
        }
        iPresemter.startRequestPost(url,map,clazz);
    }

    protected void startRequestFilesPost(String url, Map<String,String> map,Class clazz){
        if(loadingDialog == null) {
            loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
        }
        iPresemter.postFiles(url,map,clazz);
    }


    @Override
    public void onSuccessed(Object data) {
        LoadingUtils.closeDialog(loadingDialog);
        successed(data);
    }

    @Override
    public void onFailed(String error) {
        LoadingUtils.closeDialog(loadingDialog);
        failed(error);
    }

    //动态注册权限
    private void stateNetWork() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            String[] mStatenetwork = new String[]{
                    //写的权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    //读的权限
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    //入网权限
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    //WIFI权限
                    Manifest.permission.ACCESS_WIFI_STATE,
                    //读手机权限
                    Manifest.permission.READ_PHONE_STATE,
                    //网络权限
                    Manifest.permission.INTERNET,
            };
            ActivityCompat.requestPermissions(this,mStatenetwork,100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission  = false;
        if(requestCode == 100){
            for (int i = 0;i<grantResults.length;i++){
                if(grantResults[i] == -1){
                    hasPermission = true;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(iPresemter != null){
            iPresemter.detachView();
        }
    }
}
