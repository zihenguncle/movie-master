package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

public class ImageBigActivity extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_big);
        image = findViewById(R.id.image_big);
        Intent intent = getIntent();
        String imageurl = intent.getStringExtra("image");
        Glide.with(this).load(imageurl).into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
