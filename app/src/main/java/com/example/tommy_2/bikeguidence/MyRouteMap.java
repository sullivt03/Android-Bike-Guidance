package com.example.tommy_2.bikeguidence;

import android.os.Bundle;

import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.RouteManager;

/**
 * Created by tommy_2 on 3/25/2015.
 */
public class MyRouteMap extends MapActivity {
    protected MapView map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMapView();
        displayRoute();
    }

    // set your map and enable default zoom controls
    private void setupMapView() {
        this.map = (MapView) findViewById(R.id.map);
        map.setBuiltInZoomControls(true);
    }

    // create a route and display on the map
    private void displayRoute() {
        RouteManager routeManager = new RouteManager(this);
        routeManager.setMapView(map);
        routeManager.createRoute("{latLng:{lat:39.7001,lng:-75.1114}}", "Hackettstown, NJ");
    }

    @Override
    public boolean isRouteDisplayed() {
        return true;
    }
}
