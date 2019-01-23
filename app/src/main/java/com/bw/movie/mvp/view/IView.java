package com.bw.movie.mvp.view;

public interface IView<T> {
    void onSuccessed(T data);
    void onFailed(String error);
}
