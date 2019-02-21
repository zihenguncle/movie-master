package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.io.ByteArrayOutputStream;

public class ImageBigActivity extends AppCompatActivity {

    ImageView image;

    float scaleWidth;
    float scaleHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_big);
        image = findViewById(R.id.image_big);
        Intent intent = getIntent();
        String imageurl = intent.getStringExtra("image");

        Glide.with(ImageBigActivity.this).load(imageurl).into(image);
        /*Bitmap bmp = stringToBitmap(imageurl);

        //获得Bitmap的高和宽
        int bmpWidth=bmp.getWidth();
        int bmpHeight=bmp.getHeight();

//设置缩小比例
        double scale=0.8;
//计算出这次要缩小的比例
        scaleWidth=(float)(scaleWidth*scale);
        scaleHeight=(float)(scaleHeight*scale);

//产生resize后的Bitmap对象
        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBmp=Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);


        String s = bitmapToString(resizeBmp);
        */
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }



    public String bitmapToString(Bitmap bitmap){
        //将Bitmap转换成字符串
        String string=null;
        ByteArrayOutputStream bStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bStream);
        byte[]bytes=bStream.toByteArray();
        string=Base64.encodeToString(bytes,Base64.DEFAULT);
        return string;
    }


}
