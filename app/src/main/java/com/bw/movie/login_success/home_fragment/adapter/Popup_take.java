package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.home_fragment.bean.TakeBean;
import com.bw.movie.mvp.presenter.IPresemterImpl;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.mvp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Popup_take extends RecyclerView.Adapter<Popup_take.ViewHolder> implements IView {

    private List<TakeBean.ResultBean> data;
    private Context context;
    private IPresemterImpl iPresemter;

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
        iPresemter=new IPresemterImpl(this);
        return new ViewHolder(view);
    }

    public static final String Time_Style = "yyyy-MM-dd HH:mm:ss";
    @Override
    public void onBindViewHolder(@NonNull final Popup_take.ViewHolder viewHolder, int i) {
        Glide.with(context).load(data.get(i).getCommentHeadPic()).into(viewHolder.itemTakeHeadimage);
        viewHolder.itemTakeName.setText(data.get(i).getCommentUserName());
        Glide.with(context).load(data.get(i).getCommentHeadPic())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(180)))
                .into(viewHolder.itemTakeHeadimage);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Time_Style, Locale.getDefault());
        String format = dateFormat.format(data.get(i).getCommentTime());
        viewHolder.itemTakeDate.setText(format);
        viewHolder.itemTakeContent.setText(data.get(i).getMovieComment());
        viewHolder.itemTakeCommentnum.setText(data.get(i).getReplyNum()+"");
        viewHolder.itemTakePraisenum.setText(data.get(i).getGreatNum()+"");
        viewHolder.totalNum.setText("共"+data.get(i).getReplyNum()+"条评论");
        //设置适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.itemXrecycleItem.setLayoutManager(linearLayoutManager);
        viewHolder.itemXrecycleItem.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
        viewHolder.itemTakeCommentnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.itemXrecycleItem.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getData(){
        //iPresemter.startRequestGet(String.format(Apis.URL_TAKE_TAKE,));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onSuccessed(Object data) {

    }

    @Override
    public void onFailed(String error) {

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
