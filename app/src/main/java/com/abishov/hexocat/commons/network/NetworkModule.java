package com.abishov.hexocat.commons.network;

import android.content.Context;

import com.abishov.hexocat.commons.dagger.PerSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@PerSession
@Module
public final class NetworkModule {
    private static final String OK_HTTP = "OkHttp";

    private final HttpUrl baseUrl;

    public NetworkModule(HttpUrl baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @PerSession
    TypeAdapterFactory gsonAdapterFactory() {
        return HexocatAdapterFactory.create();
    }

    @Provides
    @PerSession
    CallAdapter.Factory rxJavaAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @PerSession
    Gson gson(TypeAdapterFactory adapterFactory) {
        return new GsonBuilder()
                .registerTypeAdapterFactory(adapterFactory)
                .create();
    }

    @Provides
    @PerSession
    Cache okHttpCache(Context context) {
        return new Cache(context.getCacheDir(), 10 * 1024 * 1024); // 10 MiBs
    }

    @Provides
    @PerSession
    Interceptor okHttpLogging(Cache cache) {
        return new HttpLoggingInterceptor(message -> {
            Timber.tag(OK_HTTP).d(message);
            Timber.tag(OK_HTTP).v("Cache: requests=[%s], network=[%s], hits=[%s]",
                    cache.requestCount(), cache.networkCount(), cache.hitCount());
        }).setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @PerSession
    OkHttpClient okHttpClient(Interceptor logger, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(logger)
                .cache(cache)
                .connectTimeout(32, TimeUnit.SECONDS)
                .writeTimeout(32, TimeUnit.SECONDS)
                .readTimeout(32, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @PerSession
    Converter.Factory gsonFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @PerSession
    Retrofit retrofit(Converter.Factory factory,
            CallAdapter.Factory rxJavaAdapterFactory,
            OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .addCallAdapterFactory(rxJavaAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @PerSession
    Downloader okHttpPicasso(OkHttpClient okClient) {
        return new OkHttp3Downloader(okClient);
    }

    @Provides
    @PerSession
    Picasso picasso(Context context, Downloader downloader) {
        return new Picasso.Builder(context)
                .downloader(downloader)
                .build();
    }
}