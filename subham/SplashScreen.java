package com.github.florent37.materialviewpager.subham;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    public static String imps;
    public static String username1="";
    GPSTracker gps;
    public static double latitude=0.00;
    public static double longitude=0.00;
    SharedPreferences pref1;
//AIzaSyDcoqMKqNnT7Adan9xx1tz7nd6jjwgUi14

//Server==AIzaSyAnK3nJzdfnnB305wODVr6psmI4mtbr8kA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent i2=new Intent(this,service1.class);
        this.startService(i2);
        Intent i3=new Intent(this,service2.class);
        this.startService(i3);

        gps = new GPSTracker(getApplicationContext());
        pref1 = getSharedPreferences("ActivityPREF1", Context.MODE_PRIVATE);
        username1=pref1.getString("username","");
        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if(latitude==0.00 && longitude==0.00)
            {

            }else {
                getData();
            }
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert(SplashScreen.this);
        }


    }


    private void getData(){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
          //      progressDialog = ProgressDialog.show(getApplicationContext(), "Fetching Data", "Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            //    progressDialog.dismiss();
                imps=s;
                Intent i = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);

            }

            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+username1+"&lati="+latitude+"&longi="+longitude);
                    Log.i("ok check it", "http://resolvefinance.co.uk/Whatsapp/getlist.php?username="+username1+"&lati=" + latitude + "&longi=" + longitude);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null) {
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    getData();
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

}