package com.abishov.hexocat;

import okhttp3.HttpUrl;

public final class HexocatInstrumentationTestApp extends Hexocat {
    private HttpUrl baseUrl;

    @Override
    protected AppComponent prepareAppComponent() {
        if (baseUrl == null) {
            return super.prepareAppComponent();
        }

        return DaggerAppComponent.builder()
                .baseUrl(baseUrl)
                .application(this)
                .build();
    }

    public void overrideBaseUrl(HttpUrl baseUrl) {
        this.baseUrl = baseUrl;
        setupAppComponent();
    }
}
