package com.bw.movie.login_success.person.person_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.person.personal_bean.FileImageUntils;
import com.bw.movie.login_success.person.personal_bean.HeanPicBean;
import com.bw.movie.login_success.person.personal_bean.PersonalMessageBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SimpleDataUtils;
import com.bw.movie.tools.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_Message_Activity extends BaseActivity {
    @BindView(com.bw.movie.R.id.personal_reset_pwd)
    RelativeLayout personalResetPwd;
    @BindView(com.bw.movie.R.id.personal_icon)
    ImageView personal_icon;
    @BindView(com.bw.movie.R.id.personal_nickName_textView)
    TextView personal_nickName;
    @BindView(com.bw.movie.R.id.personal_sex_textView)
    TextView personal_sex;
    @BindView(com.bw.movie.R.id.personal_month_textView)
    TextView personal_month;
    @BindView(com.bw.movie.R.id.personal_phone_number)
    TextView personal_phone;
    @BindView(com.bw.movie.R.id.personal_emails_count)
    TextView peronsal_emails;
    private String path=Environment.getExternalStorageDirectory()+"/image.png";
    private String files=Environment.getExternalStorageDirectory()+"/zld.png";


    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @OnClick({com.bw.movie.R.id.personal_reset_pwd, com.bw.movie.R.id.personal_message_back,R.id.personal_icon})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personal_icon:
                //点击头像弹出popwindow
                popWindow();
                break;
            case com.bw.movie.R.id.personal_reset_pwd:
                Intent intent = new Intent(this, Personal_Reset_Pwd_Activity.class);
                startActivity(intent);
                break;
            case com.bw.movie.R.id.personal_message_back:
                finish();
                break;
                default:break;
        }
    }

    //上传头像
    private void popWindow() {
        View view=View.inflate(this,R.layout.popwindow_image,null);
        TextView text_camcme = view.findViewById(R.id.cancme);
        TextView text_pick = view.findViewById(R.id.pick);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(view,
                Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        text_camcme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                startActivityForResult(intent, 400);
                popupWindow.dismiss();
            }
        });
        text_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 300);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected int getViewById() {
        return com.bw.movie.R.layout.personal_message_activity;
    }


    @Override
    protected void initData() {
        startRequestGet(Apis.URL_PERSONAL_MESSAGE,PersonalMessageBean.class);
    }


    @Override
    protected void successed(Object data) {
        if(data instanceof PersonalMessageBean){
            PersonalMessageBean personalMessageBean= (PersonalMessageBean) data;
            if(personalMessageBean.getStatus().equals("0000")){
                String headPic = personalMessageBean.getResult().getHeadPic();
                Glide.with(this)
                        .load(headPic)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(personal_icon);
                personal_nickName.setText(personalMessageBean.getResult().getNickName());
                personal_phone.setText(personalMessageBean.getResult().getPhone());
                int sex = personalMessageBean.getResult().getSex();
                if(sex==1){
                    personal_sex.setText("男");
                }else{
                    personal_sex.setText("女");
                }
                try {
                    personal_month.setText(SimpleDataUtils.longToString(personalMessageBean.getResult().getBirthday(), "yyyy-MM-dd"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                peronsal_emails.setText(personalMessageBean.getResult().getEmail());
            }else{
                ToastUtils.toast(personalMessageBean.getMessage());
            }
        }else if(data instanceof HeanPicBean){
            HeanPicBean heanPicBean= (HeanPicBean) data;
            if(heanPicBean.getStatus().equals("0000")){
                Glide.with(this).load(heanPicBean.getHeadPath())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(personal_icon);
            }else{
                ToastUtils.toast(heanPicBean.getMessage());
            }
        }
    }
    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 400 && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出后图片大小
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回到data
            intent.putExtra("return-data", true);
            //启动
            startActivityForResult(intent, 200);
        }
        if (requestCode == 300 && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否可裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);
        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            FileImageUntils.setBitmap(bitmap, files, 50);
            Map<String, String> params = new HashMap<>();
            //TODO  头像路径
            params.put("image", files);

            startRequestFilesPost(Apis.TYPR_INMAGE_XINXI, params, HeanPicBean.class);

        }
    }

}
