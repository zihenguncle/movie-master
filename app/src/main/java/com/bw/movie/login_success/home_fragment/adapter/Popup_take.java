package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.bean.TakeBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Popup_take extends RecyclerView.Adapter<Popup_take.ViewHolder> {

    private List<TakeBean.ResultBean> data;
    private Context context;

    public Popup_take(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<TakeBean.ResultBean> data) {
        this.data = data;
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void addData(List<TakeBean.ResultBean> data) {
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Popup_take.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.popup_take_item,null);
        return new ViewHolder(view);
    }

    public static final String Time_Style = "yyyy-MM-dd HH:mm:ss";
    @Override
    public void onBindViewHolder(@NonNull Popup_take.ViewHolder viewHolder, int i) {
        Glide.with(context).load(data.get(i).getCommentHeadPic()).into(viewHolder.itemTakeHeadimage);
        viewHolder.itemTakeName.setText(data.get(i).getCommentUserName());
        viewHolder.itemTakeContent.setText(data.get(i).getHotComment());
        SimpleDateFormat dateFormat = new SimpleDateFormat(Time_Style, Locale.getDefault());
        String format = dateFormat.format(data.get(i).getCommentTime());
        viewHolder.itemTakeDate.setText(format);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_take_headimage)
        ImageView itemTakeHeadimage;
        @BindView(R.id.item_take_name)
        TextView itemTakeName;
        @BindView(R.id.item_take_content)
        TextView itemTakeContent;
        @BindView(R.id.item_take_date)
        TextView itemTakeDate;
        @BindView(R.id.item_take_commentnum)
        TextView itemTakeCommentnum;
        @BindView(R.id.item_take_praisenum)
        TextView itemTakePraisenum;
        @BindView(R.id.total_num)
        TextView totalNum;
        @BindView(R.id.item_xrecycle_item)
        XRecyclerView itemXrecycleItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
