package com.example.tommy_2.bikeguidence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParser;
/**
 * The purpose of this class is to parse the data file into the application. The parser will read
 * xml files and store the values in fields.
 *
 * @author Dominick Palazzo 
 * @version 4/24/15
 */

/**
 *	This class parses the XML data file and populates an SQLite database with results. 
 */
public class DataParser {

    //needed for database
    private Context context;
    SQLiteDatabase db;

    //needed for XML parser
    private File dataFile; //the dataFile
    private XmlPullParserFactory factory;
    private XmlPullParser parser; //the XML parser

    /**
     *	@param file The XML data file that will be parsed.
     *	@param thisContext a Context for the database
     */
    public DataParser(File file, Context thisContext) {
        dataFile = file;
        context = thisContext;
        db = context.openOrCreateDatabase("BikeDB", Context.MODE_PRIVATE, null);

        //create eventinfo table
        db.execSQL("DROP TABLE IF EXISTS eventinfo;");
        db.execSQL("CREATE TABLE eventinfo(id VARCHAR,value VARCHAR);");

        //create routeinfo table
        db.execSQL("DROP TABLE IF EXISTS routeinfo;");
        db.execSQL("CREATE TABLE routeinfo(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "routename VARCHAR," +
                "totaldistance REAL," +
                "numsteps INTEGER);");

        //create route specific tables named route1, route2,...routeN
        String tName;
        int rNum;
        for (int i = 0; i < 5; i++) {
            rNum = i + 1;
            tName = "route" + rNum;
            db.execSQL("DROP TABLE IF EXISTS " + tName + ";");
            db.execSQL("CREATE TABLE " + tName + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "start VARCHAR," +
                    "direction VARCHAR," +
                    "distance REAL," +
                    "latitude VARCHAR," +
                    "longitude VARCHAR);");
        }
    }

    /**
     *	The parseData method will take the data from the XML file (assuming correct input)
     *	and input the data into a database.
     */
    public void parseData() throws XmlPullParserException, IOException {
        int numRoute = 0;

        //routeN table fields
        String numSteps = "";
        String routeName = "";
        String totalDistance = "";
        String start = "";
        String direction = "";
        String distance = "";
        String lat = "";
        String lon = "";
        String tableName = "";

        //if controller
        String name;

        //set up XMLPullParser
        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        parser.setInput(new FileReader(dataFile));
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                name = parser.getName();

                if (!name.equals("event") && !name.equals("routes") && !name.equals("step") &&
                        !name.equals("option")) {

                    if (name.equals("start")) {
                        eventType = parser.next();
                        start = parser.getText();
                    } else if (name.equals("direction")) {
                        eventType = parser.next();
                        direction = parser.getText();
                    } else if (name.equals("distance")) {
                        eventType = parser.next();
                        distance = parser.getText();
                    } else if (name.equals("latitude")) {
                        eventType = parser.next();
                        lat = parser.getText();
                    }
                    else if (name.equals("longitude")) {
                        eventType = parser.next();
                        lon = parser.getText();
                        db.execSQL("INSERT INTO " + tableName + " (start, direction, distance, latitude, longitude) \n" +
                                "VALUES('" + start + "','" + direction + "'," + distance + ",'" + lat + "','" + lon + "');");
                    }
                    else if (name.equals("routename")) {
                        numRoute++;
                        tableName = "route" + numRoute;
                        eventType = parser.next();
                        routeName = parser.getText();
                    } else if (name.equals("totaldistance")) {
                        eventType = parser.next();
                        totalDistance = parser.getText();
                    } else if (name.equals("numsteps")) {
                        eventType = parser.next();
                        numSteps = parser.getText();
                        db.execSQL("INSERT INTO routeinfo (routename, totaldistance, numsteps) \n" +
                                "VALUES('" + routeName + "'," + totalDistance + "," + numSteps + ");");
                    }  else if (name.equals("name")) {
                        eventType = parser.next();
                        db.execSQL("INSERT INTO eventinfo VALUES('name','" + parser.getText() + "');");
                    }else if (name.equals("about")) {
                        eventType = parser.next();
                        db.execSQL("INSERT INTO eventinfo VALUES('about','" + parser.getText() + "');");
                    } else if (name.equals("phonenumber")) {
                        eventType = parser.next();
                        db.execSQL("INSERT INTO eventinfo VALUES('helpline','" + parser.getText() + "');");
                    }
                }
            }
            eventType = parser.next();
        }
    }
}