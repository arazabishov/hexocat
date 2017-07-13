package com.abishov.hexocat;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class, AppModule.class
})
public interface AppComponent {
    void inject(@NonNull Hexocat hexocat);
}
