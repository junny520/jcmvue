package com.haiyishuzi.cordovayzd;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import org.apache.cordova.CordovaActivity;

/**
 * @author wpl on 2019-06-28.
 */
public class JcmCordovaActivity extends CordovaActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }
        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);
    }

    @Override
    protected String getConfigName() {
        String configName;
        Bundle extras = getIntent().getExtras();
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
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            statsBarColor = extras.getInt("statsBarColor");
        }
        if (statsBarColor != 0){
            View statsbarView = new View(this);
            statsbarView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.status_bar_height)));
            statsbarView.setBackgroundResource(statsBarColor);
            return statsbarView;
        }
        return null;
    }

}
