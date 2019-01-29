package com.bw.movie.login_success.home_fragment.fargment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.home_fragment.adapter.ShowDetailsAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.todo_fragment_recycle)
    XRecyclerView todo_fragment;
    private int mPage;
    private static final int TYPE_COUNT = 10;
    private ShowDetailsAdapter showDetailsAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getViewById() {
        return R.layout.todo_fargment;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        todo_fragment.setLayoutManager(layoutManager);

        showDetailsAdapter = new ShowDetailsAdapter(getContext());
        todo_fragment.setAdapter(showDetailsAdapter);

        todo_fragment.setPullRefreshEnabled(true);
        todo_fragment.setLoadingMoreEnabled(true);
        todo_fragment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadDataTodo();
            }

            @Override
            public void onLoadMore() {
                loadDataTodo();
            }
        });
        loadDataTodo();
    }

    //即将上映
    private void loadDataTodo() {
        startRequestGet(String.format(Apis.URL_BANNER, mPage, TYPE_COUNT), HomeBannerBean.class);
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof HomeBannerBean){
            HomeBannerBean homeBannerBean= (HomeBannerBean) data;
            if(homeBannerBean.getStatus().equals("0000")){
                if(mPage==1){
                    showDetailsAdapter.setDatas(homeBannerBean.getResult());
                }else{
                    showDetailsAdapter.addDatas(homeBannerBean.getResult());
                }
                mPage++;
                todo_fragment.refreshComplete();
                todo_fragment.loadMoreComplete();
            }else{
                ToastUtils.toast(homeBannerBean.getMessage());
            }

        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}
