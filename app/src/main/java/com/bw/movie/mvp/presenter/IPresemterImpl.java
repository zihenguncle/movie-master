package com.bw.movie.mvp.presenter;

import com.bw.movie.mvp.model.IModel;
import com.bw.movie.mvp.model.IModelmpl;
import com.bw.movie.mvp.utils.MCallBack;
import com.bw.movie.mvp.view.IView;

import java.util.Map;

public class IPresemterImpl implements IPresenter{
    private IModelmpl iModelmpl;
    private IView iView;

    public IPresemterImpl(IView iView) {
        this.iView = iView;
        iModelmpl=new IModelmpl();
    }

    @Override
    public void startRequestGet(String url, Class clazz) {
        iModelmpl.requestDataGet(url, clazz, new MCallBack() {
            @Override
            public void successData(Object data) {
                iView.onSuccessed(data);
            }

            @Override
            public void failData(String error) {
              iView.onFailed(error);
            }
        });
    }

    @Override
    public void startRequestPost(String url, Map<String, String> map, Class clazz) {
        iModelmpl.requestDataPost(url, map, clazz, new MCallBack() {
            @Override
            public void successData(Object data) {
                iView.onSuccessed(data);
            }

            @Override
            public void failData(String error) {
              iView.onFailed(error);
            }
        });
    }

    @Override
    public void postFiles(String dataUrl, Map<String, String> params, Class clazz) {
        iModelmpl.postFiles(dataUrl, params, clazz, new MCallBack() {
            @Override
            public void successData(Object data) {
                iView.onSuccessed(data);
            }

            @Override
            public void failData(String error) {
                iView.onFailed(error);
            }
        });
    }

    //解除绑定
    public void detachView(){
        if(iModelmpl!=null){
            iModelmpl=null;
        }
        if(iView!=null){
            iView=null;
        }
    }
}
