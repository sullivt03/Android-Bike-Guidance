package com.example.tommy_2.bikeguidence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mapquest.android.maps.MapActivity;

/**
 * Created by tommy_2 on 4/2/2015.
 */
public class FortyFiveMileMap extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fortyfive_mile_preview);


        Button OK =
                (Button) findViewById(R.id.advance_to_fortyfive_route);
        OK.setOnClickListener(this);

        Button BACK =
                (Button) findViewById(R.id.advance_to_fortyfive_route); // switch id to route selection xml
        BACK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.advance_to_fortyfive_route:
                Intent i = new Intent(
                        FortyFiveMileMap.this, MyBasicMap.class);
                startActivity(i);
                break;
            case R.id.back_to_route_selection:
                Intent j = new Intent(
                        FortyFiveMileMap.this, RouteSelection.class); //switch class to back to route selection class
                startActivity(j);
                break;
        }
    }

}
