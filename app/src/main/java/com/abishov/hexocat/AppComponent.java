package com.abishov.hexocat;

import com.abishov.hexocat.commons.network.NetworkComponent;
import com.abishov.hexocat.commons.network.NetworkModule;
import com.abishov.hexocat.commons.schedulers.SchedulerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class, SchedulerModule.class
})
public interface AppComponent {
    void inject(Hexocat hexocat);

    NetworkComponent plus(NetworkModule networkModule);
}
