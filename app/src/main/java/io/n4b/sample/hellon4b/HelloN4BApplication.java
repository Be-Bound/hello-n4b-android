package io.n4b.sample.hellon4b;

import android.app.Application;

import com.bebound.sdk.BeBound;
import com.bebound.sdk.application.BeBoundApplicationDelegate;

public class HelloN4BApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BeBoundApplicationDelegate.onCreate(this, new HelloN4B.AppConfig());
    }
}
