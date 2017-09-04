package com.khaledahmed.connectfcis.Routing.OutdoorRouting;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Khaled Ahmed on 3/10/2017.
 */
public class MyLocationListener implements LocationListener {

    private Context activityContext;

    public MyLocationListener(Context activityContext) {
        this.activityContext = activityContext;
    }

    @Override
    public void onLocationChanged(Location location) {
      //  Toast.makeText(activityContext, location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(activityContext, "GPS Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(activityContext, "GPS Disabled", Toast.LENGTH_SHORT).show();
    }
}
