package com.abishov.hexocat.common.picasso;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
@Singleton
public abstract class PicassoModule {

    @Provides
    @Singleton
    static Downloader okHttpPicasso(OkHttpClient okClient) {
        return new OkHttp3Downloader(okClient);
    }

    @Provides
    @Singleton
    static Picasso picasso(Context context, Downloader downloader) {
        return new Picasso.Builder(context)
                .downloader(downloader)
                .build();
    }

    @Binds
    abstract Transformation circularTransformation(CircleTransformation impl);
}
