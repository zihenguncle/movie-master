package com.bw.movie.login_success.nearby_cinema_fragment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.adapter.HomeBannerAdapter;
import com.bw.movie.login_success.nearby_cinema_fragment.adapter.MovieBannerAdapter;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.MovieImageBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.RecommentBean;
import com.bw.movie.mvp.utils.Apis;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class CinemaDtailActivity extends BaseActivity {
    private RecommentBean.ResultBean cinemaInfo;
    @BindView(R.id.focus_image)
    ImageView imageView;
    @BindView(R.id.focus_name)
    TextView textView_name;
    @BindView(R.id.focus_address)
    TextView textView_address;
    @BindView(R.id.focus_position)
    ImageView imageView_position;
    @BindView(R.id.rcf_cinema_flow)
    RecyclerCoverFlow recyclerCoverFlow;
    MovieBannerAdapter movieBannerAdapter;
    int flag;
    @BindView(R.id.banner_tag)
    RadioGroup radiogroup;
    @BindView(R.id.shedule_recycler)
    RecyclerView recyclerView;
    private List<MovieImageBean.ResultBean> result;


    @Override
    protected void initView(Bundle savedInstanceState) {
        //绑定控件
        ButterKnife.bind(this);

        //接收到影院详情信息
        Intent intent = getIntent();
        cinemaInfo = (RecommentBean.ResultBean) intent.getSerializableExtra("cinemaInfo");
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_focus_cinema;
    }

    @Override
    protected void initData() {
        //设置值
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(this).load(cinemaInfo.getLogo()).into(imageView);
        textView_name.setText(cinemaInfo.getName());
        textView_address.setText(cinemaInfo.getAddress());

        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);

        startRequestGet(String.format(Apis.URL_MOVIE_AT_TIME,cinemaInfo.getId()), MovieImageBean.class);

        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {//滑动监听
            @Override
            public void onItemSelected(int position) {
                flag = position;
                setCuur(flag);
                int id = result.get(position).getId();

            }
        });




    }

    private void setCuur(int flag) {
        switch (flag){
            case 0:
                radiogroup.check(R.id.but_one);
                break;
            case 1:
                radiogroup.check(R.id.but_two);
                break;
            case 2:
                radiogroup.check(R.id.but_three);
                break;
            case 3:
                radiogroup.check(R.id.but_four);
                break;
            case 4:
                radiogroup.check(R.id.but_five);
                break;
            case 5:
                radiogroup.check(R.id.but_sex);
                break;
            case 6:
                radiogroup.check(R.id.but_seveen);
                break;
            default:
                break;
        }
    }

    @Override
    protected void successed(Object data) {
           if(data instanceof MovieImageBean){
               MovieImageBean bean= (MovieImageBean) data;
               result = bean.getResult();
               movieBannerAdapter=new MovieBannerAdapter(result,this);
               recyclerCoverFlow.setAdapter(movieBannerAdapter);

           }
    }

    @Override
    protected void failed(String error) {

    }
}
