package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.home_fragment.activity.DetailsActivity;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.login_success.nearby_cinema_fragment.adapter.RecommendAdapter;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.tools.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowDetailsAdapter extends RecyclerView.Adapter<ShowDetailsAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeBannerBean.ResultBean> mDatas;
    private String sessionId;

    public ShowDetailsAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas=new ArrayList();
    }

    public void setDatas(List<HomeBannerBean.ResultBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<HomeBannerBean.ResultBean> datas) {
        //mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ShowDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.show_details_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDetailsAdapter.ViewHolder viewHolder, final int i) {
        sessionId = (String) SharedPreferencesUtils.getParam(mContext, "sessionId", "0");
        viewHolder.show_details_item_title.setText(mDatas.get(i).getName());
        viewHolder.show_details_item_count.setText(mDatas.get(i).getSummary());
        Glide.with(mContext)
                .load(mDatas.get(i%mDatas.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(5)))
                .into(viewHolder.show_detaile_item_img);

        //判断是否关注
        if(mDatas.get(i).getFollowMovie()==1){
            viewHolder.show_details_item_image.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            viewHolder.show_details_item_image.setImageResource(R.mipmap.com_icon_collection_default);
        }
        //点击/取消关注
        viewHolder.show_details_item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(sessionId.equals("0")){
                   Intent intent = new Intent(mContext, LoginActivity.class);
                   mContext.startActivity(intent);
               }else {
                   if(callBack!=null){
                       callBack.getInformation(mDatas.get(i).getId(),mDatas.get(i).getFollowMovie(),i);
                   }
               }
            }
        });

        //跳转到详情页面
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(mContext, DetailsActivity.class);
                intent.putExtra("id",mDatas.get(i).getId());
               mContext.startActivity(intent);
            }
        });
    }
    public void updateSelect(int position){
        mDatas.get(position).setFollowMovie(1);
        notifyDataSetChanged();
    }
    public void updateChoose(int position){
        mDatas.get(position).setFollowMovie(2);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.show_detaile_item_img)
        ImageView show_detaile_item_img;
        @BindView(R.id.show_detaile_item_cont)
        TextView show_details_item_count;
        @BindView(R.id.show_details_item_image)
        ImageView show_details_item_image;
        @BindView(R.id.show_detaile_item_title)
        TextView show_details_item_title;
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
        void getInformation(int id,int followMovie,int position);
    }
}
