package com.bw.movie.login_success.nearby_cinema_fragment;

import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.nearby_cinema_fragment.adapter.RecommendAdapter;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.FollowBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.RecommentBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * date:2018/1/27
 * author:zhangjing
 * function:推荐影院，附近影院，关注，取消关注
 */

public class NearbyCinemaFragment extends BaseFragment {
    @BindView(R.id.cinema_recyclerView)
    RecyclerView xRecyclerView;
    @BindView(R.id.recommend_cinema)
    RadioButton radioButton_recommend;
    @BindView(R.id.nearby_cinema)
    RadioButton radioButton_nearby;
    @BindView(R.id.home_edit_search)
    EditText edit_search;
    @BindView(R.id.relative_search)
    RelativeLayout relative_search;
    @BindView(R.id.home_search)
    ImageView image_search;
    @BindView(R.id.home_text_search)
    TextView textView;
    private RecommendAdapter recommendAdapter;
    private String cinema_name;

    @Override
    protected int getViewById() {
        return R.layout.fargment_nearbycinema;
    }

    @Override
    protected void initData() {
        //布局管理器
         LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);

        recommendAdapter=new RecommendAdapter(getActivity());
        xRecyclerView.setAdapter(recommendAdapter);

     recommendAdapter.setOnCallBack(new RecommendAdapter.CallBack() {
         @Override
         public void getInformation(int id, int followCinema,int position) {
             if(followCinema==1){
                 //取消关注
                 cancelCollection(id);
                 recommendAdapter.update2(position);

             }else {
                 //关注
                 collection(id);
                 recommendAdapter.update(position);

             }
         }
     });

        getInfoCinema();

    }

    private void collection(int id) {
         startRequestGet(String.format(Apis.URL_FOLLOW_CINEMA,id), FollowBean.class);
    }

    private void cancelCollection(int id) {
         startRequestGet(String.format(Apis.URL_CANCEL_FOLLOW_CINEMA,id),FollowBean.class);
    }

    private void getInfoFindCinema() {

        startRequestGet(String.format(Apis.URL_FIND_CINEMA,1,10,cinema_name),RecommentBean.class);
    }

    private void getInfoNearby() {

        startRequestGet(String.format(Apis.URL_NEARBY_CINEAMS,1,10), RecommentBean.class);
    }

    private void getInfoCinema() {

        startRequestGet(String.format(Apis.URL_RECOMMEND_CINEAMS,1,10), RecommentBean.class);
    }
    //进行搜索，如果输入框有信息进行搜索，否则收起
    private void gotoSearch() {
        if(TextUtils.isEmpty(edit_search.getText().toString())){
            ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", -600, 0);
            translationX.setDuration(500);
            translationX.start();
            image_search.setClickable(true);
        }else {
          //  ToastUtils.toast(edit_search.getText().toString());
            cinema_name = edit_search.getText().toString();
            getInfoFindCinema();
        }
    }

    //搜索框出现
    private void getSearch() {
        if(TextUtils.isEmpty(edit_search.getText().toString())){
            ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", 0, -600);
            translationX.setDuration(500);
            translationX.start();
            image_search.setClickable(false);
        }
    }

    @Override
    protected void initView(View view) {
        //绑定控件
        ButterKnife.bind(this,view);

    }
    @OnClick({R.id.recommend_cinema,R.id.nearby_cinema,R.id.home_text_search,R.id.home_search})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.recommend_cinema:
                recommendCineam();
                radioButton_nearby.setChecked(false);
                break;
            case R.id.nearby_cinema:
                nearbyCinema();
                radioButton_recommend.setChecked(false);
                break;
            case R.id.home_text_search:
                gotoSearch();
                break;
            //点击放大镜弹出搜索框
            case R.id.home_search:
                getSearch();
                break;
        }
    }

    private void nearbyCinema() {
        getInfoNearby();
    }

    private void recommendCineam() {
          getInfoCinema();
    }

    @Override
    protected void successed(Object data) {
           if(data instanceof RecommentBean){
               RecommentBean bean= (RecommentBean) data;
               if(bean.getStatus().equals("0000")){
                   List<RecommentBean.ResultBean> result = bean.getResult();
                   result.remove(result.size()-1);
                 recommendAdapter.setList(result);
               }else {
                   ToastUtils.toast(bean.getMessage());
               }

           }else if(data instanceof FollowBean){
               FollowBean bean= (FollowBean) data;
               if(bean.getStatus().equals("0000")){
                   ToastUtils.toast(bean.getMessage());


               }else {
                   ToastUtils.toast(bean.getMessage());
               }
           }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }


}
