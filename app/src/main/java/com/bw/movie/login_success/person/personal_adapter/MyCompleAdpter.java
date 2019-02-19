package com.bw.movie.login_success.person.personal_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.login_success.person.personal_bean.TicketInformationBeanTwo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCompleAdpter extends RecyclerView.Adapter<MyCompleAdpter.ViewHolder> {
    private List<TicketInformationBeanTwo.ResultBean> mList;
    private Context mContext;
    public MyCompleAdpter(Context mContext)
    {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<TicketInformationBeanTwo.ResultBean> list)
    {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<TicketInformationBeanTwo.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.ticket_ok,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.ticketOkName.setText(mList.get(i).getMovieName());
        String startTime = mList.get(i).getBeginTime();
        viewHolder.ticketOkStarttime.setText(startTime+" -");
        String endTime = mList.get(i).getEndTime();
        viewHolder.ticketOkEndtime.setText(" "+endTime);
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(mList.get(i).getCreateTime());
        viewHolder.ticketOkOrdertime.setText(date);
        viewHolder.ticketOkOrder.setText(mList.get(i).getOrderId());
        viewHolder.ticketOkMovie.setText(mList.get(i).getCinemaName());
        viewHolder.ticketOkFilm.setText(mList.get(i).getScreeningHall());
        viewHolder.ticketOkNum.setText(mList.get(i).getAmount() + "张");
        viewHolder.ticketOkPrice.setText(mList.get(i).getPrice() + "元");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            //获取毫秒数
            Long startLong = start.getTime();
            Long endLong = end.getTime();
            Long ms = endLong-startLong;
            //时间差转换为 \天\时\分\秒
            String time = longTimeToDay(ms);
            viewHolder.ticketOkTime.setText(time+"");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    //转换函数，可以封装成公用方法
    public static String longTimeToDay(Long ms){
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }

        return sb.toString();
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ticket_ok_name)
        TextView ticketOkName;
        @BindView(R.id.ticket_ok_starttime)
        TextView ticketOkStarttime;
        @BindView(R.id.ticket_ok_endtime)
        TextView ticketOkEndtime;
        @BindView(R.id.ticket_ok_order)
        TextView ticketOkOrder;
        @BindView(R.id.ticket_ok_ordertime)
        TextView ticketOkOrdertime;
        @BindView(R.id.ticket_ok_movie)
        TextView ticketOkMovie;
        @BindView(R.id.ticket_ok_film)
        TextView ticketOkFilm;
        @BindView(R.id.ticket_ok_time)
        TextView ticketOkTime;
        @BindView(R.id.ticket_ok_num)
        TextView ticketOkNum;
        @BindView(R.id.ticket_ok_price)
        TextView ticketOkPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
