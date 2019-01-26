package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.bean.DetailsBean;
import com.bw.movie.mvp.utils.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity {


    private int shop_id;

    @BindView(R.id.details_image)
    ImageView details_image;
    @BindView(R.id.details_image_boss)
    ImageView details_iamge_boss;
    @BindView(R.id.details_name)
    TextView name;

    private PopupWindow popupWindow;
    // 声明平移动画
    private TranslateAnimation animation;
    private ImageView p_image;
    private ImageView p_back;
    private TextView p_movieTypes;
    private TextView p_director;
    private TextView p_time;
    private TextView p_address;

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
        //获取商品id
        Intent intent = getIntent();
        shop_id = intent.getIntExtra("id", 0);
        startRequestGet(String.format(Apis.URL_MOVE_DATEILS,shop_id),DetailsBean.class);
        details_iamge_boss.setAlpha(0.4f);

        //详情的popup
        final View popupView = View.inflate(DetailsActivity.this,R.layout.popup_details,null);
        p_image = popupView.findViewById(R.id.popup_details_iamge);
        p_back = popupView.findViewById(R.id.popup_image_back);
        p_movieTypes = popupView.findViewById(R.id.details_movieTypes);
        p_director = popupView.findViewById(R.id.popup_details_director);
        p_time = popupView.findViewById(R.id.details_time);
        p_address = popupView.findViewById(R.id.details_address);
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        p_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(1000);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupView.startAnimation(animation);

    }

    @OnClick({R.id.details_back,R.id.details_details_button,R.id.details_notice_button,R.id.details_stage_button,R.id.details_video_button})
    public void onclick(View view){
        switch (view.getId()){
            //点击返回键
            case R.id.details_back:
                finish();
                break;
                //详情
            case R.id.details_details_button:
                popupWindow.showAtLocation(View.inflate(DetailsActivity.this, R.layout.popup_details, null),
                        Gravity.BOTTOM, 0, 0);
                break;
                default:
                    break;
        }
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof DetailsBean){
            String imageUrl = ((DetailsBean) data).getResult().getImageUrl();
            Glide.with(this).load(imageUrl).into(details_image);
            Glide.with(this).load(imageUrl).into(details_iamge_boss);
            details_iamge_boss.setScaleType(ImageView.ScaleType.FIT_XY);
            name.setText(((DetailsBean) data).getResult().getName());
            Glide.with(this).load(imageUrl).into(p_image);
            p_movieTypes.setText(((DetailsBean) data).getResult().getMovieTypes());
            p_director.setText(((DetailsBean) data).getResult().getDirector());
            p_address.setText(((DetailsBean) data).getResult().getPlaceOrigin());
            p_time.setText(((DetailsBean) data).getResult().getDuration());
        }
    }

    

    @Override
    protected void failed(String error) {

    }
}
