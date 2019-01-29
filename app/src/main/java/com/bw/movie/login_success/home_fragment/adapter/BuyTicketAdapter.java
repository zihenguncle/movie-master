package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.bean.BuyTicketBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyTicketAdapter extends RecyclerView.Adapter<BuyTicketAdapter.ViewHolder> {


    private Context mContext;
    private List<BuyTicketBean.ResultBean> mDatas;

    public BuyTicketAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas = new ArrayList<>();
    }

    public void setDatas(List<BuyTicketBean.ResultBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.buy_ticket_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.titleBuyTicket.setText(mDatas.get(i).getName());
        viewHolder.addressBuyTicket.setText(mDatas.get(i).getAddress());
        String logo = mDatas.get(i).getLogo();
        Glide.with(mContext).load(logo).into(viewHolder.imageBuyTicket);
        viewHolder.buyTicketItemInstance.setText(mDatas.get(i).getDistance()+"km");
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_buy_ticket)
        ImageView imageBuyTicket;
        @BindView(R.id.title_buy_ticket)
        TextView titleBuyTicket;
        @BindView(R.id.address_buy_ticket)
        TextView addressBuyTicket;
        @BindView(R.id.buy_ticket_item_instance)
        TextView buyTicketItemInstance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
