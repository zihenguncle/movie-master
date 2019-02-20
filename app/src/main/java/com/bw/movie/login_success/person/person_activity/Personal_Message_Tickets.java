package com.bw.movie.login_success.person.person_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.person.fragment.CompleteFragment;
import com.bw.movie.login_success.person.fragment.WaitPagFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Personal_Message_Tickets extends AppCompatActivity {

    private List<Fragment> list;
    @BindView(R.id.viewpager_personal_ticket)
    ViewPager viewpager_personal_ticket;
    @BindView(R.id.group_ticket)
    RadioGroup group_ticket;
    @BindView(R.id.my_waitpay_radio)
    RadioButton my_waitpay_radio;
    @BindView(R.id.my_complete_radio)
    RadioButton my_complete_radio;
    @BindView(R.id.image_View_Back)
    ImageView imageView_back;


    //my_waitpay_radio
    //my_complete_radio

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_message_tickets);
         ButterKnife.bind(this);
        list=new ArrayList<>();
        list.add(new WaitPagFragment());
        list.add(new CompleteFragment());
        viewpager_personal_ticket.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        group_ticket.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.my_waitpay_radio:
                        viewpager_personal_ticket.setCurrentItem(0);
                        my_waitpay_radio.setChecked(true);
                        my_complete_radio.setChecked(false);
                        break;
                    case R.id.my_complete_radio:
                        viewpager_personal_ticket.setCurrentItem(1);
                        my_waitpay_radio.setChecked(false);
                        my_complete_radio.setChecked(true);
                        break;
                }
            }
        });
        viewpager_personal_ticket.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        group_ticket.check(R.id.my_waitpay_radio);
                        my_waitpay_radio.setChecked(true);
                        my_complete_radio.setChecked(false);
                        break;
                    case 1:
                        group_ticket.check(R.id.my_complete_radio);
                        my_waitpay_radio.setChecked(false);
                        my_complete_radio.setChecked(true);
                    default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }
}
