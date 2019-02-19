package com.bw.movie.login_success.person.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.person.personal_adapter.MyCompleAdpter;
import com.bw.movie.login_success.person.personal_bean.TicketInformationBeanTwo;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompleteFragment extends BaseFragment {
    @BindView(R.id.my_com_recycle)
    XRecyclerView complete_recyclerView;
    private int cpage;
    MyCompleAdpter compleAdpter;
    @Override
    protected int getViewById() {
        return R.layout.complete_tickets;
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        complete_recyclerView.setLayoutManager(linearLayoutManager);
        compleAdpter = new MyCompleAdpter(getActivity());
        cpage=1;
        complete_recyclerView.setAdapter(compleAdpter);
        complete_recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                cpage=1;

                getComData();
            }
            @Override
            public void onLoadMore() {
                getComData();

            }
        });
        getComData();
    }
    //已完成请求数据
    public void getComData(){
        startRequestGet(String.format(Apis.URL_TICKET_RECORD,cpage,10,2),TicketInformationBeanTwo.class);
    }
    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void successed(Object data) {
        if (data instanceof TicketInformationBeanTwo){
            TicketInformationBeanTwo ticketRecrodBean1 = (TicketInformationBeanTwo) data;
            if (cpage==1){
                compleAdpter.setmList(ticketRecrodBean1.getResult());
            }
            else {
                compleAdpter.addmList(ticketRecrodBean1.getResult());
            }
            cpage++;
            complete_recyclerView.refreshComplete();
            complete_recyclerView.loadMoreComplete();
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}
