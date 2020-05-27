package com.haiyishuzi.haiyishuzijcm.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.gyf.barlibrary.ImmersionBar;
import com.haiyishuzi.haiyishuzijcm.HaiyishuziApp;
import com.haiyishuzi.haiyishuzijcm.callBack.TransparentBarAble;
import com.haiyishuzi.haiyishuzijcm.callBack.UserFragmentLabel;
import com.haiyishuzi.haiyishuzijcm.util.SearchUtil;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by wpl on 2017/10/23.
 * Helper class to automatically inject fragments if they implement {@link Injectable}.
 */

public class AppInjector {

    private AppInjector() {
    }

    public static void init(HaiyishuziApp app) {
        DaggerAppComponent.builder().application(app)
                .build().inject(app);
        app
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
//                        统计启动次数
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        ImmersionBar.with(activity).destroy();
                        //                        将统计信息上传服务器

                    }
                });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasSupportFragmentInjector) {
            AndroidInjection.inject(activity);
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                                              Bundle savedInstanceState) {
                                    if (f instanceof Injectable) {
                                        AndroidSupportInjection.inject(f);
                                    }
                                    //具体使用方法参见https://www.jianshu.com/p/2a884e211a62
                                    ImmersionBar mImmersionBar = ImmersionBar.with(activity);
                                    mImmersionBar.reset();
                                    //做沉浸式,因为跳转有其他fragment参与所以需要双层接口区分
                                    if (f instanceof UserFragmentLabel) {
                                        if (f instanceof TransparentBarAble) {
                                            mImmersionBar.transparentBar().statusBarDarkFont(false).keyboardEnable(true).init();
                                        } else {
                                            mImmersionBar.transparentStatusBar().statusBarDarkFont(false).keyboardEnable(true).init();
                                        }
                                    }
                                    //清空该fragment的查询条件
                                    SearchUtil.instance().removeSearch(f.getClass().toString());
                                }

                                @Override
                                public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                                    super.onFragmentResumed(fm, f);
//                                    System.out.println(f + "onFragmentResumed");
                                }

                            }, true);
        }
    }
}
