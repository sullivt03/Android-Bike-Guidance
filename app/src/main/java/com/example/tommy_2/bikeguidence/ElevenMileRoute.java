//this is a test comment to see if it works

package com.example.tommy_2.bikeguidence;

import android.content.Intent;
import android.location.*;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.RouteResponse.Route;
import com.mapquest.android.maps.RouteResponse.Route.Leg;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.RouteManager;
import com.mapquest.android.maps.RouteResponse;
import com.mapquest.android.maps.ServiceResponse;

import java.util.ArrayList;
import java.util.Locale;


public class ElevenMileRoute extends SimpleMap implements TextToSpeech.OnInitListener {

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


    private boolean voiceOn = true;
    private boolean pauseRoute = false;
    DataRetriever getter;
    int route = 2; //getRouteID from activity file.
    int step =1;
    int count = 0;
    String lat;
    String lon;
    Double [] CurrLeg;

    protected MapView map;
    private MyLocationOverlay myLocationOverlay;
    protected LineOverlay routeLine = new LineOverlay();
    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

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
        getter = new DataRetriever(ElevenMileRoute.this);
        lat = getter.getLat(route,step);
        lon = getter.getLon(route, step);
        CurrLeg = new Double[]{Double.parseDouble(lat), Double.parseDouble(lon)};
        init();

        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        MenuItem voice = menu.findItem(R.id.voiceOn);
        MenuItem pause = menu.findItem(R.id.pauseRoute);
        MenuItem change = menu.findItem(R.id.changeRoute);
        MenuItem call = menu.findItem(R.id.call);
        voice.setChecked(voiceOn);
        pause.setChecked(pauseRoute);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voiceOn:
                voiceOn = !item.isChecked();
                item.setChecked(voiceOn);
                return true;
            case R.id.pauseRoute:
                pauseRoute = !item.isChecked();
                item.setChecked(pauseRoute);
                return true;
            case R.id.changeRoute:
                //call the change route menu
                Intent intent = new Intent(this, RouteSelection.class);
                startActivity(intent);
                return true;
            case R.id.call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:7325399759"));
                startActivity(callIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isRouteDisplayed() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.eleven_mile_preview;

    }

    //speak the user text
    private void speakWords(String s) {
        String speech = "In point two miles, turn left onto Carpenter Street";
        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
            }
            else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    //setup TTS
    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
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
                Location waypoint = new Location("current");
                waypoint.setLatitude(CurrLeg[0]);
                waypoint.setLongitude(CurrLeg[1]);

                float dist = waypoint.distanceTo(/*currentPosition*/);


                if(dist > 30 && dist < 60 && count % 2 == 0){
                    speakWords(getter.getLongDirectionText(route, step));
                    CurrLeg[0] = Double.parseDouble(getter.getLat(route, step + 1));
                    CurrLeg[1] = Double.parseDouble(getter.getLon(route, step + 1));
                    count++;
                }
                else if(dist < 30 && count % 2 == 1){
                    speakWords(getter.getShortDirectionText(route, step++));
                    count++;
                }
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
                //speakWords();
            }
        });

}

}




