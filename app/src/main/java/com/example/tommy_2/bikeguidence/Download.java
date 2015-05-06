package com.example.tommy_2.bikeguidence;

/*Larry Romano wrote this
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParserException;

/*Class downloads event data file and prepares it for xml extraction.  Upon completion of download, the activity calls on DataParser
* to parse the data and then starts the next activity.  This all happens in the broadcast receiver*/
public class Download extends ActionBarActivity {
    private TextView textView;
    Context context;
    private String Download_path = "http://elvis.rowan.edu/~palazz33/dFile.xml";
    String Download_ID = "DOWNLOAD_ID";  //DownloadManager returned reference                       //temp
    DownloadManager downloadManager;
    SharedPreferences preferenceManager; //for storing download id reference in a file

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        textView = (TextView) findViewById(R.id.status);
        preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        context = this;


        Button btnDownload = (Button)findViewById(R.id.download);
        btnDownload.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Uri Download_Uri = Uri.parse(Download_path);  //converts string to uri
                // check for valid external memory locations for storage of event data file retrieved by downloadManager
   /*             if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    dataDest = getExternalFilesDir(null);  //file path to downloaded file
                }*/
                //create our download request and set request parameters
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                //where to put downloaded file
                request.setDestinationInExternalFilesDir(Download.this, null, "dFile.xml");
                //Overridden server http response
                request.setMimeType("application/xml");

                long download_id = downloadManager.enqueue(request);
                SharedPreferences.Editor PrefEdit = preferenceManager.edit();
                PrefEdit.putLong(Download_ID, download_id);
                PrefEdit.commit();

            }
        }
        );
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //get ready to receive file information broadcast by DownloadManager
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //destroy the receiver
        unregisterReceiver(downloadReceiver);
    }
    //receive completed download intent, extract useful data, initiate DataParser, and move on to the next activity
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(preferenceManager.getLong(Download_ID, 0));
            Cursor cursor = downloadManager.query(query);

            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int status = cursor.getInt(columnIndex);
                int fileNameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                int reason = cursor.getInt(columnReason);
                String savedFilePath;
                File dataFile;
                DataParser dataParser;
                if(status == DownloadManager.STATUS_SUCCESSFUL){
                    //try data parser and catch exceptions
                    //dataFile = new File(savedFilePath);
                    long downloadID = preferenceManager.getLong(Download_ID, 0);

                    ParcelFileDescriptor file;
                    try {
                        file = downloadManager.openDownloadedFile(downloadID);
                        savedFilePath = cursor.getString(fileNameIndex);

                        Toast.makeText(Download.this,
                                "File Downloaded: " + file.toString(),
                                Toast.LENGTH_LONG).show();

                        dataFile = new File(savedFilePath);
                        dataParser = new DataParser(dataFile, context);
                        dataParser.parseData();
                        Intent intent = new Intent(context, RouteSelection.class);
                        startActivity(intent);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(Download.this,
                                e.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (XmlPullParserException e) {
                        textView.setText(e.toString());
                    }   catch (IOException e) {
                        textView.setText(e.toString());
                    }


                }else if(status == DownloadManager.STATUS_FAILED){
                    Toast.makeText(Download.this,
                            "FAILED!\n" + "reason of " + reason,
                            Toast.LENGTH_LONG).show();
                }else if(status == DownloadManager.STATUS_PAUSED){
                    Toast.makeText(Download.this,
                            "PAUSED!\n" + "reason of " + reason,
                            Toast.LENGTH_LONG).show();
                }else if(status == DownloadManager.STATUS_PENDING){
                    Toast.makeText(Download.this,
                            "PENDING!",
                            Toast.LENGTH_LONG).show();
                }else if(status == DownloadManager.STATUS_RUNNING){
                    Toast.makeText(Download.this,
                            "RUNNING!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

    };

}
