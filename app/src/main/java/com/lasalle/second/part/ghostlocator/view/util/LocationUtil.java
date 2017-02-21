package com.lasalle.second.part.ghostlocator.view.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.security.Permission;

public class LocationUtil implements LocationListener {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private LocationManager locationManager;
    private Context context;

    private Location lastKnownLocation;

    public LocationUtil(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean subscribeLocationUpdates() {

        boolean grantedFineLocation = (
                ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        boolean grantedCoarseLocation = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        if(grantedFineLocation && grantedCoarseLocation)
        {
            Criteria myCriteria = new Criteria();
            myCriteria.setAccuracy(Criteria.ACCURACY_FINE);
            myCriteria.setPowerRequirement(Criteria.POWER_LOW);

            String myProvider = locationManager.getBestProvider(myCriteria, true);

            long minTimeMillis = 60;

            locationManager.requestLocationUpdates(myProvider,minTimeMillis,0, this);
            return true;
        }
        return false;
    }

    public void unsubscribeLocationUpdates() {
        if((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
           (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
        {
            locationManager.removeUpdates(this);
        }
    }

    public void requestPermissions(Fragment fragment) {
        fragment.requestPermissions(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, PERMISSION_REQUEST_CODE);
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        lastKnownLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
