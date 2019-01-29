package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.home_fragment.bean.Take_Take_Bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Popup_taketake extends RecyclerView.Adapter<Popup_taketake.ViewHolder> {

    List<Take_Take_Bean.ResultBean> data;
    private Context context;

    public Popup_taketake(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<Take_Take_Bean.ResultBean> data) {
        this.data.clear();
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(List<Take_Take_Bean.ResultBean> data) {
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.popup_takeitem, null);
        return new ViewHolder(view);
    }

    public static final String Time_Style = "yyyy-MM-dd HH:mm:ss";
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(data.get(i).getReplyHeadPic())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(180)))
                .into(viewHolder.takeitemHeadimage);
        viewHolder.takeitemName.setText(data.get(i).getReplyUserName());
        viewHolder.takeitemContent.setText(data.get(i).getReplyContent());
        SimpleDateFormat dateFormat = new SimpleDateFormat(Time_Style, Locale.getDefault());
        String format = dateFormat.format(data.get(i).getCommentTime());
        viewHolder.takeitemDate.setText(format);
        //viewHolder.takeitemPraisenum.setText(data.get(i).gete);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.takeitem_headimage)
        ImageView takeitemHeadimage;
        @BindView(R.id.takeitem_name)
        TextView takeitemName;
        @BindView(R.id.takeitem_content)
        TextView takeitemContent;
        @BindView(R.id.takeitem_date)
        TextView takeitemDate;
        @BindView(R.id.takeitem_praise)
        ImageView takeitemPraise;
        @BindView(R.id.takeitem_praisenum)
        TextView takeitemPraisenum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
