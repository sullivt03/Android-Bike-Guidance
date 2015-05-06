package com.example.tommy_2.bikeguidence;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends ActionBarActivity {
    private TextView textView;

    //menu item values
    private boolean voiceOn = true;
    private boolean pauseRoute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView = (TextView) findViewById(R.id.status);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        MenuItem voice = menu.findItem(R.id.voiceOn);
        MenuItem pause = menu.findItem(R.id.pauseRoute);
        MenuItem change = menu.findItem(R.id.changeRoute);
        MenuItem about = menu.findItem(R.id.aboutScreen);
        MenuItem call = menu.findItem(R.id.call);
        voice.setChecked(voiceOn);
        pause.setChecked(pauseRoute);
        return true;
    }
    */

    /*
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
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:7325399759"));
                startActivity(callIntent);
                return true;
            case R.id.aboutScreen:
                Intent intent = new Intent(Login.this, About_Screen.class);
                startActivity(intent);
            case R.id.call:
                //make phone call to event help line
                return true;
            default:
                return false;
        }
    }
    */


    public void sendUserInfo(View view) {
        //collect user entered name and password values
        EditText userName = (EditText) findViewById(R.id.user_name);
        EditText password = (EditText) findViewById(R.id.user_pass);
        String user = userName.getText().toString();
        String pass = password.getText().toString();
        //verify username and password.  If valid, continue to Download activity, else prompt for re-submission
        if (verify(user, pass)) {
            textView.setText("User information is validated.  Downloading event data.  Please wait...");
            Intent intent = new Intent(this, Download.class);
            startActivity(intent);
            //downloadDataFile();
        }
        else {
            //retry login
            textView.setText("Username and/or password are incorrect.  Please check information and try again.");
        }
    }
    private boolean verify(String username, String password) {
        //call verification service  stub
        return true;
    }
}