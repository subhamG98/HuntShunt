package com.github.florent37.materialviewpager.subham;

/**
 * Created by Dhruv on 12/21/2015.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dhruv on 12/8/2015.
 */
public class service1 extends Service
{
    public static String text="";
    private Timer myTimer;
    int n;
    int flag=1;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.d("", "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startid)
    {
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag=1;
                checkforinternet();
                if (flag == 1) {
                    notification();
                }
            }
        }, 0, 60000);

    }
    private void checkforinternet() {
        ConnectivityManager cm2 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni2 = cm2.getActiveNetworkInfo();
        if (ni2 == null) {
            flag=0;
            return;
        }

    }

    private void notification() {


        show_new_notification(SplashScreen.username1);
    }
    private void show_new_notification(final String username) {


        class LoginAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                try {
                    nameValuePairs.add(new BasicNameValuePair("username", "" + username));

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://resolvefinance.co.uk/Whatsapp/show_noti.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            public void onPostExecute(String result) {
                try {

                    if (result.contains("<") || result == null) {

                    } else {
                        //Toast.makeText(getBaseContext(), "New Notification is" + result, Toast.LENGTH_LONG).show();
                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        @SuppressWarnings("deprecation")

                        Notification notification = new Notification(R.drawable.huntshunt, "" + result, System.currentTimeMillis());
                        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent, 0);
                        notification.defaults |= Notification.DEFAULT_SOUND;
                        notification.defaults |= Notification.DEFAULT_VIBRATE;
                        notification.flags |= Notification.FLAG_AUTO_CANCEL;
                        notification.setLatestEventInfo(getBaseContext(), "HuntShunt-New Friend Request Received", "" + result+"sent you friend request", pendingIntent);
                        notificationManager.notify(9999, notification);
                        text = result;

                    }


                } catch (Exception e) {

                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute();

    }

}