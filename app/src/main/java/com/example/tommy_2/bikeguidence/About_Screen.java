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
    private Animation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
    //private Animation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
    private final View aboutView = findViewById(R.id.fadeLayer);
    private final int fadeDuration = 1000;
    private TextView textViewToChange = (TextView) findViewById(R.id.aboutInformation);
    private String aboutInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__screen);

        fadeIn.setDuration(fadeDuration);
        //fadeOut.setDuration(fadeDuration);
        fadeIn.setAnimationListener(new AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                aboutView.setVisibility(View.VISIBLE);
            }
        });

        aboutView.startAnimation(fadeIn);

        Button closeButton = (Button) findViewById(R.id.close);
        closeButton.setOnClickListener(this);


        setAboutInfoText("About Information Text Place Holder");
        textViewToChange.setText(aboutInfoText);
    }

    @Override
    public void onClick(View v)
    {
        //Intent i = new Intent(About_Screen.this, previous.class);//need to add correct activity name
        // startActivity(i);
    }

    public void setAboutInfoText(String aboutInfoText)
    {
        this.aboutInfoText = aboutInfoText;
    }

}
