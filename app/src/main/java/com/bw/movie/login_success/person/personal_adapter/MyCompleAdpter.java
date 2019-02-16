package com.bw.movie.login_success.person.personal_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.login_success.person.personal_bean.TicketInformationBeanTwo;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        /*String startTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(mList.get(i).getBeginTime());
        viewHolder.ticketOkStarttime.setText(startTime+" -");
        String endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(mList.get(i).getEndTime());
        viewHolder.ticketOkEndtime.setText(" "+endTime);
        viewHolder.ticketOkOrder.setText(mList.get(i).getScreeningHall());
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(mList.get(i).getCreateTime());
        viewHolder.ticketOkOrdertime.setText(date);*/
        viewHolder.ticketOkMovie.setText(mList.get(i).getCinemaName());
        viewHolder.ticketOkFilm.setText(mList.get(i).getScreeningHall());
        viewHolder.ticketOkNum.setText(mList.get(i).getAmount() + "张");
        viewHolder.ticketOkPrice.setText(mList.get(i).getPrice() + "元");
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
