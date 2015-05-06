package com.example.tommy_2.bikeguidence;

/**
 * Created by tommy_2 on 4/6/2015.
 */
//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class About_Screen extends Activity implements View.OnClickListener
{
    private String aboutInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__screen);
        TextView textViewToChange = (TextView) findViewById(R.id.aboutInformation);

        Button closeButton = (Button) findViewById(R.id.close);
        closeButton.setOnClickListener(this);


        setAboutInfoText("About Information Text Place Holder");
        textViewToChange.setText(aboutInfoText);
    }

    @Override
    public void onClick(View v)
    {

    }

    public void setAboutInfoText(String aboutInfoText)
    {
        this.aboutInfoText = aboutInfoText;
    }

}
