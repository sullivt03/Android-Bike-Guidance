package com.example.tommy_2.bikeguidence;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.mapquest.android.maps.RouteManager;

import java.util.ArrayList;

/**
 * Created by tommy_2 on 3/25/2015.
 */
public class MyRouteNarrative extends Activity {

    ArrayList<String> points = new ArrayList<String>(){{
        add("39.711845 , -75.116701");
        add("39.711952 , -75.115950");
        add("39.707586 , -75.111090");
        add("39.706645 , -75.111111");
        add("39.706950 , -75.113944");
        add("39.711209 , -75.127484");
        add("39.713784 , -75.123535");
        add("39.711581 , -75.120370");
        add("39.711160 , -75.119319");
        add("39.711952 , -75.115950");
        add("39.715765 , -75.120596");
        add("39.713784 , -75.123535");
        add("39.712563 , -75.121819");
    }};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        WebView itinerary = (WebView) findViewById(R.id.itinerary);

        // pass in your AppKey to RouteManager when not extending MapActivity
        RouteManager routeManager = new RouteManager(this,"Kmjtd%7Cluu22gurnq%2Can%3Do5-gzrl");

        // generate and display the route narrative (itinerary)
        routeManager.setItineraryView(itinerary);
        routeManager.createRoute(points);
    }
}
