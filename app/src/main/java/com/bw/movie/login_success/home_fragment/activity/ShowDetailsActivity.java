package com.bw.movie.login_success.home_fragment.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.fargment.DoingFragment;
import com.bw.movie.login_success.home_fragment.fargment.HotFragment;
import com.bw.movie.login_success.home_fragment.fargment.TodoFragment;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.tools.ToastUtils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.show_details_viewpager)
    ViewPager show_details_viewpager;
    @BindView(R.id.show_details_hot)
    RadioButton showDetailsHot;
    @BindView(R.id.show_deltails_doing)
    RadioButton showDeltailsDoing;
    @BindView(R.id.show_details_address)
    TextView addresss;
    @BindView(R.id.show_deltails_todo)
    RadioButton showDeltailsTodo;
    @BindView(R.id.show_details_radio)
    RadioGroup show_details_radio;
    private List<Fragment> list;
    @BindView(R.id.show_details_home_search)
    ImageView image_search;
    @BindView(R.id.show_details_relative_search)
    RelativeLayout relative_search;
    @BindView(R.id.show_details_edit_search)
    EditText edit_search;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String cityCode;
    private String province;
    private String city;
    private double longitude;
    private double latitude;

    @OnClick({R.id.show_details_hot,R.id.image_View_Back,R.id.show_details_image, R.id.show_deltails_doing, R.id.show_deltails_todo,R.id.show_details_home_search,R.id.show_details_text_search})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_View_Back:
                finish();
                break;
            case R.id.show_details_image:
                getLocation();
                break;
            case R.id.show_details_hot:
                show_details_viewpager.setCurrentItem(0);
                showDetailsHot.setChecked(true);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(false);
                break;
            case R.id.show_deltails_doing:
                show_details_viewpager.setCurrentItem(1);
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(true);
                showDeltailsTodo.setChecked(false);
                break;
            case R.id.show_deltails_todo:
                show_details_viewpager.setCurrentItem(2);
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(true);
                break;
            case R.id.show_details_home_search:
                getSearch();
                break;
            case R.id.show_details_text_search:
                gotoSearch();
                break;
            default:
                break;
        }
    }

    private void startLocation() {
        //开始定位，这里模拟一下定位
        mlocationClient = new AMapLocationClient(ShowDetailsActivity.this);
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null){
                    if(aMapLocation.getErrorCode() == 0){
                        //获取纬度
                        latitude = aMapLocation.getLatitude();
                        //获取经度
                        longitude = aMapLocation.getLongitude();
                        //城市信息
                        city = aMapLocation.getCity();
                        //省信息
                        province = aMapLocation.getProvince();
                        //城市编码
                        cityCode = aMapLocation.getCityCode();
                        //地区编码
                        String adCode = aMapLocation.getAdCode();
                        CityPicker.from(ShowDetailsActivity.this).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                        addresss.setText(city);
                        mlocationClient.stopLocation();
                    }
                }
            }
        });
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLoaction(MessageList message) {
        if (message.getFlag().equals("location1")){
            String location = message.getStr().toString();
            addresss.setText(location);
        }
    }

    private void gotoSearch() {
        if(TextUtils.isEmpty(edit_search.getText().toString())){
            ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", -600, 0);
            translationX.setDuration(500);
            translationX.start();
            image_search.setClickable(true);
        }else {
            ToastUtils.toast(edit_search.getText().toString());
        }
    }

    //点击放大镜弹出搜索框
    private void getSearch() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", 0, -600);
        translationX.setDuration(500);
        translationX.start();
        image_search.setClickable(false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected int getViewById() {
        return R.layout.show_details_activity;
    }

    @Override
    protected void initData() {
        startLocation();
        list = new ArrayList<>();
        list.add(new HotFragment());
        list.add(new DoingFragment());
        list.add(new TodoFragment());

        show_details_viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        Intent intent = getIntent();
        String hot = intent.getStringExtra("hot");
        switch (hot){
            case "1":
                show_details_viewpager.setCurrentItem(0);
                showDetailsHot.setChecked(true);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(false);
                break;
            case "2":
                show_details_viewpager.setCurrentItem(1);
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(true);
                showDeltailsTodo.setChecked(false);
                break;
            case "3":
                show_details_viewpager.setCurrentItem(2);
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(true);
                break;
        }


        show_details_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        show_details_radio.check(R.id.show_details_hot);
                        showDetailsHot.setChecked(true);
                        showDeltailsDoing.setChecked(false);
                        showDeltailsTodo.setChecked(false);
                        break;
                    case 1:
                        show_details_radio.check(R.id.show_deltails_doing);
                        showDetailsHot.setChecked(false);
                        showDeltailsDoing.setChecked(true);
                        showDeltailsTodo.setChecked(false);
                        break;
                    case 2:
                        show_details_radio.check(R.id.show_deltails_todo);
                        showDetailsHot.setChecked(false);
                        showDeltailsDoing.setChecked(false);
                        showDeltailsTodo.setChecked(true);
                        break;
                        default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        show_details_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0:
                        showDetailsHot.setChecked(true);
                        showDeltailsDoing.setChecked(false);
                        showDeltailsTodo.setChecked(false);
                        break;
                    case 1:
                        showDetailsHot.setChecked(false);
                        showDeltailsDoing.setChecked(true);
                        showDeltailsTodo.setChecked(false);
                        break;
                    case 2:
                        showDetailsHot.setChecked(false);
                        showDeltailsDoing.setChecked(false);
                        showDeltailsTodo.setChecked(true);
                        break;
                }
            }
        });
}
    private void getLocation() {
        CityPicker.from(ShowDetailsActivity.this) //activity或者fragment
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        addresss.setText(data.getName());
                    }

                    @Override
                    public void onCancel(){

                    }

                    @Override
                    public void onLocate() {
                        //定位接口，需要APP自身实现，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.from(ShowDetailsActivity.this).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                            }
                        }, 3000);
                    }
                })
                .show();
    }
    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

}
