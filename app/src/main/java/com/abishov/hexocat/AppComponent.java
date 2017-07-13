package com.abishov.hexocat;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {
    void inject(@NonNull Hexocat hexocat);
}
