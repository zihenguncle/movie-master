package com.bw.movie.login_success.nearby_cinema_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.ScheduleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<ScheduleBean.ResultBean> list;
    private Context context;

    public ScheduleAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ScheduleBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.schedule_movie,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView_screeningHall.setText(list.get(i).getScreeningHall());
        viewHolder.textView_beginTime.setText(list.get(i).getBeginTime());
        viewHolder.textView_text_endTime.setText(list.get(i).getEndTime()+"  end");
        viewHolder.textView_fare.setText(list.get(i).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_screeningHall)
        TextView textView_screeningHall;
        @BindView(R.id.text_beginTime)
        TextView textView_beginTime;
        @BindView(R.id.text_endTime)
        TextView textView_text_endTime;
        @BindView(R.id.text_fare)
        TextView textView_fare;
        @BindView(R.id.image_next)
        ImageView imageView_next;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
