package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.adapter.Popup_image;
import com.bw.movie.login_success.home_fragment.adapter.Popup_name;
import com.bw.movie.login_success.home_fragment.adapter.Popup_take;
import com.bw.movie.login_success.home_fragment.adapter.Popup_video;
import com.bw.movie.login_success.home_fragment.bean.DetailsBean;
import com.bw.movie.login_success.home_fragment.bean.TakeBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.FollowBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.PATCH;

public class DetailsActivity extends BaseActivity {


    private int shop_id;

    @BindView(R.id.details_image)
    ImageView details_image;
    @BindView(R.id.details_image_boss)
    ImageView details_iamge_boss;
    @BindView(R.id.details_name)
    TextView name;

    @BindView(R.id.details_image_love)
    ImageView love_image;
    private PopupWindow popupWindow_nocie;
    // 声明平移动画
    private TranslateAnimation animation;
    private ImageView p_image;
    private ImageView p_back;
    private TextView p_movieTypes;
    private TextView p_director;
    private TextView p_time;
    private TextView p_address;
    private DetailsBean databean;
    private RecyclerView recyclerView;
    private XRecyclerView p_xrecyclerView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_details;
    }

    @Override
    protected void initData() {
        //获取影视id
        Intent intent = getIntent();
        shop_id = intent.getIntExtra("id", 0);
        startRequestGet(String.format(Apis.URL_MOVE_DATEILS,shop_id),DetailsBean.class);
        details_iamge_boss.setAlpha(0.4f);

    }

    @OnClick({R.id.details_back,R.id.details_gotopay,R.id.details_image_love,R.id.details_details_button,R.id.details_notice_button,R.id.details_stage_button,R.id.details_take_button})
    public void onclick(View view){
            switch (view.getId()){
            //点击返回键
            case R.id.details_back:
                finish();
                break;
                //详情
            case R.id.details_details_button:
                View popup = getPopup(R.layout.popup_details);
                getPopupView(popup);
                break;
                case R.id.details_notice_button:
                    View popup1 = getPopup(R.layout.popup_notice);
                    getNoticeView(popup1);
                    break;
                case R.id.details_stage_button:
                    View popup2 = getPopup(R.layout.popup_stage);
                    getStage(popup2);
                    break;
                case R.id.details_take_button:
                    View popup3 = getPopup(R.layout.popup_take);
                    getTake(popup3);
                    break;
                case R.id.details_gotopay:
                    Intent intent = new Intent(this, BuyTicketActivity.class);
                    intent.putExtra("movieId",shop_id);
                    startActivity(intent);
                    break;
                case R.id.details_image_love:
                    if(databean.getResult().getFollowMovie()==1){
                        startRequestGet(String.format(Apis.URLNOLOVEMOVIE,shop_id),FollowBean.class);
                        love_image.setImageResource(R.mipmap.com_icon_collection_default);
                        databean.getResult().setFollowMovie(2);
                        startRequestGet(String.format(Apis.URL_MOVE_DATEILS,shop_id),DetailsBean.class);
                    }else {
                        startRequestGet(String.format(Apis.URL_LOVEMOVIE,shop_id),FollowBean.class);
                        love_image.setImageResource(R.mipmap.com_icon_collection_selected);
                        databean.getResult().setFollowMovie(1);
                        startRequestGet(String.format(Apis.URL_MOVE_DATEILS,shop_id),DetailsBean.class);
                    }
                default:
                    break;
        }
    }


    int page;
    Popup_take popup_take;

    private void getTake(View popup3) {
        page = 1;
        p_xrecyclerView = popup3.findViewById(R.id.popup_take_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        p_xrecyclerView.setLayoutManager(linearLayoutManager);
        p_xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getTakeList();
            }

            @Override
            public void onLoadMore() {
                getTakeList();
            }
        });
        getTakeList();
        popup_take = new Popup_take(this,shop_id);
        p_xrecyclerView.setAdapter(popup_take);
        ImageView back = popup3.findViewById(R.id.popup_image_take_back);
        final ImageView take_back = popup3.findViewById(R.id.item_take_back);
        popup_take.setXrecycleData(new Popup_take.getXrecycleData() {
            @Override
            public void getdata() {
                take_back.setVisibility(View.VISIBLE);
            }
        });
        //点击取消查看回复
        take_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_take.notifyDataSetChanged();
                take_back.setVisibility(View.GONE);
            }
        });
        //点击popup取消
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_nocie.dismiss();
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
            }
        });
    }

    private void getTakeList() {
        startRequestGet(String.format(Apis.URL_SELECT_TAKE,shop_id,page,5),TakeBean.class);
    }


    //
    private void getStage(View popup2) {
        RecyclerView recyclerView_stage = popup2.findViewById(R.id.popup_stage_recycle);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView_stage.setLayoutManager(layoutManager);
        ImageView back = popup2.findViewById(R.id.popup_image_stage_back);
        List<String> posterList = databean.getResult().getPosterList();
        Popup_image popup_image = new Popup_image(posterList,this);
        recyclerView_stage.setAdapter(popup_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_nocie.dismiss();
            }
        });
    }

    //预告的获取id
    private void getNoticeView(View popupView) {
        recyclerView = popupView.findViewById(R.id.popup_notice);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        Popup_video adapter_video = new Popup_video(databean.getResult().getShortFilmList(),this);
        recyclerView.setAdapter(adapter_video);
        ImageView back = popupView.findViewById(R.id.popup_image_noice_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_nocie.dismiss();
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
            }
        });
    }

    //详情的获取id
    private void getPopupView(View popupView) {
        p_image = popupView.findViewById(R.id.popup_details_iamge);
        p_back = popupView.findViewById(R.id.popup_image_back);
        p_movieTypes = popupView.findViewById(R.id.details_movieTypes);
        p_director = popupView.findViewById(R.id.popup_details_director);
        p_time = popupView.findViewById(R.id.details_time);
        p_address = popupView.findViewById(R.id.details_address);
        TextView p_intro = popupView.findViewById(R.id.popup_text_intro);
        RecyclerView p_listview = popupView.findViewById(R.id.popup_details_listview);
        p_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_nocie.dismiss();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        p_listview.setLayoutManager(linearLayoutManager);
        String starring = databean.getResult().getStarring();
        String[] split = starring.split("\\,");
        Popup_name popup_name = new Popup_name(split,this);
        p_listview.setAdapter(popup_name);
        String imageUrl = (databean.getResult().getImageUrl());
        Glide.with(this).load(imageUrl).into(p_image);
        p_movieTypes.setText(databean.getResult().getMovieTypes());
        p_director.setText(databean.getResult().getDirector());
        p_address.setText(databean.getResult().getPlaceOrigin());
        p_time.setText(databean.getResult().getDuration());
        p_intro.setText(databean.getResult().getSummary());

    }

    //弹出popup
    private View getPopup(int popup_view) {
        View popupView = View.inflate(DetailsActivity.this,popup_view,null);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(500);
        popupWindow_nocie = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow_nocie.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_nocie.setFocusable(true);
        popupWindow_nocie.setOutsideTouchable(true);
        popupWindow_nocie.showAtLocation(View.inflate(DetailsActivity.this, popup_view, null),
                Gravity.BOTTOM, 0, 0);
        popupView.startAnimation(animation);
        return popupView;
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof DetailsBean){
            databean = (DetailsBean) data;
            String imageUrl = (databean.getResult().getImageUrl());
            details_iamge_boss.setScaleType(ImageView.ScaleType.FIT_XY);
            name.setText(databean.getResult().getName());
            Glide.with(this).load(imageUrl).into(details_image);
            Glide.with(this).load(imageUrl).into(details_iamge_boss);
            if(databean.getResult().getFollowMovie()==1){
                love_image.setImageResource(R.mipmap.com_icon_collection_selected);
            }else {
                love_image.setImageResource(R.mipmap.com_icon_collection_default);
            }
        }
        if(data instanceof TakeBean){
            if(page == 1){
                popup_take.setData(((TakeBean) data).getResult());
            }else {
                popup_take.addData(((TakeBean) data).getResult());
            }
            page++;
            p_xrecyclerView.loadMoreComplete();
            p_xrecyclerView.refreshComplete();
        }
        if(data instanceof FollowBean){
            ToastUtils.toast(((FollowBean) data).getMessage());
        }
    }

    

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(popup_take != null){
            popup_take.detach();
        }
    }
}
