package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.activity.CinemaSeatTableActivity;
import com.bw.movie.login_success.home_fragment.bean.SchedBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchedAdapter extends RecyclerView.Adapter<SchedAdapter.ViewHolder> {

    private List<SchedBean.ResultBean> data;
    private Context context;

    public SchedAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<SchedBean.ResultBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SchedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_sched,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.screeninghall.setText(data.get(i).getScreeningHall());
        viewHolder.begintime.setText(data.get(i).getBeginTime());
        viewHolder.endtime.setText(data.get(i).getEndTime());
        viewHolder.fare.setText(data.get(i).getPrice()+"");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieName != null){
                    movieName.setFloat(data.get(i).getBeginTime(),data.get(i).getEndTime(),data.get(i).getScreeningHall(),data.get(i).getPrice());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_screeningHall)
        TextView screeninghall;
        @BindView(R.id.text_screening)
        TextView screening;
        @BindView(R.id.text_beginTime)
        TextView begintime;
        @BindView(R.id.text_endTime)
        TextView endtime;
        @BindView(R.id.text_fare)
        TextView fare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    setMovieName movieName;
    public void setName(setMovieName name){
        movieName = name;
    }
    public interface setMovieName{
        void setFloat(String starttime,String endtime,String num,double price);
    }
}
