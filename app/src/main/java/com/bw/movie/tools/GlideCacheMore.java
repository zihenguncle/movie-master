package com.bw.movie.tools;



import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;
import java.lang.annotation.Annotation;

public class GlideCacheMore implements GlideModule {
    private String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/GlidePath";//更改为自己想要的名字

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
      //  builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置磁盘缓存目录（和创建的缓存目录相同）
        File file=new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        String downloadDirectoryPath=path;

        int cacheSize = 100*1000*1000;   //这是缓存大小
        builder.setDiskCache( new DiskLruCacheFactory(downloadDirectoryPath, cacheSize) );
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
