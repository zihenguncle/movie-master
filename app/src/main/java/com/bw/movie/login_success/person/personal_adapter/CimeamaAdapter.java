package com.bw.movie.login_success.person.personal_adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.nearby_cinema_fragment.activity.CinemaDtailActivity;
import com.bw.movie.login_success.person.personal_bean.CimeamaBean;
import com.bw.movie.tools.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CimeamaAdapter extends RecyclerView.Adapter<CimeamaAdapter.ViewHolder> {

    public List<CimeamaBean.ResultBean> mData;
    public Context mContext;
    private String URl=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Camera/test.jpg";
    private Bitmap bitmap;


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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(viewHolder.cimeamaItemImage, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        viewHolder.cimeamaItemImage.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(mData.get(i%mData.size()).getLogo())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(5)).placeholder(R.drawable.rotate))
                .into(viewHolder.cimeamaItemImage);
        viewHolder.cimeamaItemTextName.setText(mData.get(i).getName());
        viewHolder.cimeamaItemTextTitle.setText(mData.get(i).getAddress());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CinemaDtailActivity.class);
                intent.putExtra("logo",mData.get(i).getLogo());
                intent.putExtra("name",mData.get(i).getName());
                intent.putExtra("address",mData.get(i).getAddress());
                intent.putExtra("id",mData.get(i).getId());
                mContext.startActivity(intent);
            }
        });

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
