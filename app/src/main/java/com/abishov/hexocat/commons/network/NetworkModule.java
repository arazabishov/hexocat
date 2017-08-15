package com.abishov.hexocat.commons.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.abishov.hexocat.commons.dagger.PerSession;
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

@PerSession
@Module
public final class NetworkModule {
    private static final String OK_HTTP = "OkHttp";

    @NonNull
    private final HttpUrl baseUrl;

    public NetworkModule(@NonNull HttpUrl baseUrl) {
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Provides
    @PerSession
    TypeAdapterFactory gsonAdapterFactory() {
        return HexocatAdapterFactory.create();
    }

    @NonNull
    @Provides
    @PerSession
    CallAdapter.Factory rxJavaAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @NonNull
    @Provides
    @PerSession
    Gson gson(@NonNull TypeAdapterFactory adapterFactory) {
        return new GsonBuilder()
                .registerTypeAdapterFactory(adapterFactory)
                .create();
    }

    @NonNull
    @Provides
    @PerSession
    Cache okHttpCache(@NonNull Context context) {
        return new Cache(context.getCacheDir(), 10 * 1024 * 1024); // 10 MiBs
    }

    @NonNull
    @Provides
    @PerSession
    Interceptor okHttpLogging(@NonNull Cache cache) {
        return new HttpLoggingInterceptor(message -> {
            Timber.tag(OK_HTTP).d(message);
            Timber.tag(OK_HTTP).v("Cache: requests=[%s], network=[%s], hits=[%s]",
                    cache.requestCount(), cache.networkCount(), cache.hitCount());
        }).setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @NonNull
    @Provides
    @PerSession
    OkHttpClient okHttpClient(@NonNull Interceptor logger, @NonNull Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(logger)
                .cache(cache)
                .connectTimeout(32, TimeUnit.SECONDS)
                .writeTimeout(32, TimeUnit.SECONDS)
                .readTimeout(32, TimeUnit.SECONDS)
                .build();
    }

    @NonNull
    @Provides
    @PerSession
    Converter.Factory gsonFactory(@NonNull Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @NonNull
    @Provides
    @PerSession
    Retrofit retrofit(@NonNull Converter.Factory factory,
            @NonNull CallAdapter.Factory rxJavaAdapterFactory,
            @NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .addCallAdapterFactory(rxJavaAdapterFactory)
                .client(okHttpClient)
                .build();
    }
}