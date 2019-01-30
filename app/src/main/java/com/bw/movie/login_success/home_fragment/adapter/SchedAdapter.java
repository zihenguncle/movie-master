package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.bean.SchedBean;

import java.util.ArrayList;
import java.util.List;

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
    public void onBindViewHolder(@NonNull SchedAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
