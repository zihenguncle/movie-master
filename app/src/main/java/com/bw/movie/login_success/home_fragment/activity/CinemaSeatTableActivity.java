package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.bean.CinemaSeatTableDetailBean;
import com.bw.movie.login_success.home_fragment.seattable.SeatTable;
import com.bw.movie.tools.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 郭淄恒
 * Date: 2019/2/13 10:33
 * Description: 选座
 */
public class CinemaSeatTableActivity extends BaseActivity {

    @BindView(R.id.cinema_seat_table_text_beginTime)
    TextView mTextView_beginTime;

    @BindView(R.id.cinema_seat_table_text_endTime)
    TextView mTextView_endTime;

    @BindView(R.id.cinema_seat_table_text_hall)
    TextView mTextView_hall;

    @BindView(R.id.item_cinema_seat_text_price)
    TextView mTextView_price;

    @BindView(R.id.item_cinema_detail_img_v)
    ImageView mImageView_v;

    public SeatTable seatTableView;
    @BindView(R.id.item_cinema_detail_img_x)
    ImageView mImageView_x;
    private int mSeatsTotal;
    double totalPrice = 0;

    @BindView(R.id.seat_table)
    TextView seat_table;
    @BindView(R.id.seat_address)
    TextView seat_address;
    @BindView(R.id.movie_name)
    TextView movie_name;
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getViewById() {
        return R.layout.activity_cinema_seat_table;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        seat_table.setText(name);
        String address = intent.getStringExtra("address");
        seat_address.setText(address);
        String movieName = intent.getStringExtra("MovieName");
        movie_name.setText(movieName+" (国语3D)");
        String start = intent.getStringExtra("start");
        mTextView_beginTime.setText(start);
        String end = intent.getStringExtra("end");
        mTextView_endTime.setText(end);
        String num = intent.getStringExtra("num");
        mTextView_hall.setText(num);

        //票的单价
        final double price = intent.getDoubleExtra("price", 0.0);
        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName(num);//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                totalPrice += price;
                mTextView_price.setText(totalPrice + "");
            }

            @Override
            public void unCheck(int row, int column) {
                totalPrice -= price;
                mTextView_price.setText(totalPrice + "");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);

    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {

    }

    @OnClick(R.id.item_cinema_detail_img_x)
    public void onImgXClickListener() {
        finish();
    }

    @OnClick(R.id.item_cinema_detail_img_v)
    public void onImgVClickListener() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sjx(CinemaSeatTableDetailBean detailsBean) {
        mTextView_beginTime.setText(detailsBean.getBeginTime());
        mTextView_endTime.setText(detailsBean.getEndTime());
        mTextView_hall.setText(detailsBean.getHall());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
