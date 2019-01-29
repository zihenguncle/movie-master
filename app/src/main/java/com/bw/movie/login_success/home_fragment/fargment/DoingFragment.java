package com.bw.movie.login_success.home_fragment.fargment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.home_fragment.adapter.ShowDetailsAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoingFragment extends BaseFragment implements View.OnClickListener {

    private int mPage;
    private static final int TYPE_COUNT = 10;
    @BindView(R.id.doing_fragment_recycle)
    XRecyclerView doing_fragment_recycle;
    private ShowDetailsAdapter detailsAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getViewById() {
        return R.layout.dong_fargment;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        doing_fragment_recycle.setLayoutManager(layoutManager);

        detailsAdapter = new ShowDetailsAdapter(getContext());
        doing_fragment_recycle.setAdapter(detailsAdapter);

        doing_fragment_recycle.setPullRefreshEnabled(true);
        doing_fragment_recycle.setLoadingMoreEnabled(true);
        doing_fragment_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadDataDoing();
            }

            @Override
            public void onLoadMore() {
                loadDataDoing();
            }
        });
        loadDataDoing();

    }

    //正在热映
    private void loadDataDoing() {
        startRequestGet(String.format(Apis.URL_DOINGMOVIE, mPage, TYPE_COUNT), HomeBannerBean.class);
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
                detailsAdapter.setDatas(homeBannerBean.getResult());
                }else{
                    detailsAdapter.addDatas(homeBannerBean.getResult());
                }
                mPage++;
                doing_fragment_recycle.refreshComplete();
                doing_fragment_recycle.loadMoreComplete();
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
