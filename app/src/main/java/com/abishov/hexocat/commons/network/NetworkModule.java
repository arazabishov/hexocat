package com.abishov.hexocat.commons.network;

import android.content.Context;

import com.abishov.hexocat.commons.dagger.SessionScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

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

@Module
@SessionScope
public final class NetworkModule {
    private static final String OK_HTTP = "OkHttp";

    @Provides
    @SessionScope
    HttpUrl baseUrl() {
        return HttpUrl.parse("http://api.github.com");
    }

    @Provides
    @SessionScope
    TypeAdapterFactory gsonAdapterFactory() {
        return HexocatAdapterFactory.create();
    }

    @Provides
    @SessionScope
    CallAdapter.Factory rxJavaAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @SessionScope
    Gson gson(TypeAdapterFactory adapterFactory) {
        return new GsonBuilder()
                .registerTypeAdapterFactory(adapterFactory)
                .create();
    }

    @Provides
    @SessionScope
    Cache okHttpCache(Context context) {
        return new Cache(context.getCacheDir(), 10 * 1024 * 1024); // 10 MiBs
    }

    @Provides
    @SessionScope
    Interceptor okHttpLogging(Cache cache) {
        return new HttpLoggingInterceptor(message -> {
            Timber.tag(OK_HTTP).d(message);
            Timber.tag(OK_HTTP).v("Cache: requests=[%s], network=[%s], hits=[%s]",
                    cache.requestCount(), cache.networkCount(), cache.hitCount());
        }).setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @SessionScope
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
    @SessionScope
    Converter.Factory gsonFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @SessionScope
    Retrofit retrofit(HttpUrl baseUrl, Converter.Factory factory,
            CallAdapter.Factory rxJavaAdapterFactory,
            OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .addCallAdapterFactory(rxJavaAdapterFactory)
                .client(okHttpClient)
                .build();
    }
}