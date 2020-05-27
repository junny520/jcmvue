package com.haiyishuzi.haiyishuzijcm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.haiyishuzi.haiyishuzijcm.di.AppInjector;
import com.jeremyliao.liveeventbus.LiveEventBus;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * @author wpl on 2019-06-27.
 */
public class HaiyishuziApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        AppInjector.init(this);

        //初始化LiveEventBus
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(true);

    }

    /**
     * Returns an {@link AndroidInjector} of {@link Activity}s.
     */
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        //版本小于21时候的分包处理
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(this);
        }
    }
}

