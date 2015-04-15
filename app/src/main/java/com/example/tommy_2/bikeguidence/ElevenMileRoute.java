//this is a test comment to see if it works

package com.example.tommy_2.bikeguidence;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.mapquest.android.maps.LineOverlay;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.RouteManager;
import com.mapquest.android.maps.RouteResponse;
import com.mapquest.android.maps.ServiceResponse;

import java.util.ArrayList;


public class ElevenMileRoute extends SimpleMap {

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



    protected MapView map;
    private MyLocationOverlay myLocationOverlay;
    protected LineOverlay routeLine = new LineOverlay();

    public static ArrayList setGeoList(String [] LatLngs){
        ArrayList<GeoPoint> data = new ArrayList<GeoPoint>();
        for(int i=0; i< LatLngs.length; i+=2) {
            double temp1 = Double.parseDouble(LatLngs[i]);
            double temp2 = Double.parseDouble(LatLngs[i+1]);
            GeoPoint pnt = new GeoPoint(temp1, temp2);
            data.add(pnt);
        }
        return data;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eleven_mile_preview);
        setupMapView();
        setupMyLocation();
        init();
    }

    // set your map and enable default zoom controls
    private void setupMapView() {
        this.map = (MapView) findViewById(R.id.map);
        map.setBuiltInZoomControls(true);
    }

    // set up a MyLocationOverlay and execute the runnable once we have a location fix
    private void setupMyLocation() {
        this.myLocationOverlay = new MyLocationOverlay(this, map);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                GeoPoint currentLocation = myLocationOverlay.getMyLocation();
                map.getController().animateTo(currentLocation);
                map.getController().setZoom(14);
                map.getOverlays().add(myLocationOverlay);
                myLocationOverlay.setFollowing(true);
            }
        });
    }

    // enable features of the overlay
    @Override
    protected void onResume() {
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableCompass();
        super.onResume();
    }

    // disable features of the overlay when in the background
    @Override
    protected void onPause() {
        super.onPause();
        myLocationOverlay.disableCompass();
        myLocationOverlay.disableMyLocation();
    }

    @Override
    public boolean isRouteDisplayed() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.eleven_mile_preview;

    }

    @Override
    protected void init() {
        super.init();

        //find the objects we need to interact with
        final MapView mapView = (MapView) findViewById(R.id.map);
        final WebView itinerary = (WebView) findViewById(R.id.itinerary);

        final RelativeLayout mapLayout = (RelativeLayout) findViewById(R.id.mapLayout);
        final RelativeLayout itineraryLayout = (RelativeLayout) findViewById(R.id.itineraryLayout);
        final Button createRouteButton = (Button) findViewById(R.id.createRouteButton);
        final RouteManager routeManager = new RouteManager(this);
        routeManager.setMapView(mapView);
        routeManager.setItineraryView(itinerary);
        routeManager.setDebug(true);
        routeManager.setRouteCallback(new RouteManager.RouteCallback() {
            @Override
            public void onError(RouteResponse routeResponse) {
                ServiceResponse.Info info = routeResponse.info;
                int statusCode = info.statusCode;

                StringBuilder message = new StringBuilder();
                message.append("Unable to create route.\n")
                        .append("Error: ").append(statusCode).append("\n")
                        .append("Message: ").append(info.messages);
                Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_LONG).show();
                createRouteButton.setEnabled(true);
            }

            @Override
            public void onSuccess(RouteResponse routeResponse) {
                createRouteButton.setEnabled(true);
            }
        });


        //create an onclick listener for the instructional text
        createRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRouteButton.setEnabled(false);
                createRouteButton.setVisibility(View.GONE);
                //String startAt = getText(start);
                //String endAt = getText(end);
                //routeManager.createRoute(startAt, endAt);
                routeManager.createRoute(points);
            }
        });

}

}




