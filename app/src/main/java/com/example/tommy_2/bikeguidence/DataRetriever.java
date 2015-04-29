package com.example.tommy_2.bikeguidence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Larry on 4/19/2015.
 */

/**
 * In order to instantiate DataRetriever, you need to pass it a context of an activity.  To do this, create a Context object
 * in the onCreate method of your activity and pass that object to the constructor.
 */
public class DataRetriever {

    SQLiteDatabase db;
    Context context;
    private Cursor c;
    public DataRetriever(Context thisContext) {
        context = thisContext;
        db = context.openOrCreateDatabase("BikeDB", Context.MODE_PRIVATE, null);
    }
    //from eventinfo table

    public String getName(){
        c = db.rawQuery("SELECT * FROM eventinfo WHERE id='name'", null);
        if (c.moveToFirst()) {
            // Returning name if found
            return c.getString(1);

        } else {
            return null;
        }
    }
    public String getAbout(){
        c = db.rawQuery("SELECT * FROM eventinfo WHERE id='about'", null);
        if (c.moveToFirst()) {
            // Returning about box data if found
            return c.getString(1);

        } else {
            return "not found";
        }
    }
    public String getHelpLine(){
        c = db.rawQuery("SELECT * FROM eventinfo WHERE id='helpline'", null);
        if (c.moveToFirst()) {
            // return helpLine if found
            return c.getString(1);

        } else {
            return null;
        }
    }

    //from routeinfo table

    public float getRouteNum(int route){
        c = db.rawQuery("SELECT * FROM routeinfo WHERE id= " + route, null);
        if (c.moveToFirst()) {
            // return routenum if found
            return c.getInt(0);
        } else {
            return -1;
        }
    }

    public String getRouteName(int route){
        c = db.rawQuery("SELECT * FROM routeinfo WHERE id=" + route, null);
        if (c.moveToFirst()) {
            // Return route name if found
            return c.getString(1);
        } else {
            return null;
        }
    }
    public float getTotalDistance(int route){
        c = db.rawQuery("SELECT * FROM routeinfo WHERE id=" + route, null);
        if (c.moveToFirst()) {
            // Return total distance if found

            return c.getFloat(2);
        } else {
            return -1;
        }
    }
    public int getNumSteps(int route){
        c = db.rawQuery("SELECT * FROM routeinfo WHERE id=" + route, null);
        if (c.moveToFirst()) {
            // Return number of steps if found
            return c.getInt(3);

        } else {
            return -1;
        }
    }

    //from routeN tables, e.g. route1, route2...

    public String getStart(int route, int step){
        String tableName = "route" + route;

        c = db.rawQuery("SELECT * FROM " + tableName + " WHERE id=" + step, null);
        if (c.moveToFirst()) {
            // Return startif found
            return c.getString(1);
        } else {
            return null;
        }
    }
    public String getDirection(int route, int step) {
        String tableName = "route" + route;

        c = db.rawQuery("SELECT * FROM " + tableName + " WHERE id=" + step, null);
        if (c.moveToFirst()) {
            // Return direction if found
            return c.getString(2);
        } else {
            return null;

        }
    }
    public float getDistance(int route, int step) {
        String tableName = "route" + route;

        c = db.rawQuery("SELECT * FROM " + tableName + " WHERE id=" + step, null);
        if (c.moveToFirst()) {
            // Return distance if found
            return c.getFloat(3);
        } else {
            return -1;

        }
    }
    public String getLat(int route, int step) {
        String tableName = "route" + route;

        c = db.rawQuery("SELECT * FROM " + tableName + " WHERE id=" + step, null);
        if (c.moveToFirst()) {
            // Return latitude if found
            return c.getString(4);
        } else {
            return null;
        }
    }
    public String getLon(int route, int step) {
        String tableName = "route" + route;

        c = db.rawQuery("SELECT * FROM " + tableName + " WHERE id=" + step, null);
        if (c.moveToFirst()) {
            // Return longitude if found
            return c.getString(5);
        } else {
            return null;

        }
    }
    public String getWayPoint(int route, int step){
        return getLat(route, step) + "," + getLon(route, step);
    }
    public String getLongDirectionText(int route, int step){
        return "In "+getDistance(route, step) + " miles " + getDirection(route, step) + " on " + getStart(route, step+1);

    }
    public String getShortDirectionText(int route, int step){
        return "In 100 feet " + getDirection(route, step) + " on " + getStart(route, step+1);
    }
}
