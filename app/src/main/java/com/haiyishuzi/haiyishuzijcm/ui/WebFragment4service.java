package com.haiyishuzi.haiyishuzijcm.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.haiyishuzi.cordovayzd.ServiceH5Fragment;
import com.haiyishuzi.haiyishuzijcm.callBack.UserFragmentLabel;
import com.haiyishuzi.haiyishuzijcm.di.Injectable;


/**
 * 使用Cordova加载h5的fragment
 *
 */

public class WebFragment4service extends ServiceH5Fragment implements Injectable, UserFragmentLabel {

    private static final String LAUNCH_URL = "LAUNCH_URL";
    //是否要注入本地js
    private static final String NATIVE_JS = "NATIVE_JS";

    public static WebFragment4service create(String url, boolean nativeJs) {
        WebFragment4service webFragment = new WebFragment4service();
        Bundle bundle = new Bundle();
        bundle.putString(LAUNCH_URL, url);
        bundle.putBoolean(NATIVE_JS,nativeJs);
        webFragment.setArguments(bundle);
        return webFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUrl(launchUrl);
    }

    @Override
    protected String getConfigName() {
        String configName;
        Bundle extras = getArguments();
        if(extras != null){
            configName = extras.getString("configName","config");
        }else{
            configName = "config";
        }
        return configName;
    }

    /**
     * 添加在头部的额外视图，一般用于沉浸式
     *
     * @param context
     * @return view
     */
    @Override
    protected View getTopExtraView(Context context) {
        int statsBarColor = 0;
        Bundle extras = getArguments();
        if(extras != null){
            statsBarColor = extras.getInt("statsBarColor");
        }
        if (statsBarColor != 0){
            View statsbarView = new View(getContext());
            statsbarView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(com.haiyishuzi.cordovayzd.R.dimen.status_bar_height)));
            statsbarView.setBackgroundResource(statsBarColor);
            return statsbarView;
        }
        return null;
    }


}
