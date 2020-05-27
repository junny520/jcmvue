package com.haiyishuzi.haiyishuzijcm.liveData;

import android.os.Handler;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;

import java.util.Date;

/**
 *
 */
public class PaomiaoLiveData extends LiveData<Date> {

    private Handler handler;

    private static class Singleton{
        private static final PaomiaoLiveData INSTANCE = new PaomiaoLiveData();
    }

    @MainThread
    public static PaomiaoLiveData get() {
        return Singleton.INSTANCE;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable,1000);
            setValue(new Date());
        }
    };

    private PaomiaoLiveData() {
        handler = new Handler();
    }

    @Override
    protected void onActive() {
        handler.post(runnable);
    }

    @Override
    protected void onInactive() {
        handler.removeCallbacks(runnable);
    }

}
