package com.haiyishuzi.cordovayzd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.apache.cordova.Config;
import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterfaceFragmentImpl;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;
import org.apache.cordova.CordovaWebViewWplImpl;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginEntry;
import org.apache.cordova.PluginManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * 参照CordovaActivity实现的fragment,加载服务端h5，本地注入cordova相关js
 * @author wpl on 2019-05-30.
 */
public abstract class ServiceH5Fragment extends Fragment {

    public static final String TAG = "ServiceH5Fragment";

    // The webview for our app
    protected CordovaWebView appView;
    // Read from config.xml:
    protected CordovaPreferences preferences;
    protected String launchUrl;
    protected ArrayList<PluginEntry> pluginEntries;
    protected CordovaInterfaceFragmentImpl cordovaInterface;
    // Keep app running when pause is received. (default = true)
    // If true, then the JavaScript and native code continue to run in the background
    // when another application (activity) is started.
    protected boolean keepRunning = true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        loadConfig();
        String logLevel = preferences.getString("loglevel", "ERROR");
        LOG.setLogLevel(logLevel);
        LOG.i(TAG, "Apache Cordova native platform version " + CordovaWebView.CORDOVA_VERSION + " is starting");
        LOG.d(TAG, "CordovaActivity.onCreate()");
        super.onCreate(savedInstanceState);
        cordovaInterface = makeCordovaInterface();
        if (savedInstanceState != null) {
            cordovaInterface.restoreInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //20180726wpl修改，添加沉浸式占位高度
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewParent viewParent = appView.getView().getParent();
        if(null != viewParent){
            LinearLayout parent = (LinearLayout) viewParent;
            parent.removeAllViews();
        }
        //根据是否设置了statsBarColor做是在原生层处理沉浸式还是h5中
        View topExtraView = getTopExtraView(getContext());
        if(null != topExtraView)
            layout.addView(topExtraView);
        layout.addView(appView.getView());
        return layout;
//        return appView.getView();
    }

    /**
     * Called when the system is about to start resuming a previous activity.
     */
    @Override
    public void onPause() {
        super.onPause();
        LOG.d(TAG, "Paused the activity.");

        if (this.appView != null) {
            // CB-9382 If there is an activity that started for result and main activity is waiting for callback
            // result, we shoudn't stop WebView Javascript timers, as activity for result might be using them
            boolean keepRunning = this.keepRunning || this.cordovaInterface.activityResultCallback != null;
            this.appView.handlePause(keepRunning);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LOG.d(TAG, "Resumed the activity.");

        if (this.appView == null) {
            return;
        }
        // Force window to have focus, so application always
        // receive user input. Workaround for some devices (Samsung Galaxy Note 3 at least)
        getActivity().getWindow().getDecorView().requestFocus();

        this.appView.handleResume(this.keepRunning);
    }

    @Override
    public void onStop() {
        super.onStop();
        LOG.d(TAG, "Stopped the activity.");

        if (this.appView == null) {
            return;
        }
        this.appView.handleStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        LOG.d(TAG, "Started the activity.");

        if (this.appView == null) {
            return;
        }
        this.appView.handleStart();
    }

    @Override
    public void onDestroy() {
        LOG.d(TAG, "CordovaActivity.onDestroy()");
        super.onDestroy();

        if (this.appView != null) {
            appView.handleDestroy();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        cordovaInterface.setActivityResultRequestCode(requestCode);
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        cordovaInterface.setActivityResultRequestCode(requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode The request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param intent      An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        LOG.d(TAG, "Incoming Result. Request code = " + requestCode);
        super.onActivityResult(requestCode, resultCode, intent);
        cordovaInterface.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        cordovaInterface.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * Called by the system when the device configuration changes while your activity is running.
     *
     * @param newConfig The new device configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.appView == null) {
            return;
        }
        PluginManager pm = this.appView.getPluginManager();
        if (pm != null) {
            pm.onConfigurationChanged(newConfig);
        }
    }


    /**
     * Called by the system when the user grants permissions
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        try
        {
            cordovaInterface.onRequestPermissionResult(requestCode, permissions, grantResults);
        }
        catch (JSONException e)
        {
            LOG.d(TAG, "JSONException: Parameters fed into the method are not valid");
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    protected void loadConfig() {
        ConfigXmlParser parser = new ConfigXmlParser();
        String configName = getConfigName();
        if(TextUtils.isEmpty(configName)){
            configName = "config";
        }
        parser.parse(getContext(),configName);
        preferences = parser.getPreferences();
        preferences.setPreferencesBundle(getArguments());
        launchUrl = parser.getLaunchUrl();
        pluginEntries = parser.getPluginEntries();
        Config.parser = parser;
    }

    /**
     * Load the url into the webview.
     */
    public void loadUrl(String url) {
        if (appView == null) {
            init();
        }

        // If keepRunning
        this.keepRunning = preferences.getBoolean("KeepRunning", true);

        appView.loadUrlIntoView(url, true);
    }

    protected void init() {
        appView = makeWebView();
        createViews();
        if (!appView.isInitialized()) {
            appView.init(cordovaInterface, pluginEntries, preferences);
        }
        cordovaInterface.onCordovaInit(appView.getPluginManager());

        // Wire the hardware volume controls to control media if desired.
        String volumePref = preferences.getString("DefaultVolumeStream", "");
        if ("media".equals(volumePref.toLowerCase(Locale.ENGLISH))) {
            getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }
    }

    /**
     * Construct the default web view object.
     * <p/>
     * Override this to customize the webview that is used.
     */
    protected CordovaWebView makeWebView() {
        return new CordovaWebViewWplImpl(makeWebViewEngine());
    }

    protected CordovaWebViewEngine makeWebViewEngine() {
        return CordovaWebViewWplImpl.createEngine(getActivity(), preferences);
    }

    //Suppressing warnings in AndroidStudio
    @SuppressWarnings({"deprecation", "ResourceType"})
    protected void createViews() {
        //Why are we setting a constant as the ID? This should be investigated
        appView.getView().setId(100);
        appView.getView().setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        if (preferences.contains("BackgroundColor")) {
            try {
                int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
                // Background of activity:
                appView.getView().setBackgroundColor(backgroundColor);
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        appView.getView().requestFocusFromTouch();
    }

    protected abstract String getConfigName();
    /**
     * 添加在头部的额外视图，一般用于沉浸式
     * @return view
     */
    protected abstract View getTopExtraView(Context context);

    protected CordovaInterfaceFragmentImpl makeCordovaInterface() {
        return new CordovaInterfaceFragmentImpl(this) {
            @Override
            public Object onMessage(String id, Object data) {
                // Plumb this to CordovaActivity.onMessage for backwards compatibility
                return ServiceH5Fragment.this.onMessage(id, data);
            }
        };
    }

    public Object onMessage(String id, Object data) {
        if ("onReceivedError".equals(id)) {
            JSONObject d = (JSONObject) data;
            try {
                this.onReceivedError(d.getInt("errorCode"), d.getString("description"), d.getString("url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if ("exit".equals(id)) {
            getActivity().onBackPressed();
        }
        return null;
    }

    public void onReceivedError(final int errorCode, final String description, final String failingUrl) {
        final ServiceH5Fragment me = this;
        // If errorUrl specified, then load it
        final String errorUrl = preferences.getString("errorUrl", null);
        if ((errorUrl != null) && (!failingUrl.equals(errorUrl)) && (appView != null)) {
            // Load URL on UI thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    me.appView.showWebPage(errorUrl, false, true, null);
                }
            });
        }
        // If not, then display error dialog
        else {
            final boolean exit = !(errorCode == WebViewClient.ERROR_HOST_LOOKUP);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (exit) {
                        me.appView.getView().setVisibility(View.GONE);
                        //修改成不提示
//                        me.displayError("Application Error", description + " (" + failingUrl + ")", "OK", exit);
                    }
                }
            });
        }
    }

    /**
     * Display an error dialog and optionally exit application.
     */
    public void displayError(final String title, final String message, final String button, final boolean exit) {
        final ServiceH5Fragment me = this;
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                try {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    dlg.setMessage(message);
                    dlg.setTitle(title);
                    dlg.setCancelable(false);
                    dlg.setPositiveButton(button,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (exit) {
                                        getActivity().finish();
                                    }
                                }
                            });
                    dlg.create();
                    dlg.show();
                } catch (Exception e) {
                    getActivity().onBackPressed();
                }
            }
        });
    }
}
