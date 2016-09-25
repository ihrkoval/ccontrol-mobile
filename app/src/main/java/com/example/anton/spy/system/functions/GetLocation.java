package com.example.anton.spy.system.functions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Anton on 24.09.2016.
 */

public class GetLocation implements LocationListener {
   public LocationManager locationManager;
    Context context;
    public String provider;

    public GetLocation(Context context) {
        System.out.println("CREATE CLASS LOCATION");
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        System.out.println(provider + " PROVIDER");
        System.out.println(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION));


        if (provider != null && !provider.equals("")) {
            System.out.println("PROVIDER IS NOT NULL");
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Location location = locationManager.getLastKnownLocation(provider);
            System.out.println("GET TIME LOCATION");
            System.out.println(location.getTime() + " TIME LOCATION");
            locationManager.requestLocationUpdates(provider, 15000, 1, this);
            if (location != null)
                onLocationChanged(location);
            // Location location = locationManager.getLastKnownLocation(provider);
        }

    }

    public String getProvider(){
        return provider;
    }

    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
        System.out.println("lat " + lat + "lng "+lng);
        //... ... ..

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
