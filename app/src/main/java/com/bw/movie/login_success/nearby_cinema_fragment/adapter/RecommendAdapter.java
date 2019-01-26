package com.bw.movie.login_success.nearby_cinema_fragment.adapter;

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
import com.bw.movie.login_success.nearby_cinema_fragment.bean.RecommentBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    private List<RecommentBean.ResultBean> list;
    private Context context;

    public RecommendAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<RecommentBean.ResultBean> list) {
        this.list .clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addList(List<RecommentBean.ResultBean> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.recommend_cineam,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
          viewHolder.textView_name.setText(list.get(i).getName());
          viewHolder.textView_address.setText(list.get(i).getAddress());
          viewHolder.textView_km.setText(list.get(i).getDistance()+"km");
        Glide.with(context).load(list.get(i).getLogo()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rec_image)
        ImageView imageView;
        @BindView(R.id.rec_text_name)
        TextView textView_name;
        @BindView(R.id.rec_text_address)
        TextView textView_address;
        @BindView(R.id.rec_text_km)
        TextView textView_km;
        @BindView(R.id.rec_collection)
        ImageView imageView_collection;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
