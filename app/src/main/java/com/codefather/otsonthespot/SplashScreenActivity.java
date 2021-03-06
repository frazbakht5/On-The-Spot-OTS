package com.codefather.otsonthespot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class SplashScreenActivity extends AppCompatActivity
{
    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.e(TAG, "lol:--------------------------------------------");
     /*
        This block of code checks the internet availability. if the phone is connected
        to Wifi then the app opens the login menu
        If not then the app shows a dialog box that asks the user to open the wifi or
        exit the applicaiton.
         */
        Log.e(TAG, "Internet available: " + isNetworkAvailable());
        if (isNetworkAvailable())
        {
            splashScreenTime(1000);
        }
        else
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(SplashScreenActivity.this);
            builder1.setMessage("You don't have an Internet Connection right now, " +
                    "the Application must have Internet access to proceed," +
                    " would you like to turn on your WiFi?");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Yes & Proceed",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifiManager.setWifiEnabled(true);
                            dialog.cancel();
                            new wifiConnection().execute();
                        }
                    });

            builder1.setNegativeButton(
                    "No & Exit",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                            Log.e(TAG, "Application Exiting with status 0");
                            System.exit(0);
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.setTitle(R.string.app_name);
            alert11.setIcon(R.mipmap.ic_launcher_round);
            alert11.show();
        }
    }

    private void splashScreenTime(int time)
    {
        new Handler().postDelayed(new Runnable()
        {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run()
            {
                // This method will be executed once the timer is over
                // Start your app main activity

                Context context = getApplicationContext();
                TinyDB tinydb = new TinyDB(context);


//                boolean user = tinydb.getObject("initialize", Boolean.class);
//                Log.d(TAG, "lol: " + user);
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "lol: " + user);
                if (user != null)
                {
                    Log.d(TAG, "lol: Not null");
                    Intent i = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Log.e(TAG, "lol: Is null");
                    Intent j = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    startActivity(j);
                    // close this activity
                    finish();
                }
            }
        }, time);
    }

    /**
     * This method checks if the network is available or not
     *
     * @return
     */
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * This is a private Async task which runs in the background thread, it processes a dialog box
     * and waits for the phone to get connected to the wifi. when it connects then the progress dialog
     * exits
     */
    private class wifiConnection extends AsyncTask<Void, Void, Void>
    {

        private ProgressDialog myDialog;

        @Override
        protected void onPreExecute()
        {
            /*
             * assigning attributes to progress dialog*/
            myDialog = new ProgressDialog(SplashScreenActivity.this);
            super.onPreExecute();
            myDialog.setTitle(R.string.app_name);
            myDialog.setIcon(R.mipmap.ic_launcher_round);
            myDialog.setMessage("Connecting to Wifi...");
            myDialog.setCancelable(false);
            myDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            while (!isNetworkAvailable())
            {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            myDialog.dismiss();

            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            if (user != null)
            {
                Intent i = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                startActivity(i);
            }
            Toast.makeText(getApplicationContext(), "Not signed in", Toast.LENGTH_SHORT);
            Intent i = new Intent(SplashScreenActivity.this, SignInActivity.class);
            startActivity(i);
        }
    }
}