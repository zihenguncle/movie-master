package com.bw.movie.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

    }

    protected abstract void initView(Bundle savedInstanceState);


    protected abstract int getViewById();

    protected abstract void initData();

    protected abstract void successed(Object data);
    protected abstract void failed(String error);

    protected void startRequestGet(String url,Class clazz){
   //  LoadingUtils.showLoadDialog(this,"加载中...",true);
        iPresemter.startRequestGet(url,clazz);
    }

    protected void startRequestPost(String url, Map<String,String> map,Class clazz){
       // LoadingUtils.showLoadDialog(this,"加载中...",true);
        iPresemter.startRequestPost(url,map,clazz);
    }

    @Override
    public void onSuccessed(Object data) {
    //  LoadingUtils.closeDialog(loadingDialog);
        successed(data);
    }

    @Override
    public void onFailed(String error) {
       // LoadingUtils.closeDialog(loadingDialog);
        failed(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(iPresemter != null){
            iPresemter.detachView();
        }
    }
}
