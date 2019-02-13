package com.bw.movie.login_success.person.personal_adapter;

import android.content.Context;
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
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.person.personal_bean.CimeamaBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CimeamaAdapter extends RecyclerView.Adapter<CimeamaAdapter.ViewHolder> {

    public List<CimeamaBean.ResultBean> mData;
    public Context mContext;


    public CimeamaAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setDatas(List<CimeamaBean.ResultBean> data) {
        //this.mData = mData;
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addDatas(List<CimeamaBean.ResultBean> data) {
        //this.mData = mData;
        //mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cimeama_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mData.get(i%mData.size()).getLogo())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(5)))
                .into(viewHolder.cimeamaItemImage);
        viewHolder.cimeamaItemTextName.setText(mData.get(i).getName());
        viewHolder.cimeamaItemTextTitle.setText(mData.get(i).getAddress());
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cimeama_item_image)
        ImageView cimeamaItemImage;
        @BindView(R.id.cimeama_item_text_name)
        TextView cimeamaItemTextName;
        @BindView(R.id.cimeama_item_text_title)
        TextView cimeamaItemTextTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
