package com.abishov.hexocat;

import android.app.Application;

import com.abishov.hexocat.commons.network.NetworkModule;
import com.abishov.hexocat.commons.picasso.PicassoModule;
import com.abishov.hexocat.commons.schedulers.SchedulerModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import okhttp3.HttpUrl;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        PicassoModule.class,
        SchedulerModule.class,
        AndroidInjectionModule.class,
        AppBindings.class,
})
public interface AppComponent {
    void inject(Hexocat hexocat);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder baseUrl(HttpUrl baseUrl);

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
