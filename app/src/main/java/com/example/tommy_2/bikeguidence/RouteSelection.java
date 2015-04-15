package com.example.tommy_2.bikeguidence;

/**
 * Created by tommy_2 on 4/6/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class RouteSelection extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_selection);

        Button Route_1_OK =
                (Button) findViewById(R.id.route_selection_1);
        Route_1_OK.setOnClickListener(this);

        Button Route_2_OK =
                (Button) findViewById(R.id.route_selection_2);
        Route_2_OK.setOnClickListener(this);

        Button Route_3_OK =
                (Button) findViewById(R.id.route_selection_3);
        Route_3_OK.setOnClickListener(this);

        Button Route_4_OK =
                (Button) findViewById(R.id.route_selection_4);
        Route_4_OK.setOnClickListener(this);

        Button Route_EXIT =
                (Button) findViewById(R.id.route_selection_exit);
        Route_EXIT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.route_selection_1:
                Intent i = new Intent(
                        RouteSelection.this, ElevenMileRoute.class);
                startActivity(i);
                break;

            case R.id.route_selection_2:
                Intent j = new Intent(
                        RouteSelection.this, TwentyMileMap.class);
                startActivity(j);
                break;

            case R.id.route_selection_3:
                Intent k = new Intent(
                        RouteSelection.this, FortyFiveMileMap.class);
                startActivity(k);
                break;

            case R.id.route_selection_4:
                Intent r = new Intent(
                        RouteSelection.this, SixtyTwoMileMap.class);
                startActivity(r);
                break;

            case R.id.route_selection_exit:
                Intent t = new Intent(
                        RouteSelection.this, Login.class);
                startActivity(t);
                break;
        }
    }

}
