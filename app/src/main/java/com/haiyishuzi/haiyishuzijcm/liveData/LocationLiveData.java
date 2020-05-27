package com.haiyishuzi.haiyishuzijcm.liveData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;

/**
 * 实现定位的livedata
 */

public class LocationLiveData extends LiveData<Location> {

    private static LocationLiveData sInstance;
    private LocationManager locationManager;

    @MainThread
    public static LocationLiveData get(Context context) {
        if (sInstance == null) {
            sInstance = new LocationLiveData(context.getApplicationContext());
        }
        return sInstance;

    }

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setValue(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private LocationLiveData(Context context) {
        locationManager = (LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
    }

    @Override
    protected void onInactive() {
        locationManager.removeUpdates(listener);
    }

}
