package com.example.tommy_2.bikeguidence;

/**
 * Created by tommy_2 on 4/6/2015.
 */
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Login extends ActionBarActivity {
    private final static String EXTRA_PASSWORD = "com.omg.login.PASSWORD";
    private final static String downloadFileUrl = "http://elvis.rowan.edu/~romanol8/DataFile.dat";

    private TextView textView;
    private DownloadManager downloadManager;
    private long myDownloadReference;

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

        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, intentFilter);
    }

    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        unregisterReceiver(downloadReceiver);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        MenuItem voice = menu.findItem(R.id.voiceOn);
        MenuItem pause = menu.findItem(R.id.pauseRoute);
        MenuItem change = menu.findItem(R.id.changeRoute);
        voice.setChecked(voiceOn);
        return true;
    }

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
                return true;
            default:
                return false;
        }
    }



    public void sendUserInfo(View view) {

        EditText userName = (EditText) findViewById(R.id.user_name);
        EditText password = (EditText) findViewById(R.id.user_pass);

        String user = userName.getText().toString();
        String pass = password.getText().toString();
        if (verify(user, pass)) {
            textView.setText("User information is validated.  Downloading event data.  Please wait...");
            download();
        }
        else {
            textView.setText("Username and/or password are incorrect.  Please check information and try again.");

            //retry login
        }

        //   re-enter data
        //   Bundle extras = new Bundle();
        //   extras.putString(EXTRA_USERNAME, user);
        //   extras.putString(EXTRA_PASSWORD, pass);
        //   intent.putExtras(extras);
        //   startActivity(intent);

    }

    private boolean verify(String username, String password) {
        //call verification service
        return true;
    }

    private void download(){
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(downloadFileUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDescription("Downloading event data...");
        myDownloadReference = downloadManager.enqueue(request);
    }

    //  IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (myDownloadReference == reference) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(reference);
                Cursor cursor = downloadManager.query(query);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int status = cursor.getInt(columnIndex);
                int fileNameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                String savedFilePath = cursor.getString(fileNameIndex);
                int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                int reason = cursor.getInt(columnReason);

                switch (status) {
                    case DownloadManager.STATUS_SUCCESSFUL:
                        // update textview
                        textView.setText("Download complete.  Extracting event data...");
                        // call FileReader
                        // Intent i = new Intent(Login.this, FileReader.class);
                        // startService(i);
                        // i.putExtra("uri", saveFilePath);
                        break;
                    case DownloadManager.STATUS_FAILED:
                        //update textview
                        textView.setText("Download failed.  Data file is not available.  Please try again later.");

                        break;
                    case DownloadManager.STATUS_PAUSED:
                        //shouldn't happen, file is small
                        textView.setText("Event data download paused...");

                        break;
                    case DownloadManager.STATUS_PENDING:
                        //shouldn't happen
                        textView.setText("Event data download pending...");

                        break;
                    case DownloadManager.STATUS_RUNNING:
                        //do nothing
                        textView.setText("Downloading event data...");

                        break;
                }
            }
        }

    };
}
