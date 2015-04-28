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
public class UserAgreement extends Activity implements View.OnClickListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.user_agreement);


            Button UA_OK =
                    (Button) findViewById(R.id.user_agreement_ok);
            UA_OK.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        Intent i=new Intent(
                UserAgreement.this, Login.class);
        startActivity(i);
    }

}
