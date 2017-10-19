package com.abishov.hexocat;

import android.app.Application;

import com.abishov.hexocat.commons.network.NetworkModule;
import com.abishov.hexocat.commons.picasso.PicassoModule;
import com.abishov.hexocat.commons.schedulers.SchedulerModule;
import com.abishov.hexocat.home.repository.RepositoryItemView;
import com.abishov.hexocat.home.trending.TrendingComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        PicassoModule.class,
        SchedulerModule.class,
})
public interface AppComponent {
    void inject(Hexocat hexocat);

    // ToDo: remove in the future
    void inject(RepositoryItemView repositoryItemView);

    TrendingComponent trendingComponent();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
