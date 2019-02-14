package com.bw.movie.login_success.nearby_cinema_fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.login_success.nearby_cinema_fragment.activity.CinemaDtailActivity;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.RecommentBean;
import com.bw.movie.tools.ToastUtils;

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
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.recommend_cineam,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
          viewHolder.textView_name.setText(list.get(i).getName());
          viewHolder.textView_address.setText(list.get(i).getAddress());
          viewHolder.textView_km.setText(list.get(i).getDistance()+"km");
        Glide.with(context).load(list.get(i).getLogo()).into(viewHolder.imageView);
        if(list.get(i).getFollowCinema()==1){
            viewHolder.imageView_collection.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            viewHolder.imageView_collection.setImageResource(R.mipmap.com_icon_collection_default);
        }

        //点击/取消关注
        viewHolder.imageView_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(callBack!=null){
                       callBack.getInformation(list.get(i).getId(),list.get(i).getFollowCinema(),i);
                   }
            }
        });
        //跳转到用户关注的影院信息
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CinemaDtailActivity.class);
                intent.putExtra("logo",list.get(i).getLogo());
                intent.putExtra("name",list.get(i).getName());
                intent.putExtra("address",list.get(i).getAddress());
                intent.putExtra("id",list.get(i).getId());
                context.startActivity(intent);
            }
        });
    }
    public void update(int position){
        list.get(position).setFollowCinema(1);
        notifyDataSetChanged();
    }
    public void update2(int position){
        list.get(position).setFollowCinema(2);
        notifyDataSetChanged();
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
    public CallBack callBack;
    public void setOnCallBack(CallBack myCallBack){
        this.callBack=myCallBack;
    }
    public interface CallBack {
        void getInformation(int id,int followCinema,int position);
    }

}
