package com.bw.movie.login_success.home_fragment.fargment;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.view.animation.Animation;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.home_fragment.adapter.ShowDetailsAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.FollowBean;
import com.bw.movie.login_success.person.personal_bean.PersonalMessageBean;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        detailsAdapter.setOnCallBack(new ShowDetailsAdapter.CallBack() {
            @Override
            public void getInformation(int id, int followMovie, int position) {
                //取消关注
                if(followMovie==1){
                    cancelMovie(id);
                    detailsAdapter.updateChoose(position);
                }else {
                    //关注
                    loveMovie(id);
                    detailsAdapter.updateSelect(position);
                }
            }
        });

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
    private void loveMovie(int id) {
        startRequestGet(String.format(Apis.URL_LOVEMOVIE,id),FollowBean.class);
    }


    private void cancelMovie(int id) {
        startRequestGet(String.format(Apis.URLNOLOVEMOVIE,id), FollowBean.class);
    }
    //正在热映
    private void loadDataDoing() {
        startRequestGet(String.format(Apis.URL_DOINGMOVIE, mPage, TYPE_COUNT), HomeBannerBean.class);
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccess(MessageList message) {
        if (message.getFlag().equals("int")){
            if(message.getStr().equals("1")) {
                mPage = 1;
                startRequestGet(String.format(Apis.URL_DOINGMOVIE, mPage, TYPE_COUNT), HomeBannerBean.class);
            }
        }
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
        if(data instanceof FollowBean){
            FollowBean bean= (FollowBean) data;
            if(bean.getStatus().equals("0000")){
                ToastUtils.toast(bean.getMessage());
                EventBus.getDefault().post(new MessageList("int","1"));

            }else {
                ToastUtils.toast(bean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }


    @SuppressLint("NewApi")
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
