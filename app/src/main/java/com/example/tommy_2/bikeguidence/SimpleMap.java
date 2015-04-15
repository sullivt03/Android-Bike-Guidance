package com.example.tommy_2.bikeguidence;

/**
 * Created by tommy_2 on 4/7/2015.
 */
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;

/**
 * Simple base class for common things used through out the demos
 *
 */
public class SimpleMap extends MapActivity {

    protected MapView map;

    /**
     * Called when the activity is first created.
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        init();
    }

    /**
     * Initialize the view.
     */
    protected void init() {
        this.setupMapView(new GeoPoint(39.7001,-75.1114), 5);
    }

    /**
     * This will set up a basic MapQuest map with zoom controls
     */
    protected void setupMapView(GeoPoint pt, int zoom) {
        this.map = (MapView) findViewById(R.id.map);

        // set the zoom level
        map.getController().setZoom(zoom);

        // set the center point
        map.getController().setCenter(pt);

        // enable the zoom controls
        map.setBuiltInZoomControls(true);

    }

    /**
     * Get the id of the layout file.
     * @return
     */
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    /**
     * Utility method for getting the text of an EditText, if no text was entered the hint is returned
     * @param editText
     * @return
     */
    public String getText(EditText editText){
        String s = editText.getText().toString();
        if("".equals(s)) s=editText.getHint().toString();
        return s;
    }


}
