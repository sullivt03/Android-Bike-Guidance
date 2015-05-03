package com.example.tommy_2.bikeguidence;

/*
 * Created by Me on 5/2/2015.
 */

public class AverageSpeed {

    private float totalSpeed;
    private long stepCount;
    final double meterToMile = 2.23694;

    public AverageSpeed ()
    {
        totalSpeed = 0;
        stepCount = 0;
    }

    public float calculateAvgSpeed (float speed)
    {
        totalSpeed += speed * meterToMile;
        stepCount++;
        return totalSpeed / stepCount;
    }

    public float update (float speed)
    {
        return calculateAvgSpeed(speed);
    }

}
