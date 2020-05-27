package com.haiyishuzi.haiyishuzijcm.di;

import android.app.Application;

import com.haiyishuzi.haiyishuzijcm.HaiyishuziApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class,AppModule.class,MainActivityModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(HaiyishuziApp app);
}