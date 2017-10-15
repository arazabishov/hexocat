package com.abishov.hexocat.commons.picasso;

import android.content.Context;

import com.abishov.hexocat.commons.dagger.PicassoScope;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
@PicassoScope
public abstract class PicassoModule {

    @Provides
    @PicassoScope
    static Downloader okHttpPicasso(OkHttpClient okClient) {
        return new OkHttp3Downloader(okClient);
    }

    @Provides
    @PicassoScope
    static Picasso picasso(Context context, Downloader downloader) {
        return new Picasso.Builder(context)
                .downloader(downloader)
                .build();
    }

    @Binds
    abstract Transformation circularTransformation(CircleTransformation impl);
}
