package com.abishov.hexocat;

import android.app.Application;
import com.abishov.hexocat.common.network.NetworkModule;
import com.abishov.hexocat.common.picasso.PicassoModule;
import com.abishov.hexocat.common.schedulers.SchedulerModule;
import com.squareup.picasso.RequestHandler;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

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

  OkHttpClient okHttpClient();

  void inject(Hexocat hexocat);

  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder baseUrl(HttpUrl baseUrl);

    @BindsInstance
    Builder application(Application application);

    @BindsInstance
    Builder requestHandler(@Nullable RequestHandler requestHandler);

    AppComponent build();
  }
}
