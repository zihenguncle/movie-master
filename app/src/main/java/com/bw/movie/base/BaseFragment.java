package com.bw.movie.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.mvp.presenter.IPresemterImpl;
import com.bw.movie.mvp.view.IView;

import java.util.Map;

public abstract class BaseFragment extends Fragment implements IView {

    IPresemterImpl iPresemter;
    private Dialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getViewById(),container,false);
    }

    protected abstract int getViewById();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresemter = new IPresemterImpl(this);
        initView(view);
        initData();

    }

    protected abstract void initData();

    protected abstract void initView(View view);


    protected abstract void successed(Object data);
    protected abstract void failed(String error);

    protected void startRequestGet(String url,Class clazz){
        //loadingDialog = LoadingUtils.createLoadingDialog(getActivity(), "加载中.....");
        iPresemter.startRequestGet(url,clazz);
    }

    protected void startRequestPost(String url, Map<String,String> map, Class clazz){
        //loadingDialog = LoadingUtils.createLoadingDialog(getActivity(), "加载中.....");
        iPresemter.startRequestPost(url,map,clazz);
    }

    @Override
    public void onSuccessed(Object data) {
        //LoadingUtils.closeDialog(loadingDialog);
        successed(data);
    }

    @Override
    public void onFailed(String error) {
        //LoadingUtils.closeDialog(loadingDialog);
        failed(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(iPresemter != null){
            iPresemter.detachView();
        }
    }
}
