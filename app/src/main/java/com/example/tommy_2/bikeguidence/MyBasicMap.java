package com.example.tommy_2.bikeguidence;

import android.os.Bundle;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;

/**
 * Created by tommy_2 on 3/25/2015.
 */

public class MyBasicMap extends MapActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the zoom level, center point and enable the default zoom controls
        MapView map = (MapView) findViewById(R.id.map);
        map.getController().setZoom(9);
        map.getController().setCenter(new GeoPoint(39.7001,-75.1114));
        map.setBuiltInZoomControls(true);
    }

    // return false since no route is being displayed
    @Override
    public boolean isRouteDisplayed() {
        return false;
    }
}
